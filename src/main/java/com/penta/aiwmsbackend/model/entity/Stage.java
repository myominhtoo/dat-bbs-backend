package com.penta.aiwmsbackend.model.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "stages" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stage {
    @Id
    @GeneratedValue( strategy =  GenerationType.AUTO )
    private Integer id;

    @Column ( name = "stageName" , nullable = false )
    private String stageName;

    @Column ( name = "defaultStatus" , nullable = false )
    private boolean defaultStatus;

    // @OneToMany( mappedBy = "stage" )
    // private List<TaskCard> taskCards;

    @ManyToOne
    @JoinColumn( name = "board_id")
    private Board board;
}
