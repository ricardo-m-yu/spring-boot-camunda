package com.sy.camunda.domain.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;


@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class BaseBo implements Serializable {

    private Date creationDate;

    private Date updatedDate;

    private Long id;

    private String createUsername;

    private Long createUser;

    private String updateUsername;

    private Long updateUser;
}