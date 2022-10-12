package com.penta.aiwmsbackend.model.entity;

import java.util.Date;
import java.util.List;

import javax.mail.Multipart;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Integer id;

    @Column ( name = "boardName" , nullable= false )
    private String boardName;

    @Column ( name = "createdDate" , nullable= true )
    private Date createdDate;

    @Column ( name = "imageUrl" , nullable = true )
    private String imageUrl;

    @Column ( name = "description" , nullable = true )
    private String description;

    @Transient
    private Multipart image;

    @Column ( name = "deleteStatus" , nullable = false )
    private boolean deleteStatus;

    @Column( name = "code" , nullable = true )
    private Integer code;

    /*
     * edited to get user who create board
     */
    @OneToOne
    private User user;

    @Transient
    private String [] invitedEmails;

    // @ManyToMany( targetEntity = User.class )
    // @JoinTable( 
    //     name = "boards_has_users" ,
    //     joinColumns = @JoinColumn( name = "board_id" , referencedColumnName = "id" ),
    //     inverseJoinColumns =  @JoinColumn( name = "user_id" , referencedColumnName = "id" )
    // )
    // private List<User> users;

    @OneToMany( mappedBy = "board")
    private List<TaskCard> taskCards;

    @OneToMany( mappedBy = "board")
    private List<Stage> stages;

   
}
