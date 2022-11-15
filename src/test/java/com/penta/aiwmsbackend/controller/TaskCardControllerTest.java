package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jfree.data.gantt.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.jasperReport.TaskCardReportService;
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
    private TaskCardReportService taskCardReportService;

    @MockBean
    private BoardService boardService;

    private static TaskCard taskCard;
    private static List<TaskCard> taskCards;

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
    @WithMockUser
    public void createTaskTest() throws JsonProcessingException, Exception{
        when(this.taskCardService.createTask(taskCard)).thenReturn(taskCard);
        MvcResult mvcResult = this.mockMvc.perform(post("/api/create-task").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(taskCard)))
                                 .andExpect(status().isOk()).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void updateTaskCardTest() throws JsonProcessingException, Exception{
        when(this.taskCardService.updateTaskCard(taskCard)).thenReturn(taskCard);
         MvcResult mvcResult = this.mockMvc.perform(put("/api/update-task").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(taskCard)))
                                 .andExpect(status().isOk()).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void getTaskCardsTest() throws Exception{
        when(this.taskCardService.showAllTaskCard(1)).thenReturn(taskCards);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/boards/1/task-cards"))
                             .andExpect(status().isOk()).andReturn();

        assertEquals( 200 , mvcResult.getResponse().getStatus());
        //assertEquals( mvcResult.getResponse().getContentAsString(),this.objectMapper.writeValueAsString(taskCards));
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    @WithMockUser
    public void assignTaskToMembersTest() throws JsonProcessingException, Exception{
        when( this.taskCardService.assignTasksToMembers(taskCard) ).thenReturn(taskCard);
        MvcResult mvcResult = this.mockMvc.perform( put("/api/tasks/1/assign-task").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(taskCard)))
                              .andExpect( status().isOk() )
                              .andReturn();
        assertTrue( mvcResult.getResponse().getStatus() == 200 );
        assertNotNull( mvcResult.getResponse().getContentAsString() );
    }

    @Test
    @WithMockUser
    public void showMyTasksTest() throws Exception{
        when(this.taskCardService.showMyTasks(1)).thenReturn(taskCards);
        MvcResult mvcResult = this.mockMvc.perform( get("/api/users/1/task-cards") )
                              .andExpect(status().isOk())
                              .andReturn();
        assertTrue( mvcResult.getResponse().getStatus() == 200 );
        assertNotNull( mvcResult.getResponse().getContentAsString() );
    }

    @Test
    @WithMockUser
    public void generateReportTest() throws Exception{
        when(this.taskCardReportService.exportTaskReport("pdf")).thenReturn("report generated in path D:\\Penta\\JasperReport");
        MvcResult mvcResult = this.mockMvc.perform( get("/api/boards/1/reportTask?format=pdf") )
                              .andExpect( status().isOk() )
                              .andReturn();
        assertTrue( mvcResult.getResponse().getStatus() == 200 );
        assertNotNull( mvcResult.getResponse().getContentAsString() );
    }

    @Test
    @WithMockUser
    public void updateDeleteStatuTaskCardById() throws Exception {
        when(this.taskCardService.updateDeleteStatusTaskCard(1)).thenReturn(taskCard);
        MvcResult mvcResult = this.mockMvc.perform( get("/api/boards/1/task-cards?id=1"))
                              .andExpect( status().isOk() )
                              .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void showDeleteStatusTaskCard() throws Exception{
        when(this.taskCardService.showDeleteStatusTaskCard(1)).thenReturn(taskCards);
        MvcResult mvcResult = this.mockMvc.perform( get("/api/boards/1/archive-tasks"))
                            .andExpect( status().isOk() )
                            .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
        
    }

    @Test
    @WithMockUser
    public void restoreTask() throws Exception{
        when(this.taskCardService.restoreTaskCard(1)).thenReturn(taskCard);
        MvcResult mvcResult = this.mockMvc.perform( put("/api/boards/1/restore-tasks?id=1"))
                            .andExpect( status().isOk() )
                            .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());


    }

}
 