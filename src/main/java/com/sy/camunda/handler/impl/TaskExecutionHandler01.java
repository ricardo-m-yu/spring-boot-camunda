package com.sy.camunda.handler.impl;


import com.sy.camunda.handler.IUserTaskExecuteHandler;
import com.sy.camunda.util.ParamAssert;
import com.sy.camunda.util.UserUtil;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class TaskExecutionHandler01 implements IUserTaskExecuteHandler {

    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;

    @Override
    public boolean match(String taskCode) {
        return taskCode.equals("T01");
    }

    @Override
    public void execute(ProcessInstance instance, Task task, Map<String, Object> params) {
        //业务

    }

    @Override
    public void after(ProcessInstance instance, List<Task> task, Map<String, Object> params) {

    }

}