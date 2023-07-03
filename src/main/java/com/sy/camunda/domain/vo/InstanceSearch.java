package com.sy.camunda.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class InstanceSearch {
    private String keyword;
    private Date startTime;
    private Date endTime;
    @ApiModelProperty(hidden = true)
    private boolean current;
    @ApiModelProperty(hidden = true)
    private boolean currentUser;
    private Long instanceId;
    @ApiModelProperty("订单类型")
    private Integer bizType;
    @ApiModelProperty("订单ID")
    private Long bizId;
}
