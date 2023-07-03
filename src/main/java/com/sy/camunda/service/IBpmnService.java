package com.sy.camunda.service;



import com.sy.camunda.domain.bo.InstanceBo;
import com.sy.camunda.domain.bo.ProcessBo;
import com.sy.camunda.domain.bo.TaskBo;
import com.sy.camunda.domain.vo.InstanceSearch;
import com.sy.camunda.domain.vo.ProcessFormVo;
import com.sy.camunda.domain.vo.StartProcessVo;
import com.sy.camunda.domain.vo.TaskSearch;

import java.util.List;
import java.util.Map;

public interface IBpmnService {


    List<TaskBo> finishTask(ProcessFormVo vo);

    List<TaskBo> getCurrentTask(TaskSearch search);

    List<InstanceBo> getCurrentProcess(InstanceSearch search);

    void terminateProcess(ProcessFormVo vo);

    void backTask(ProcessFormVo vo);
    void reAssign(ProcessFormVo vo);

    void setVariables(ProcessFormVo vo);

    List<TaskBo> startProcess(StartProcessVo startProcessVo);

}
