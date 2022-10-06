package com.penta.aiwmsbackend.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    private Integer id;
    private String name;
    private boolean defaultStatus;

    @OneToMany( mappedBy = "stage" )
    private List<TaskCard> taskCards;

    @ManyToOne
    @JoinColumn( name = "board_id")
    private Board board;
}
