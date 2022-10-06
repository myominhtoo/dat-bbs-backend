package com.penta.aiwmsbackend.model.entity;

import java.util.Date;

import javax.mail.Multipart;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attachments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    @Id
    private Integer id;
    private String fileUrl;
    private Date createdDate;

    @Transient
    private Multipart file;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;
}
