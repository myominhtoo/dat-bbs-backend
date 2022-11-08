package com.penta.aiwmsbackend.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn( name = "board_id" )
    private Board board;
}
