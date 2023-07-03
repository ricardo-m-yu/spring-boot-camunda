package com.sy.camunda.service.impl;

import com.sy.camunda.domain.bo.InstanceBo;
import com.sy.camunda.domain.bo.ProcessBo;
import com.sy.camunda.domain.bo.TaskBo;
import com.sy.camunda.domain.vo.InstanceSearch;
import com.sy.camunda.domain.vo.ProcessFormVo;
import com.sy.camunda.domain.vo.StartProcessVo;
import com.sy.camunda.domain.vo.TaskSearch;
import com.sy.camunda.handler.IUserProcessExecuteHandler;
import com.sy.camunda.handler.IUserTaskExecuteHandler;
import com.sy.camunda.service.IBpmnService;
import com.sy.camunda.util.ParamAssert;
import com.sy.camunda.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ResourceDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class BpmnServiceImpl implements IBpmnService {


    private final RuntimeService runtimeService;
    private final IdentityService identityService;
    private final ProcessEngine processEngine;
    private final TaskService taskService;
    private final HistoryService historyService;
    private final RepositoryService repositoryService;
    private final List<IUserTaskExecuteHandler> iUserTaskExecuteHandlers;
    private final List<IUserProcessExecuteHandler> iUserProcessExecuteHandlers;


    public BpmnServiceImpl(RuntimeService runtimeService, IdentityService identityService, ProcessEngine processEngine, TaskService taskService, HistoryService historyService, RepositoryService repositoryService, List<IUserTaskExecuteHandler> iUserTaskExecuteHandlers
            , List<IUserProcessExecuteHandler> iUserProcessExecuteHandlers) {
        this.runtimeService = runtimeService;
        this.identityService = identityService;
        this.processEngine = processEngine;
        this.taskService = taskService;
        this.historyService = historyService;
        this.repositoryService = repositoryService;
        this.iUserTaskExecuteHandlers = iUserTaskExecuteHandlers;
        this.iUserProcessExecuteHandlers = iUserProcessExecuteHandlers;
    }

    @Override
    @Transactional
    public List<TaskBo> finishTask(ProcessFormVo vo) {
        String taskId = vo.getRunTimeTaskId();
        Map<String, Object> variables = vo.getParams();
        List<IUserTaskExecuteHandler> executeHandlers = iUserTaskExecuteHandlers.stream().filter(v -> v.match(vo.getTaskCode())).toList();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        ParamAssert.notNull(task, "task is null! taskId -> %s", taskId);
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        IUserTaskExecuteHandler handler = null;
        if (executeHandlers.size() > 0) {
            handler = executeHandlers.get(0);
        }
        if (ObjectUtils.isNotEmpty(handler)) handler.execute(instance, task, variables);
        taskService.complete(taskId, variables);

        TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId());
        List<Task> nextTasks = taskQuery.list();
        if (ObjectUtils.isNotEmpty(handler)) {
            handler.after(instance, nextTasks, variables);
        }
        return convertTaskBo(nextTasks);
    }

    @Override
    public List<TaskBo> getCurrentTask(TaskSearch search) {
        TaskQuery taskQuery = initTaskQuery(search);

        return convertTaskBo(taskQuery.list());
    }


    private TaskQuery initTaskQuery(TaskSearch search) {
        TaskQuery taskQuery = taskService.createTaskQuery().orderByTaskCreateTime().desc();

        // TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(UserUtil.getUserId().toString()).orderByTaskCreateTime().desc();
        if (StringUtils.hasText(search.getInstanceId())) {
            taskQuery.processInstanceId(search.getInstanceId());
        }
        return taskQuery;
    }


    private List<TaskBo> convertTaskBo(List<Task> tasks) {
        if (CollectionUtils.isEmpty(tasks)) {
            return new ArrayList<>();
        }

        String[] defIds = tasks.stream().map(Task::getProcessDefinitionId).toArray(String[]::new);
        Map<String, ProcessDefinition> processDefinitionMap = repositoryService.createProcessDefinitionQuery().processDefinitionIdIn(defIds).list().stream().collect(Collectors.toMap(ResourceDefinition::getId, v -> v));

        Set<String> instanceIds = tasks.stream().map(Task::getProcessInstanceId).collect(Collectors.toSet());
        Map<String, HistoricProcessInstance> historyInstanceMap = historyService.createHistoricProcessInstanceQuery().processInstanceIds(instanceIds).list().stream().collect(Collectors.toMap(HistoricProcessInstance::getId, v -> v));

        Set<Long> userIds = historyInstanceMap.values().stream().filter(v -> null != v.getStartUserId()).map(v -> Long.parseLong(v.getStartUserId())).collect(Collectors.toSet());
        Set<Long> taskUserIds = tasks.stream().filter(v -> null != v.getAssignee()).map(v -> Long.parseLong(v.getAssignee())).collect(Collectors.toSet());

        userIds.addAll(taskUserIds);
        return tasks.stream().map(v -> {
            log.debug(v.getTaskDefinitionKey());
            TaskBo taskBo = new TaskBo();


            ProcessDefinition pd = processDefinitionMap.get(v.getProcessDefinitionId());
            Map<String, Object> variables = runtimeService.getVariables(v.getProcessInstanceId());


            HistoricProcessInstance historicProcessInstance = historyInstanceMap.get(v.getProcessInstanceId());

            // 设置任务信息
            taskBo.setCreateTime(v.getCreateTime());
            taskBo.setDueDate(v.getDueDate());
            taskBo.setVariables(variables);


            // 设置流程相关信息
            taskBo.setRunTimeTaskId(v.getId());
            taskBo.setProcessDefinitionId(v.getProcessDefinitionId());
            taskBo.setProcessDefinitionKey(pd.getKey());
            taskBo.setInstanceStartTime(historicProcessInstance.getStartTime());
            taskBo.setProcessDefinitionName(pd.getName());
            taskBo.setInstanceId(v.getProcessInstanceId());

            // 设置执行时间
            long time = (new Date().getTime() - v.getCreateTime().getTime());
            taskBo.setExecuteTime(BigDecimal.valueOf(time).divide(BigDecimal.valueOf(1000 * 3600), 2, RoundingMode.HALF_DOWN));

            // 设置执行人
            String assignee = v.getAssignee();
            return taskBo;
        }).toList();
    }


    @Override
    public List<InstanceBo> getCurrentProcess(InstanceSearch search) {
        List<HistoricProcessInstance> processInstances = historyService.createHistoricProcessInstanceQuery().unfinished().startedBy(UserUtil.getUserId().toString()).list();
        return convertInstance(processInstances);
    }

    private List<InstanceBo> convertInstance(List<HistoricProcessInstance> processInstances) {
        if (CollectionUtils.isEmpty(processInstances)) {
            return Collections.emptyList();
        }
        List<InstanceBo> list = processInstances.stream().map(v -> {
            InstanceBo bo = new InstanceBo();
            BeanUtils.copyProperties(v, bo);
            return bo;
        }).toList();

        return list;
    }

    @Override
    @Transactional
    public void terminateProcess(ProcessFormVo vo) {

        String key = vo.getProcessDefinitionKey();
        String instanceId = vo.getProcessInstanceId();
        Assert.notNull(key, "key is null!");
        Assert.notNull(instanceId, "instanceId is null!");

        List<IUserProcessExecuteHandler> executeHandlers = iUserProcessExecuteHandlers.stream().filter(v -> v.match(key)).toList();
        Assert.notEmpty(executeHandlers, "IUserProcessExecuteHandler is null!");

        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        executeHandlers.forEach(v -> v.terminate(instance, vo.getParams()));
    }

    @Override
    public void backTask(ProcessFormVo vo) {
        String instanceId = vo.getProcessInstanceId();
        String taskId = vo.getRunTimeTaskId();
        Assert.notNull(taskId, "taskId is null!");
        Assert.notNull(instanceId, "instanceId is null!");

        Task activeTask = taskService.createTaskQuery()
                .taskId(taskId)
                .active()
                .singleResult();
        List<HistoricTaskInstance> historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(instanceId)
                .orderByHistoricActivityInstanceStartTime()
                .desc()
                .list();

        List<HistoricTaskInstance> historicTaskInstances = historicTaskInstance.stream().filter(v -> !v.getTaskDefinitionKey().equals(activeTask.getTaskDefinitionKey())).toList();

        Assert.notEmpty(historicTaskInstances, "当前已是初始任务！");
        HistoricTaskInstance curr = historicTaskInstances.get(0);
        String assignee = curr.getAssignee();

        runtimeService.createProcessInstanceModification(instanceId)
                .cancelAllForActivity(activeTask.getTaskDefinitionKey())
                .setAnnotation("重新执行")
                .startBeforeActivity(curr.getTaskDefinitionKey())
                .execute();

        Task task = taskService.createTaskQuery().taskId(curr.getId()).singleResult();
        taskService.setAssignee(task.getId(), assignee);
    }

    @Override
    public void reAssign(ProcessFormVo vo) {
        String runTimeTaskId = vo.getRunTimeTaskId();
        String nextTaskAssignee = vo.getNextTaskAssignee();
        Assert.notNull(runTimeTaskId, "任务不能为空");
        Assert.notNull(nextTaskAssignee, "指定的执行人不能为空");
        taskService.setAssignee(runTimeTaskId, nextTaskAssignee);
    }


    private void setInfo(List<InstanceBo> instanceBos) {

        String[] instanceIds = instanceBos.stream().map(v -> v.getId().toString()).toArray(String[]::new);

        Map<String, Task> taskMap = taskService.createTaskQuery().processInstanceIdIn(instanceIds).list().stream().collect(Collectors.toMap(Task::getProcessInstanceId, v -> v, (v1, v2) -> v2));


        for (InstanceBo bo : instanceBos) {
            Task task = taskMap.get(bo.getId().toString());
            if (null != task) {
                bo.setCurrTaskId(Long.parseLong(task.getId()));

                // 设置执行时间
                long time = (new Date().getTime() - bo.getStartTime().getTime());
                bo.setExecuteTime(BigDecimal.valueOf(time).divide(BigDecimal.valueOf(1000 * 3600), 2, RoundingMode.HALF_DOWN));
            }
        }
    }


    @Override
    public List<TaskBo> startProcess(StartProcessVo startProcessVo) {
        Map<String, Object> params = startProcessVo.getParams();
        return start(startProcessVo.getProcessKey(), params);
    }


    @Transactional
    public List<TaskBo> start(String processKey, Map<String, Object> params) {
        // 测试用订单
        ProcessDefinition result = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).orderByDeploymentTime().desc().list().get(0);
        ParamAssert.notNull(result, "bpmn任务【%s】未定义！", processKey);

        // 设置camunda鉴权为系统用户
        identityService.setAuthenticatedUserId(UserUtil.getUserId().toString());

        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey, params);

        //业务执行逻辑
        List<IUserProcessExecuteHandler> processExecuteHandlers = iUserProcessExecuteHandlers.stream().filter(v -> v.match(processKey)).toList();

        // 初始化时设置当前任务
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getId()).list();

        if (CollectionUtils.isEmpty(processExecuteHandlers)) {
            setTaskAssignee(tasks);
        } else {
            processExecuteHandlers.forEach(v -> v.setFirstAssignee(instance, tasks, params));
        }
        log.info("process start ... key -> {} instanceId -> {}", processKey, instance.getProcessInstanceId());

        return convertTaskBo(tasks);
    }

    private void setTaskAssignee(List<Task> taskList) {
        for (Task task : taskList) {
            taskService.setAssignee(task.getId(), UserUtil.getUserId().toString());
        }
    }

    @Override
    public void setVariables(ProcessFormVo vo) {
        Assert.notNull(vo.getProcessInstanceId(), "instanceId is null!");
        runtimeService.setVariables(vo.getProcessInstanceId(), vo.getParams());
    }
}
