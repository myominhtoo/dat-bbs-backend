package com.penta.aiwmsbackend.model.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id 
    @GeneratedValue ( strategy = GenerationType.AUTO)
    private Integer id;

    @Column ( name = " comment" , nullable = true )
    private String comment;

    @Column ( name = "createdDate" , nullable = false )
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn( name = "user_id" )
    private User user;

    @OneToOne
    private TaskCard taskCard;
}
