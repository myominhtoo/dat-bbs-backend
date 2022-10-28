package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.IOException;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;
import com.penta.aiwmsbackend.exception.custom.CustomFileNotFoundException;
import com.penta.aiwmsbackend.exception.custom.InvalidActivityIdException;
import com.penta.aiwmsbackend.exception.custom.MultipartFileNotFoundException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Activity;
import com.penta.aiwmsbackend.model.entity.Attachment;
import com.penta.aiwmsbackend.model.service.ActivityService;
import com.penta.aiwmsbackend.model.service.AttachmentService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import ch.qos.logback.core.status.Status;

@SpringBootTest
@AutoConfigureMockMvc
public class AttachmentControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityService activityService;
    @MockBean
    private AttachmentService attachmentService;

    private static Attachment attachment;
    private static Activity activity;
    private static List<Attachment> attachments;

    @BeforeAll
    public static void doBeforeTests(){
        Activity activity1= new Activity();
        Attachment attachment1= new Attachment();
        attachment1.setId(1);
        attachment1.setCreatedDate(LocalDateTime.now());
        attachment1.setFileUrl("https://localhost:4200");
        attachment1.setActivity(activity);

        Activity activity2= new Activity();
        Attachment attachment2= new Attachment();
        attachment2.setId(2);
        attachment2.setCreatedDate(LocalDateTime.now());
        attachment2.setFileUrl("https://localhost:4200");
        attachment2.setActivity(activity);

        attachment=attachment1;
        attachments=new ArrayList();
        Collections.addAll(attachments,attachment1,attachment2);

      
    }
    @Test
    public void uploadFileTest() throws Exception{
        MockMultipartFile multipartFile=new MockMultipartFile(
            "file",
            "file.png",
            MediaType.IMAGE_PNG_VALUE,
            new FileInputStream(new java.io.File("D:\\Penta\\ai-wms-backend\\src\\main\\resources\\static\\attachments\\85312828logo-png.png"))
        );
        when(this.attachmentService.uploadFile(1, multipartFile)).thenReturn(attachment);
        HttpResponse<Attachment> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK ,
            HttpStatus.OK.value() ,
            "Successfully uploaded File!" ,
            "OK!" ,
            true ,
            attachment
            );

                          
        MvcResult mvcResult = this.mockMvc.perform( multipart(HttpMethod.POST, "/api/activities/1/create-attachment").file(multipartFile) )
                              .andExpect(status().isOk())
                              .andReturn();

        assertEquals( 200 , mvcResult.getResponse().getStatus());
    }


}
