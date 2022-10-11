package com.penta.aiwmsbackend.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
    private Integer id;

    @OneToMany
    @JoinColumn( name = "board_id" )
    private List<Board> boards;

    @OneToMany
    @JoinColumn( name = "user_id")
    private List<User> users;

    @Column( name = "joined_status" , nullable = true )
    private boolean joinedStatus;

    @Column( name = "joined_date" , nullable =  true )
    private Date joinedDate;
}
