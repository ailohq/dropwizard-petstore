package com.example.model;

import javax.validation.constraints.NotNull;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dog")
@ApiModel(value = "Representation of a Dog")
public class Dog {

    @Id
    @JsonProperty
    @NotNull
    @ApiModelProperty(required=true)
    private Integer id;

    @Column
    @JsonProperty
    private String message;

}
