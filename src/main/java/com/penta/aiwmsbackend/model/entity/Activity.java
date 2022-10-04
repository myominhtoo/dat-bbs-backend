package com.penta.aiwmsbackend.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "activities" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    @Id
    private Integer id;
    private String activityName;
    private boolean status;
    private Date startedDate;
    private Date endedDate;

    @OneToMany( mappedBy = "activity")
    private List<Attatchment> attatchments;

    @ManyToOne
    @JoinColumn( name = "task_card_id" )
    private TaskCard taskCard;
}
