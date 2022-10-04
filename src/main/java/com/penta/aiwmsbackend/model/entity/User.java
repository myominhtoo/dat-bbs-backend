package com.penta.aiwmsbackend.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "users" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String image;
    private Date joinedDate;

    @ManyToMany( targetEntity = Board.class ,  mappedBy = "users")
    private List<Board> boards;

    @ManyToMany( targetEntity = TaskCard.class , mappedBy = "users")
    private List<TaskCard> cards;

    @OneToMany( mappedBy = "user" )
    private List<Comment> comments;

    @OneToMany( mappedBy = "user" )
    private List<Chat> chats;

}
