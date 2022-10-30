package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.Where;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.exception.custom.DuplicateTaskCardNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.service.BoardService;
import com.penta.aiwmsbackend.model.service.TaskCardService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskCardControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskCardService taskCardService;
    @MockBean
    private BoardService boardService;

    private static TaskCard taskCard;
    private static List<TaskCard> taskCards;
    private static Board board;

    @BeforeAll
    public static void doBeforeTests(){
        Board board1 = new Board();
        TaskCard taskCard1 = new TaskCard();
        taskCard1.setId(1);
        taskCard1.setTaskName("TaskCardName");
        taskCard1.setBookMark(false);
        taskCard1.setDescription("Description");
        taskCard1.setDeleteStatus(false);
        taskCard1.setStartedDate(LocalDate.now());
        taskCard1.setEndedDate(LocalDate.now());
        taskCard1.setBoard(board1);

        TaskCard taskCard2 = new TaskCard();
        Board board2 = new Board();
        taskCard2.setId(2);
        taskCard2.setTaskName("Task");
        taskCard2.setBookMark(false);
        taskCard2.setDescription("Desc");
        taskCard2.setDeleteStatus(false);
        taskCard2.setStartedDate(LocalDate.now());
        taskCard2.setEndedDate(LocalDate.now());
        taskCard2.setBoard(board2);

        taskCard= taskCard1;
        taskCards = new ArrayList<>();
        Collections.addAll(taskCards, taskCard1, taskCard2);
    }

    @Test
    public void createTaskTest() throws JsonProcessingException, Exception{
        when(this.taskCardService.createTask(taskCard)).thenReturn(taskCard);
        HttpResponse<TaskCard> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK ,
            HttpStatus.OK.value(),
            "Successfully Created!" ,
            "Ok" ,
            false,
            taskCard );

            MvcResult mvcResult = this.mockMvc.perform(post("/api/create-task").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(taskCard)))
                                 .andExpect(status().isOk()).andReturn();

           assertEquals(200, mvcResult.getResponse().getStatus());
   //        assertEquals(this.objectMapper.writeValueAsString(httpResponse),mvcResult.getResponse().getContentAsString());
           assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void updateTaskCardTest() throws JsonProcessingException, Exception{
        when(this.taskCardService.updateTaskCard(taskCard)).thenReturn(taskCard);
        HttpResponse<TaskCard> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK ,
            HttpStatus.OK.value(),
            "Successfully Updated!",
            "Ok",
            false,
            taskCard);
            MvcResult mvcResult = this.mockMvc.perform(put("/api/update-task").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(taskCard)))
                                 .andExpect(status().isOk()).andReturn();

           assertEquals(200, mvcResult.getResponse().getStatus());
        //    assertEquals(httpResponse,this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), HttpResponse.class));
          assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getTaskCardsTest() throws Exception{
        when(this.taskCardService.showAllTaskCard(1)).thenReturn(taskCards);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/boards/1/task-cards"))
                             .andExpect(status().isOk()).andReturn();

        assertEquals( 200 , mvcResult.getResponse().getStatus());
        //assertEquals( mvcResult.getResponse().getContentAsString(),this.objectMapper.writeValueAsString(taskCards));
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }
    
}
 