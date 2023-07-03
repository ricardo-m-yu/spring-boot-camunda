package com.sy.camunda.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


@ApiModel("任务BO")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskBo extends BaseBo {

    @ApiModelProperty(notes = "任务code")
    private String code;

    @ApiModelProperty(notes = "任务名称")
    private String name;

    @ApiModelProperty(notes = "任务描述")
    private String comment;


    @ApiModelProperty(notes = "任务前端路径")
    private String url;

    @ApiModelProperty(notes = "在数据库中定义的参数")
    private String parameters;

    @ApiModelProperty(notes = "执行人")
    private Long assignee;

    @ApiModelProperty(notes = "执行人姓名")
    private String assigneeName;

    @ApiModelProperty(notes = "关联病人Id")
    private Long relatedId;

    @ApiModelProperty(notes = "关联病人姓名")
    private String relatedName;

    @ApiModelProperty(notes = "任务产生时间")
    private Date createTime;

    @ApiModelProperty(notes = "截止时间")
    private Date dueDate;

    @ApiModelProperty(notes = "执行时间:单位-小时")
    private BigDecimal executeTime;

    private Long parentId;
    private Boolean available;
    private String runTimeTaskId;

    //以下为流程信息

    private String processDefinitionId;

    @ApiModelProperty(notes = "流程编号")
    private String processDefinitionKey;

    @ApiModelProperty(notes = "流程名称")
    private String processDefinitionName;

    @ApiModelProperty(notes = "流程开始时间")
    private Date instanceStartTime;

    @ApiModelProperty(notes = "流程发起人")
    private Long instanceStartUser;

    @ApiModelProperty(notes = "流程发起人名字")
    private String instanceStartUserName;

    private String instanceId;

    @ApiModelProperty(notes = "过程变量")
    private Map<String, Object> variables;

    @ApiModelProperty(notes = "是否可分配")
    private boolean cloudAssign;
}
