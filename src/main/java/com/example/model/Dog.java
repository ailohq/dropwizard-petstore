package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;

import javax.persistence.*;

@Entity
@Table(name = "dog")
@ApiModel(value = "Representation of a Dog")
public class Dog {

    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @JsonProperty
    private String name;

}
