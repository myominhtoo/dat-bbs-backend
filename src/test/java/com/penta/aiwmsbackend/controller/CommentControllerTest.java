package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Comment;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.service.CommentService;
import com.penta.aiwmsbackend.model.service.TaskCardService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;
    @MockBean
    private TaskCardService taskCardService;
    

    private static Comment comment;
    private static List<Comment> comments;

    @BeforeAll
    public static void doBeforeTests(){
        User user1=new User();
        Comment comment1 = new Comment();
        TaskCard taskCard1 = new TaskCard();
        taskCard1.setId(1);
        comment1.setId(1);
        comment1.setComment("Hello");
        comment1.setCreatedDate(LocalDateTime.now());
        comment1.setTaskCard(taskCard1);
        comment1.setUser(user1);

        Comment comment2 = new Comment();
        TaskCard taskCard2 = new TaskCard();
        taskCard2.setId(2);
        comment2.setId(2);
        comment2.setComment("Hi");
        comment2.setCreatedDate(LocalDateTime.now());
        comment2.setTaskCard(taskCard2);

        comment=comment1;
        comments = new ArrayList<>();
        Collections.addAll(comments,comment1,comment2);
        
    }
    
    @Test
    @WithMockUser
    public void createCommentTest() throws JsonProcessingException, Exception{
        when(this.commentService.createComment(comment)).thenReturn(null);
        MvcResult mvcResult = this.mockMvc.perform(post("/api/create-comment").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(comment)))
                           .andExpect(status().isBadRequest()).andReturn();
                           
    assertEquals(400, mvcResult.getResponse().getStatus());
    assertNotNull(mvcResult.getResponse().getContentAsString());
   }

    @Test
    @WithMockUser
    public void showCommentsTest() throws Exception{
        when(this.commentService.showComments(1)).thenReturn(comments);
        MvcResult mvcResult = this.mockMvc.perform( get("/api/tasks/1/comments"))
                              .andExpect(status().isOk())
                              .andReturn();

       assertEquals( 200 , mvcResult.getResponse().getStatus());
       assertEquals( mvcResult.getResponse().getContentAsString(),this.objectMapper.writeValueAsString(comments));
       assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void deleteComment() throws JsonProcessingException, Exception{
        when(this.commentService.deleteComment(1)).thenReturn(true);
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK ,
            HttpStatus.OK.value() ,
            "Successfully Deleted!" ,
            "Ok",
            true,
            true
        );
       
        MvcResult mvcResult= this.mockMvc.perform(delete("/api/comment/delete-comment?id=1").contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
                         
    }

    @Test
    @WithMockUser
    public void updateComment() throws JsonProcessingException, Exception{
        Comment comment = new Comment();
          when (this.commentService.updateComment(comment)).thenReturn(comment);
          HttpResponse<Comment> httpResponse= new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK ,
            HttpStatus.OK.value(),
            "Successfully Edited!" ,
            "OK",
            false,
            comment);
            MvcResult mvcResult = this.mockMvc.perform(put("/api/update-comment").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(comment)))
            .andExpect(status().isOk())
            .andReturn();
            assertEquals( 200 , mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult.getResponse().getContentAsString());
    }
  
 
}


