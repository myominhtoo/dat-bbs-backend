package com.penta.aiwmsbackend.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "task_cards")
@Data
@AllArgsConstructor
@NoArgsConstructor 
public class TaskCard {
    @Id
    private Integer id;
    private String taskName;
    private String description;
    private boolean bookMark;
    private Date startedDate;
    private Date endedDate;

    @ManyToMany
    @JoinTable( 
        name = "users_has_tasks" ,
        joinColumns = @JoinColumn( name = "task_card_id" , referencedColumnName = "id" ) ,
        inverseJoinColumns =  @JoinColumn( name = "user_id" , referencedColumnName = "id" ) )
    private List<User> users;

    @OneToMany( mappedBy = "taskCard")
    private List<Activity> activities;

    @ManyToOne
    @JoinColumn( name = "board_id" )
    private Board board;

    @ManyToOne
    @JoinColumn( name = "stage_id" )
    private Stage stage;

    private boolean deleteStatus;
}
