package com.penta.aiwmsbackend.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.penta.aiwmsbackend.model.entity.Comment;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.CommentRepo;
import com.penta.aiwmsbackend.model.service.CommentService;

@SpringBootTest
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepo commentRepo;

    Comment comment;

    LocalDateTime now = LocalDateTime.now();

    @Test
    void createCommentTest() {

        MockitoAnnotations.initMocks(this);

        Comment cm = new Comment();

        cm.setId(1);
        cm.setComment("sppl");
        cm.setCreatedDate(now);
        cm.setUser(new User());
        cm.setTaskCard(new TaskCard());

        Comment cm1 = new Comment();

        cm1.setId(2);
        cm1.setComment("spplarpop");
        cm1.setCreatedDate(now);
        cm1.setUser(new User());
        cm1.setTaskCard(new TaskCard());

        List<Comment> commentList = new ArrayList<>();
        commentList.add(cm);
        commentList.add(cm1);

    }

    @Test
    void getCommentTest() throws Exception {

        List<Comment> com = new ArrayList<>();

        Comment cm = new Comment();

        cm.setId(1);
        cm.setComment("sppl");
        cm.setCreatedDate(now);
        cm.setUser(new User());

        TaskCard tk = new TaskCard();
        tk.setId(99);

        cm.setTaskCard(tk);

        Comment cm1 = new Comment();

        cm1.setId(2);
        cm1.setComment("spplarpop");
        cm1.setCreatedDate(now);
        cm1.setUser(new User());

        // TaskCard tk1 = new TaskCard();
        // tk.setId(99);

        cm1.setTaskCard(tk);

        com.add(cm);
        com.add(cm1);

        // List<Comment> com1 = new ArrayList<>();

        when(commentRepo.findCommentByTaskCardId(99)).thenReturn(com);
        // this.commentService.showComments(99);
        // com= commentRepo.findCommentByTaskCardId(99);

        assertNotNull(this.commentService.showComments(99));

    }

}
