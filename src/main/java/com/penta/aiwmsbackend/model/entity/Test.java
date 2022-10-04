package com.penta.aiwmsbackend.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table( name = "test" )
@Data
public class Test {
    @Id
    private int id;
    private String name;
    private String youSee;

}
