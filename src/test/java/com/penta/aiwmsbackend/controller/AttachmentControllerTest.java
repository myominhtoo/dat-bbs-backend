package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Activity;
import com.penta.aiwmsbackend.model.entity.Attachment;
import com.penta.aiwmsbackend.model.service.ActivityService;
import com.penta.aiwmsbackend.model.service.AttachmentService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private String FILE = System.getProperty("java.class.path").split(";")[0].replace("target\\test-classes", "")
    + "src\\main\\resources\\static\\attachments\\85312828logo-png.png";

    private static Attachment attachment;
    private static Activity activity;
    private static List<Attachment> attachments;

    @BeforeAll
    public static void doBeforeTests(){
        Activity activity1= new Activity();
        Attachment attachment1= new Attachment();
        attachment1.setId(1);
        attachment1.setCreatedDate(LocalDateTime.now());
        attachment1.setName("file");
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
            new FileInputStream(new java.io.File("D:\\Penta\\ai-wms-backend\\src\\main\\resources\\static\\attachments\\91105139bbms.png"))
        );
       when(this.attachmentService.uploadFile(attachment)).thenReturn(attachment);
        HttpResponse<Attachment> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK ,
            HttpStatus.OK.value() ,
            "Successfully uploaded File!" ,
            "OK!" ,
            true ,
            attachment
            );

                          
        MvcResult mvcResult = this.mockMvc.perform( multipart(HttpMethod.POST, "/api/activities/1/create-attachment"))
                              .andExpect(status().isOk())
                              .andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
    }

    @Test
    public void getAttachments() throws Exception{
        when(this.attachmentService.showAllAttachments(1)).thenReturn(attachments);
        MvcResult mvcResult= this.mockMvc.perform(get("/api/activities/1/attachments"))
                             .andExpect(status().isOk()).andReturn();

        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void deleteAttachment() throws Exception{
        when(this.attachmentService.deleteAttachment(1)).thenReturn(true);
        MvcResult mvcResult= this.mockMvc.perform(delete("/api/attachments/1"))
                            .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
                         
    }

}
