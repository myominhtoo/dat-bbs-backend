package com.penta.aiwmsbackend.model.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue( strategy =  GenerationType.AUTO )
    private Integer id;

    @Column ( name = "activityName" , nullable = false )
    private String activityName; 

    @Column ( name = "status" , nullable =  false)
    private boolean status;

    @Column ( name = "startedDate" , nullable = true )
    private LocalDate startedDate;
    
    @Column ( name = "endedDate" , nullable = true )
    private LocalDate endedDate;

    @OneToMany(mappedBy = "activity")
    private List<Attachment> attatchments;

    @ManyToOne
    @JoinColumn(name = "task_card_id")
    private TaskCard taskCard;
}
