package com.penta.aiwmsbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;
import com.penta.aiwmsbackend.exception.custom.CustomFileNotFoundException;
import com.penta.aiwmsbackend.exception.custom.InvalidActivityIdException;
import com.penta.aiwmsbackend.exception.custom.MultipartFileNotFoundException;
import com.penta.aiwmsbackend.model.entity.Activity;
import com.penta.aiwmsbackend.model.entity.Attachment;
import com.penta.aiwmsbackend.model.repo.ActivityRepo;
import com.penta.aiwmsbackend.model.repo.AttachmentRepo;
import com.penta.aiwmsbackend.model.service.ActivityService;
import com.penta.aiwmsbackend.model.service.AttachmentService;

@SpringBootTest
public class AttachmentServiceTest {
    @Mock
    private ActivityRepo activityRepo;
    @Mock
    private AttachmentRepo attachmentRepo;
    
    @InjectMocks
    private ActivityService activityService;
    @InjectMocks
    private AttachmentService attachmentService;

    private static Activity activity;
    private static Attachment attachment;
    private static List<Attachment> attachments;

    @BeforeAll
    public static void doBeforeTests(){
        Activity activity1=new Activity();
        activity1.setId(1);
        Attachment attachment1= new Attachment();
        attachment1.setName("attachment");
        attachment1.setFileUrl("91105139bbms.png");
        attachment1.setId(1);
        attachment1.setCreatedDate(LocalDateTime.now());
        attachment1.setActivity(activity1);
        
        Activity activity2=new Activity();
        Attachment attachment2= new Attachment();
        attachment2.setId(1);
        attachment2.setCreatedDate(LocalDateTime.now());
        attachment2.setActivity(activity2);

        attachment = attachment1;
        attachments = new ArrayList();
        Collections.addAll(attachments,attachment1,attachment2);
    }

    @Test
    public void uploadFileTest() throws IllegalStateException, CustomFileNotFoundException, IOException, InvalidActivityIdException, MultipartFileNotFoundException{
        String pathname = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
                          .replace("target\\test-classes", "")+"src\\main\\resources\\static\\attachments\\";
        MockMultipartFile multipartFile=new MockMultipartFile(
            "file",
            "file.png",
            MediaType.IMAGE_PNG_VALUE,
           // new FileInputStream(new java.io.File("D:\\Penta\\ai-wms-backend\\src\\main\\resources\\static\\attachments\\91105139bbms.png"))
            new FileInputStream(new java.io.File(pathname+"91105139bbms.png"))
        );
        when ( this.activityRepo.findById(attachment.getActivity().getId())).thenReturn(Optional.of(new Activity()));
        this.attachmentRepo.save(attachment);
        verify( this.attachmentRepo , times(1)).save(attachment);
    }

    @Test
    public void showAllAttachments() {
        // when(this.activityRepo.findById(1)).thenReturn(Optional.of(activity));
        when(this.attachmentRepo.findAttachmentsByActivityId(1)).thenReturn(attachments);
            List<Attachment> attachmentList= this.attachmentService.showAllAttachments(1);
          assertEquals(attachments.size(), attachmentList.size());
          verify(this.attachmentRepo,times(1)).findAttachmentsByActivityId(1);

    }
    @Test
    public void deleteAttachment(){
        when(this.attachmentRepo.findById(1)).thenReturn(Optional.of(attachment));
        this.attachmentRepo.deleteById(attachment.getId());
        verify(this.attachmentRepo,times(1)).deleteById(1);
    }
   
}
          
