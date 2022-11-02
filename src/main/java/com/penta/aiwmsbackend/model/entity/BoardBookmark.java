package com.penta.aiwmsbackend.model.entity;

import java.time.LocalDateTime;

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
@Table(name = "board_bookmarks")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class BoardBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private User user;

    @OneToOne
    private Board board;

    private LocalDateTime createdDate;
}
