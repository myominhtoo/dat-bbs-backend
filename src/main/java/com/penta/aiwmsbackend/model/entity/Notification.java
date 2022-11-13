package com.penta.aiwmsbackend.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id 
    @GeneratedValue ( strategy = GenerationType.AUTO)
    private Integer id;

    @Column ( name = "content" )
    private String content;

    @Column ( name = "createdDate" , nullable = true )
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn( name = "board_id" )
    private Board board;

    @OneToOne
    private User sentUser;

    @OneToMany
    private List<User> seenUsers;
}
