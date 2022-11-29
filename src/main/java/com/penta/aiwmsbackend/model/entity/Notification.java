package com.penta.aiwmsbackend.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "createdDate", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = true)
    private Board board;

    @OneToOne
    private User sentUser;

    @OneToOne
    @JoinColumn( name = "target_user_id" , nullable =  true )
    private User targetUser;

    @ManyToMany
    @JoinTable( name = "users_seen_notis" )
    private List<User> seenUsers;

    /*
     * edited for Invitiation status
     */

    @Column(name = "is_invitiation", nullable = true)
    private Boolean invitiation;
}
