package com.sy.camunda.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TaskSearch {

    private String instanceId;
    private String code;
    private String name;
    private String keyword;
    private Boolean currentUser = true;
}
