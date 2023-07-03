package com.sy.camunda.controller;


import com.sy.camunda.domain.bo.InstanceBo;
import com.sy.camunda.domain.bo.ProcessBo;
import com.sy.camunda.domain.bo.TaskBo;
import com.sy.camunda.domain.vo.InstanceSearch;
import com.sy.camunda.domain.vo.ProcessFormVo;
import com.sy.camunda.domain.vo.StartProcessVo;
import com.sy.camunda.domain.vo.TaskSearch;
import com.sy.camunda.service.IBpmnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "流程管理-bpmn流程设计")
@RestController
@RequestMapping(value = "/bpmn")
public class BpmnController {

    private final IBpmnService bpmnService;

    public BpmnController(IBpmnService bpmnService) {
        this.bpmnService = bpmnService;
    }

    @ApiOperation("1、开启一个新流程(附带参数)")
    @PostMapping("/start/process")
    public List<TaskBo> startProcess(@RequestBody StartProcessVo startProcessVo) {
        return bpmnService.startProcess(startProcessVo);
    }

    @ApiOperation("2、获取待办流程")
    @PostMapping("/current/process")
    public List<InstanceBo> getCurrentProcess(@RequestBody InstanceSearch search) {
        return bpmnService.getCurrentProcess(search);
    }
    @ApiOperation("3、获取待办任务")
    @PostMapping("/task/current")
    public List<TaskBo> getCurrentByProcess(@RequestBody TaskSearch search) {
        return bpmnService.getCurrentTask(search);
    }

    @ApiOperation("4、完成任务")
    @PutMapping("/finish/process/task")
    public List<TaskBo> finishTask (@Validated @RequestBody ProcessFormVo vo) {
        return bpmnService.finishTask(vo);
    }

    @ApiOperation("5、终止流程")
    @PostMapping("/terminate/process")
    public void terminateProcess (@Validated @RequestBody ProcessFormVo vo) {
        bpmnService.terminateProcess(vo);
    }

    @ApiOperation("6、任务回退到上一步")
    @PostMapping("/task/back")
    public void backTask (@Validated @RequestBody ProcessFormVo vo) {
        bpmnService.backTask(vo);
    }

    @ApiOperation("8、分配任务")
    @PostMapping("/task/reAssign")
    public void reAssign (@Validated @RequestBody ProcessFormVo vo) {
        bpmnService.reAssign(vo);
    }

    @ApiOperation("9、 设置流程变量")
    @PostMapping("/variables")
    public void setVariables (@Validated @RequestBody ProcessFormVo vo) {
        bpmnService.setVariables(vo);
    }
}