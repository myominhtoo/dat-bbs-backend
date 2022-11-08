package com.penta.aiwmsbackend.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "board_messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMessage{
    @Id
    private Integer id;

    @Column ( name = "content" , nullable = true )
    private String content;
    
    @Column ( name = "createdDate" , nullable = true )
    private LocalDateTime createdDate;

    @Column( name = "file_url" , nullable =  true )
    private String fileUrl;

    @Transient
    private MultipartFile file;

    @ManyToOne
    @JoinColumn( name = "user_id")
    private User user;

    @OneToOne
    private Board board;
}
