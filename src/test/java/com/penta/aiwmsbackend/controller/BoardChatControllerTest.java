package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardMessage;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.service.BoardChatService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardChatControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardChatService boardMessageService;

    private static BoardMessage boardMessage;
    private static List<BoardMessage> boardMessages;

    @BeforeAll
    public static void doBeforeTests(){
        User user1= new User();
        user1.setId(1);
        Board board1= new Board();
        board1.setId(1);
        BoardMessage boardMessage1= new BoardMessage();
        boardMessage1.setId(1);
        boardMessage1.setContent("I'm content");
        boardMessage1.setCreatedDate(LocalDateTime.now());
        boardMessage1.setFileUrl("localhost://8080/api/boards/1/board-messages");
        boardMessage1.setBoard(board1);
        boardMessage1.setUser(user1);

        BoardMessage boardMessage2= new BoardMessage();
        boardMessage2.setId(2);
        boardMessage2.setContent("I'm Boardmessage content");
        boardMessage2.setCreatedDate(LocalDateTime.now());
        boardMessage2.setBoard(board1);
        boardMessage2.setUser(user1);

        boardMessage= boardMessage1;
        boardMessages = new ArrayList<>();
        Collections.addAll(boardMessages, boardMessage1, boardMessage2);


    }
    @Test
    public void getBoardMessageByBoardId() throws Exception{
        when ( this.boardMessageService.getBoardMessageByBoardId(1)).thenReturn( boardMessages );
        MvcResult mvcResult = this.mockMvc.perform(get("/api/boards/1/board-messages"))
                             .andExpect(status().isOk())
                              .andReturn();

       assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
   
    }

}
