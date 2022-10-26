package com.penta.aiwmsbackend.controller;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void doBeforeTests(){
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
    public void createBoardTest(){
    
    }


}
