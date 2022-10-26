package com.penta.aiwmsbackend.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.exception.custom.DuplicateStageNameInBoardException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.Stage;
import com.penta.aiwmsbackend.model.service.BoardService;
import com.penta.aiwmsbackend.model.service.StageService;

@SpringBootTest
@AutoConfigureMockMvc
public class StageControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StageService stageService;
    @MockBean
    private BoardService boardService;

    private static Stage stage;
    private static List<Stage> stages;
    private static Board board;

    @BeforeAll
    public static void doBeforeTests(){
        Board board1 = new Board();
        Stage stage1 = new Stage();
        stage1.setId(1);
        stage1.setStageName("StageName");
        stage1.setDefaultStatus(false);
        stage1.setBoard(board1);

        Board board2 = new Board();
        Stage stage2 = new Stage();
        stage2.setId(2);
        stage2.setStageName("Stage");
        stage2.setDefaultStatus(false);
        stage2.setBoard(board2);
        
        stage=stage1;
        stages= new ArrayList();
        Collections.addAll(stages,stage1,stage2);

    }

    @Test
    public void getStagesTest() throws Exception{
        when(this.stageService.getStages()).thenReturn(stages);
        MvcResult mvcResult =   this.mockMvc.perform( get("/api/stages"))
                                .andExpect(status().isOk()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals( mvcResult.getResponse().getContentAsString(),this.objectMapper.writeValueAsString(stages));
        //assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void createActivity() throws JsonProcessingException, Exception{
        when(this.stageService.createCustomStage(stage)).thenReturn(stage);
        HttpResponse<Stage> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK ,
            HttpStatus.OK.value() ,
            "Successfully Created!" ,
            "OK" ,
            false ,
            stage
        );
        MvcResult mvcResult = this.mockMvc.perform(post("/api/create-stage").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(stage)))
                             .andExpect(status().isOk()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
       // assertEquals(httpResponse,this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), HttpResponse.class));
        assertNotNull(mvcResult.getResponse().getContentAsString());

    }
    @Test
    public void updateStageTest() throws JsonProcessingException, Exception{
        when(this.stageService.updateCustomStage(stage)).thenReturn(stage);
        HttpResponse<Stage> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK ,
            HttpStatus.OK.value(),
            "Successfully Updated! ",
            "OK",
            false,
            stage);
            MvcResult mvcResult = this.mockMvc.perform(put("/api/update-stage").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(stage)))
                                 .andExpect(status().isOk()).andReturn();

           assertEquals(200, mvcResult.getResponse().getStatus());
           assertNotNull(mvcResult.getResponse().getContentAsString());
    }

}
