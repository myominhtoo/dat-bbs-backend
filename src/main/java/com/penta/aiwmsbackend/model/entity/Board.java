package com.penta.aiwmsbackend.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.mail.Multipart;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

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

    /*
     * added for board icon color 
     */
    @Column( name = "icon_color" , nullable =  false )
    private String iconColor;


    @ManyToMany
    @JoinTable( name = "users_archive_boards" )
    private List<User> archivedUsers;
    
}
