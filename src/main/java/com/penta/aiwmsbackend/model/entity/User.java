package com.penta.aiwmsbackend.model.entity;

import java.util.Date;
import java.util.List;

import javax.mail.Multipart;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Integer id;

    @Column ( name = "username" , nullable = true )
    private String username;
    
    @Column ( name = "email" , nullable =  false , unique = true )
    private String email;
    
    @Column ( name = "password" , nullable = true )
    private String password;
    
    @Column ( name = "phone" , nullable = true )
    private String phone;

    @Column ( name = "imageUrl" , nullable = true )
    private String imageUrl;

    @Column ( name = "joinedDate" , nullable = true )
    private Date joinedDate;

    @Column ( name = "position" , nullable = true )
    private String position;

    @Column ( name= "gender" , nullable = true )
    private int gender;

    @Transient
    private Multipart image;
    
    @Column ( name= "deleteStatus" , nullable = true)
    private boolean deleteStatus;

    /*
     * edited for token
     */

    @Column( name = "code" )
    private Integer code;

    @Column( name = "valid_user")
    private boolean validUser;

    @ManyToMany( targetEntity = Board.class ,  mappedBy = "users")
    private List<Board> boards;

    @ManyToMany( targetEntity = TaskCard.class , mappedBy = "users")
    private List<TaskCard> cards;

    @OneToMany( mappedBy = "user" )
    private List<Comment> comments;

    @OneToMany( mappedBy = "user" )
    private List<Chat> chats;

    

}
