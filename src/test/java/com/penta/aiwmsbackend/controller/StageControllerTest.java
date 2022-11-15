package com.penta.aiwmsbackend.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        stages= new ArrayList<>();
        Collections.addAll(stages,stage1,stage2);

    }

    @Test
    @WithMockUser
    public void getStagesTest() throws Exception{
        when(this.stageService.getStages()).thenReturn(stages);
        MvcResult mvcResult =   this.mockMvc.perform( get("/api/stages"))
                                .andExpect(status().isOk()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals( mvcResult.getResponse().getContentAsString(),this.objectMapper.writeValueAsString(stages));
        //assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void createStageTest() throws JsonProcessingException, Exception{
        when(this.stageService.createCustomStage(stage)).thenReturn(stage);
        MvcResult mvcResult = this.mockMvc.perform(post("/api/create-stage").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(stage)))
                             .andExpect(status().isOk()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
       // assertEquals(httpResponse,this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), HttpResponse.class));
        assertNotNull(mvcResult.getResponse().getContentAsString());

    }
    
    @Test
    @WithMockUser
    public void updateStageTest() throws JsonProcessingException, Exception{
        when(this.stageService.updateCustomStage(stage)).thenReturn(stage);
            MvcResult mvcResult = this.mockMvc.perform(put("/api/update-stage").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(stage)))
                                 .andExpect(status().isOk()).andReturn();

           assertEquals(200, mvcResult.getResponse().getStatus());
           assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void deleteStageTest() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform( delete("/api/delete-stage?id=1"))
                              .andExpect(status().isOk())
                              .andReturn();
        verify(this.stageService , times(1)).deleteStage(1);
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

}
