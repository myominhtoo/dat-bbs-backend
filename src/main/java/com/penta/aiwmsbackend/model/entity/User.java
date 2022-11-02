package com.penta.aiwmsbackend.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "username", nullable = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "imageUrl", nullable = true)
    private String imageUrl;

    @Column(name = "joinedDate", nullable = true)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinedDate;

    @Column(name = "position", nullable = true)
    private String position;

    @Column(name = "gender", nullable = true)
    private int gender;

    @Transient
    private MultipartFile image;
    @Transient
    private String confirmpassword;

    @Column(name = "deleteStatus", nullable = true)
    private boolean deleteStatus;

    /*
     * edited for token
     */

    @Column(name = "code")
    private Integer code;

    @Column(name = "valid_user")
    private boolean validUser;

    /*
     * edited for bio
     */
    @Column(name = "bios")
    private String bio;

    // @ManyToMany( targetEntity = Board.class , mappedBy = "users")
    // private List<Board> boards;

    // @ManyToMany( targetEntity = TaskCard.class , mappedBy = "users")
    // private List<TaskCard> cards;

    // @OneToMany( mappedBy = "user" )
    // private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Chat> chats;

}
