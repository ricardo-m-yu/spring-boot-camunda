package com.sy.camunda.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;


@ApiModel("流程参数VO")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessFormVo {

    @ApiModelProperty("流程实例id")
    private String processInstanceId;

    @ApiModelProperty("流程定义id")
    private String processDefinitionId;

    @ApiModelProperty("流程定义key")
    private String processDefinitionKey;

    @ApiModelProperty("任务id")
    private String runTimeTaskId;

    @ApiModelProperty("任务code")
    private String taskCode;

    @ApiModelProperty("下一个任务的执行者")
    private String nextTaskAssignee;

    @ApiModelProperty("参数 - map结构 <key, value>")
    private Map<String, Object> params;
}
