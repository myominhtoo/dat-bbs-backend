package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.exception.custom.DuplicateActivityNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidTaskCardIdException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Activity;
import com.penta.aiwmsbackend.model.entity.TaskCard;
import com.penta.aiwmsbackend.model.service.ActivityService;
import com.penta.aiwmsbackend.model.service.TaskCardService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ActivityControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;
    @MockBean
    private TaskCardService taskCardService;

    private static Activity activity;
    private static TaskCard taskCard;
    private static List<Activity> activities;

    @BeforeAll
    public static void doBeforeTests() {
        TaskCard taskCard1 = new TaskCard();
        Activity activity1 = new Activity();
        activity1.setId(1);
        activity1.setActivityName("ActivityName");
        activity1.setStatus(false);
        activity1.setStartedDate(LocalDateTime.now());
        activity1.setEndedDate(LocalDateTime.now());
        activity1.setTaskCard(taskCard1);

        TaskCard taskCard2 = new TaskCard();
        Activity activity2 = new Activity();
        activity2.setId(2);
        activity2.setActivityName("Activity");
        activity2.setStatus(false);
        activity2.setStartedDate(LocalDateTime.now());
        activity2.setEndedDate(LocalDateTime.now());
        activity2.setTaskCard(taskCard2);

        activity = activity1;
        activities = new ArrayList<>();
        Collections.addAll(activities, activity1, activity2);
    }

    @Test
    public void createActivityTest() throws JsonProcessingException, Exception{
        Activity activity = new Activity();
        when(this.activityService.createActivity(activity)).thenReturn(activity);
        HttpResponse<Activity> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                HttpStatus.OK ,
                HttpStatus.OK.value(),
                "Successfully Added!",
                "Ok",
                false  ,
                activity
            );
        MvcResult mvcResult = this.mockMvc.perform(post("/api/create-activity").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(activity)))
                             .andExpect(status().isOk()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getActivitiesTest() throws Exception{
        when(this.activityService.showActivities(1)).thenReturn(activities);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/task-card/1/activities"))
                             .andExpect(status().isOk()).andReturn();

        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test 
    public void getActivityIdByTaskIdTest() throws Exception{
        when(this.activityService.findByActivityId(1)).thenReturn(activity);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/task-card/1/activities/1"))
                             .andExpect(status().isOk()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void updateActivityTest() throws JsonProcessingException, Exception{
        Activity activity= new Activity();
        when(this.activityService.updateActivity(activity)).thenReturn(activity);
        HttpResponse<Activity> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK ,
            HttpStatus.OK.value(),
            "Successfully Updated!",
            "Ok",
            false,
            activity);
            MvcResult mvcResult = this.mockMvc.perform(put("/api/update-activity").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(activity)))
                                 .andExpect(status().isOk()).andReturn();

           assertEquals(200, mvcResult.getResponse().getStatus());
          assertNotNull(mvcResult.getResponse().getContentAsString());
    }

}
