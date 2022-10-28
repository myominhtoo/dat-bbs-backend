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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  startedDate;
    
    @Column ( name = "endedDate" , nullable = true )
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endedDate;

    @Column( name = "deleteStatus" , nullable = true )
    private boolean deleteStatus;

    // @OneToMany(mappedBy = "activity")
    // private List<Attachment> attatchments;

    @ManyToOne
    @JoinColumn(name = "task_card_id")
    private TaskCard taskCard;
}
