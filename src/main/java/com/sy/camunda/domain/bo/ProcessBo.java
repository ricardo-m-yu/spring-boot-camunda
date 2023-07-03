package com.sy.camunda.domain.bo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@ApiModel("流程BO")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessBo extends BaseBo {

    @ApiModelProperty(notes = "编码")
    private String code;

    @ApiModelProperty(notes = "文件名")
    private String fileName;

    @ApiModelProperty(notes = "描述")
    private String comment;

    @ApiModelProperty(notes = "名称")
    private String name;

    @ApiModelProperty(notes = "业务类型")
    private Integer bizType;

    private Boolean available;
    private List<TaskBo> tasks;
    private List<InstanceBo> instances;
}
