package com.penta.aiwmsbackend.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.mail.MessagingException;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.exception.custom.CreatePermissionException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.exception.custom.JoinPermissionException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.service.BoardService;
import com.penta.aiwmsbackend.model.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private BoardService boardService;
    
    private static Board board;
    private static User user;
    
    private static List<Board> boards;
    
    @BeforeAll
    public static void doBeforeTests(){
       // RedirectView redirectView=new RedirectView();

        User user1= new User();
        Board board1= new Board();
        board1.setId(1);
        board1.setBoardName("BoardName");
        board1.setCode(11);
        board1.setDescription("Description");
        board1.setCreatedDate(LocalDateTime.now());
        board1.setDeleteStatus(false);
        board1.setUser(user1);

        User user2= new User();
        Board board2= new Board();
        board2.setId(1);
        board2.setBoardName("I'm Board");
        board2.setUser(user2);

        board=board1;
        boards= new ArrayList<>();
        Collections.addAll(boards,board1,board2);

    }
    @Test
    public void createBoardTest() throws JsonProcessingException, Exception{
       // when(this.boardService.createBoard(board)).thenReturn(true);
       
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "Successfully Created!",
                "Ok",
                true,
                true);

        MvcResult mvcResult = this.mockMvc.perform( post("/api/create-board").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(board)))
                              .andExpect(status().isOk()).andReturn();
        verify(this.boardService, times(1)).createBoard(board);
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertEquals( this.objectMapper.writeValueAsString(httpResponse) ,  mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getBoardTest() throws Exception{
        when(this.boardService.getBoardWithBoardId(1)).thenReturn(board);
        MvcResult mvcResult = this.mockMvc.perform(get("/api//boards/1"))
                             .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void inviteMembersTest() throws JsonProcessingException, Exception{
        when(this.boardService.inviteMembers(board)).thenReturn(true);
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                HttpStatus.OK ,
                HttpStatus.OK.value() ,
                "Successfully Invited!",
                "OK",
                true,
                true);
        MvcResult mvcResult = this.mockMvc.perform( post("/api/boards/1/invite-members").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(board)))
                              .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertEquals( this.objectMapper.writeValueAsString(httpResponse) ,  mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void joinBoardTest() throws JsonProcessingException, Exception{
        RedirectView redirectView=new RedirectView("https://localhost:4200");
        
        when(this.boardService.joinBoard("email@gmail.com", 1, 1)).thenReturn(redirectView);
        MvcResult mvcResult = this.mockMvc.perform( get("/api/join-board?email=email@gmail.com&code=1&boardId=1"))
                              .andExpect(status().is(302)).andReturn();
        assertEquals( 302 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }
}
