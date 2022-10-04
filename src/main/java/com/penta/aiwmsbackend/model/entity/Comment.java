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
@Table( name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    private Integer id;
    private String comment;
    private Date createdDate;

    @ManyToOne
    @JoinColumn( name = "user_id" )
    private User user;

    @OneToOne
    private TaskCard taskCard;
}
