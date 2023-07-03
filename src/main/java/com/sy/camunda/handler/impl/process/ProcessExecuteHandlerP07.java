package com.sy.camunda.handler.impl.process;

import com.sy.camunda.handler.AbstractTaskExecutionHandler;
import com.sy.camunda.handler.IUserProcessExecuteHandler;
import com.sy.camunda.util.UserUtil;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class ProcessExecuteHandlerP07 extends AbstractTaskExecutionHandler implements IUserProcessExecuteHandler {

    public static final String PROCESS_CODE = "P07";

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;

    @Override
    public boolean match(String processCode) {
        return processCode.equals(PROCESS_CODE);
    }

    @Override
    public void suspend(ProcessInstance instance) {
        runtimeService.suspendProcessInstanceById(instance.getId());
    }

    @Override
    public void startExecute(ProcessInstance instance, Map<String, Object> params) {
    }

    @Override
    public void setFirstAssignee(ProcessInstance instance, List<Task> tasks, Map<String, Object> params) {
        for (Task task : tasks) {
            taskService.setAssignee(task.getId(), UserUtil.getUserId().toString());
        }
    }

    @Override
    public void terminate(ProcessInstance instance, Map<String, Object> params) {

        runtimeService.deleteProcessInstance(instance.getId(), "手动删除");
    }
}
