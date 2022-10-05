package com.penta.aiwmsbackend.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "boards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    private Integer id;
    private String boardName;
    private Date createDate;
    private String image;
    private String description;

    @ManyToMany( targetEntity = User.class )
    @JoinTable( 
        name = "boards_has_users" ,
        joinColumns = @JoinColumn( name = "board_id" , referencedColumnName = "id" ),
        inverseJoinColumns =  @JoinColumn( name = "user_id" , referencedColumnName = "id" )
    )
    private List<User> users;

    @OneToMany( mappedBy = "board")
    private List<TaskCard> taskCards;

    @OneToMany( mappedBy = "board")
    private List<Stage> stages;
}