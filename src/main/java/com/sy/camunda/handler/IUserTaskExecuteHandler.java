package com.sy.camunda.handler;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface IUserTaskExecuteHandler {

    boolean match(String taskCode);

    void execute(ProcessInstance instance, Task task,  Map<String, Object> params);

    default void after(ProcessInstance instance, List<Task> task, Map<String, Object> params) {}
}
