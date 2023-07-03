package com.sy.camunda.handler;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface IUserProcessExecuteHandler {

    boolean match(String processCode);

    void suspend(ProcessInstance instance);

    void setFirstAssignee(ProcessInstance instance, List<Task> tasks, Map<String, Object> params);

    void terminate(ProcessInstance instance, Map<String, Object> params);

    void startExecute(ProcessInstance instance, Map<String, Object> params);
}
