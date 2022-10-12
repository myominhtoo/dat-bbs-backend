package com.penta.aiwmsbackend.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "boards_has_users" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardsHasUsers {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Integer id;

    @OneToOne
    private Board board;

    @OneToOne
    private User user;

    @Column( name = "joined_status" , nullable = true )
    private boolean joinedStatus;

    @Column( name = "joined_date" , nullable =  true )
    private Date joinedDate;
}
