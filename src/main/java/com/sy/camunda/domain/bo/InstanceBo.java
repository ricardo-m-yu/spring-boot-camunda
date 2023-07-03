package com.sy.camunda.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;


@ApiModel("流程实例BO")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InstanceBo extends BaseBo {

    private Long initiatorId;

    private String initiatorName;

    private Long processId;

    @ApiModelProperty(notes = "流程名称")
    private String processName;

    @ApiModelProperty(notes = "流程编码")
    private String processCode;

    @ApiModelProperty(notes = "流程开始时间")
    private Date instanceStartTime;

    @ApiModelProperty(notes = "流程发起人")
    private Long instanceStartUser;

    @ApiModelProperty(notes = "流程发起人名字")
    private String instanceStartUserName;

    @ApiModelProperty(notes = "当前任务")
    private Long currTaskId;

    @ApiModelProperty(notes = "当前任务编号")
    private String currTaskCode;

    @ApiModelProperty(notes = "当前任务名称")
    private String currTaskName;

    @ApiModelProperty(notes = "当前任务执行人名称")
    private String currTaskAssigneeName;

    @ApiModelProperty(notes = "进度")
    private Integer progress;

    private Boolean success;

    private Date startTime;

    private Date endTime;

    private Long bizId;

    private Integer bizType;

    @ApiModelProperty(notes = "关联病人Id")
    private Long relatedId;

    @ApiModelProperty(notes = "关联病人姓名")
    private String relatedName;

    @ApiModelProperty(notes = "执行时间:单位-小时")
    private BigDecimal executeTime;

    @ApiModelProperty(notes = "状态")
    private Integer state;
}

