package com.penta.aiwmsbackend.model.entity;

import java.util.Date;
import java.util.List;

import javax.mail.Multipart;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
    
    @Column( name = "email" , nullable =  false , unique = true )
    private String email;

    private String password;
    
    private String phone;
    private String imageUrl;
    private Date joinedDate;
    private String position;
    private int gender;

    @Transient
    private Multipart image;

    @ManyToMany( targetEntity = Board.class ,  mappedBy = "users")
    private List<Board> boards;

    @ManyToMany( targetEntity = TaskCard.class , mappedBy = "users")
    private List<TaskCard> cards;

    @OneToMany( mappedBy = "user" )
    private List<Comment> comments;

    @OneToMany( mappedBy = "user" )
    private List<Chat> chats;

    private boolean deleteStatus;

}
