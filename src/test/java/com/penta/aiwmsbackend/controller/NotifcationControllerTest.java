package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.model.entity.Notification;
import com.penta.aiwmsbackend.model.service.NotificationService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NotifcationControllerTest {
    
    @MockBean
    private NotificationService notiService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void showNotificationsTest() throws Exception{
        List<Notification> notis = new ArrayList<>();
        Notification noti = new Notification();
        noti.setId(null);
        
        when(this.notiService.getNotificationsWithUserId(1)).thenReturn(notis);
        MvcResult mvcResult = this.mockMvc.perform( get("/api/users/1/notifications") )
                              .andExpect(status().isOk())
                              .andReturn();
        assertTrue( mvcResult.getResponse().getStatus() == 200 );
        assertEquals( this.objectMapper.writeValueAsString(notis), mvcResult.getResponse().getContentAsString());                              
    }

}
