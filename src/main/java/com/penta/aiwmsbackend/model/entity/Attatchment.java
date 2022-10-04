package com.penta.aiwmsbackend.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "attachments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attatchment {
    @Id
    private Integer id;
    private String file;
    private Date createdDate;

    @ManyToOne
    @JoinColumn( name = "activity_id" )
    private Activity activity;
}
