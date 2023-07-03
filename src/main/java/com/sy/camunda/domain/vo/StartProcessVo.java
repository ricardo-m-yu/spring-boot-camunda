package com.sy.camunda.domain.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ApiModel("开启流程VO")
public class StartProcessVo {

    @NotEmpty(message = "流程key 不能为空!")
    @ApiModelProperty("流程key")
    private String processKey;

    @ApiModelProperty("参数 - map结构 <key, value>")
    private Map<String, Object> params;
}
