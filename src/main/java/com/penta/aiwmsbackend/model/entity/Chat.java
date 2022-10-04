package com.penta.aiwmsbackend.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "chats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat{
    @Id
    private Integer id;
    private String message;
    private Date createdDate;

    @ManyToOne
    @JoinColumn( name = "user_id")
    private User user;

    @OneToOne
    private Board board;
}
