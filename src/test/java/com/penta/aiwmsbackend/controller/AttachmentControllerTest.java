package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.io.FileInputStream;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.model.entity.Activity;
import com.penta.aiwmsbackend.model.entity.Attachment;
import com.penta.aiwmsbackend.model.service.ActivityService;
import com.penta.aiwmsbackend.model.service.AttachmentService;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;

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
        attachment1.setActivity(activity1);

        Activity activity2= new Activity();
        Attachment attachment2= new Attachment();
        attachment2.setId(2);
        attachment2.setCreatedDate(LocalDateTime.now());
        attachment2.setFileUrl("https://localhost:4200");
        attachment2.setActivity(activity2);

        attachment=attachment1;
        activity = activity1;
        attachments = new ArrayList<>();
        
        Collections.addAll(attachments,attachment1,attachment2);     
    }
    @Test 
    @WithMockUser
    public void uploadFileTest() throws Exception{
         Attachment attachment = new Attachment();
        String pathname = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                          .replace("target\\test-classes", "")+"src\\main\\resources\\static\\attachments\\";
        MockMultipartFile multipartFile=new MockMultipartFile(
            "file",
            "file.png",
            MediaType.IMAGE_PNG_VALUE,
            new FileInputStream(new java.io.File(pathname+"17455922download (4).png"))
        );
        
       when(this.attachmentService.uploadFile(attachment)).thenReturn(attachment);
      
        MvcResult mvcResult = this.mockMvc.perform( multipart(HttpMethod.POST, "/api/activities/1/create-attachment").part(new MockPart("data" , this.objectMapper.writeValueAsBytes(attachment)) ) .part(new MockPart("file", multipartFile.getBytes())))
                           //   .andExpect(status().isOk())
                              .andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    @WithMockUser
    public void getAttachments() throws Exception{
        when(this.attachmentService.showAllAttachments(1)).thenReturn(attachments);
        MvcResult mvcResult= this.mockMvc.perform(get("/api/activities/1/attachments"))
                             .andExpect(status().isOk()).andReturn();

        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    @WithMockUser
    public void deleteAttachment() throws Exception{
        when(this.attachmentService.deleteAttachment(1)).thenReturn(true);
        MvcResult mvcResult= this.mockMvc.perform(delete("/api/attachments/1"))
                            .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
                         
    }

}
