package com.penta.aiwmsbackend.model.entity;

import java.time.LocalDateTime;

import javax.mail.Multipart;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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

    @Column ( name = "fileUrl" , nullable = true )
    private String fileUrl;

    @Column ( name = "createdDate" , nullable = true)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @Transient
    private Multipart file;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;
}
