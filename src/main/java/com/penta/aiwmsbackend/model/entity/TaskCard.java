package com.penta.aiwmsbackend.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task_cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "taskName", nullable = false)
    private String taskName;

    @Column(name = "description", nullable = true)
    private String description; 

    @Column(name = "bookMark", nullable = true)
    private boolean bookMark;
 
    @Column(name = "startedDate", nullable = true )
    // @JsonFormat
    private LocalDateTime startedDate;

    @Column(name = "endedDate", nullable = true )
    // @JsonFormat
    private LocalDateTime endedDate;

    @Column(name = "deleteStatus", nullable = false)
    private boolean deleteStatus;

    @ManyToMany
    @JoinTable(name = "users_has_tasks", joinColumns = @JoinColumn(name = "task_card_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> users;

    @OneToMany(mappedBy = "taskCard")
    // private List<Activity> activities;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

}
