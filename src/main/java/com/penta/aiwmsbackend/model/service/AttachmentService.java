package com.penta.aiwmsbackend.model.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.PortUnreachableException;
import com.penta.aiwmsbackend.exception.custom.CustomFileNotFoundException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.mail.Multipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.penta.aiwmsbackend.exception.custom.FileNotSupportException;
import com.penta.aiwmsbackend.exception.custom.InvalidActivityIdException;
import com.penta.aiwmsbackend.exception.custom.MultipartFileNotFoundException;
import com.penta.aiwmsbackend.model.entity.Activity;
import com.penta.aiwmsbackend.model.entity.Attachment;
import com.penta.aiwmsbackend.model.repo.ActivityRepo;
import com.penta.aiwmsbackend.model.repo.AttachmentRepo;
import com.penta.aiwmsbackend.util.RandomCode;

@Service("AttachmentService")
public class AttachmentService {
    private AttachmentRepo attachmentRepo;
    private ActivityRepo activityRepo;

    @Autowired
    public AttachmentService (AttachmentRepo attachmentRepo,ActivityRepo activityRepo){
        this.activityRepo=activityRepo;
        this.attachmentRepo=attachmentRepo;
    }

    private String PATH = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
    + "src\\main\\resources\\static\\attachments\\";

    public Attachment uploadFile (Integer id , MultipartFile file )throws CustomFileNotFoundException, IllegalStateException, IOException, InvalidActivityIdException ,MultipartFileNotFoundException {
     int code = RandomCode.generate();
     String fullPath = PATH + code +file.getOriginalFilename();
     String fileName = code + StringUtils.cleanPath(file.getOriginalFilename());
     String extension = file.getContentType();
      
    //   Optional <Attachment> attachment = this.attachmentRepo.findById(id);
    // Optional<Activity> activity= this.activityRepo.findById(id);

    Activity activity = this.activityRepo.findById(id).orElseThrow(() -> new InvalidActivityIdException("Activity id Not Found!"));
    if ( file.isEmpty()){
        throw new MultipartFileNotFoundException("Wrong File Type!");
    }else{
        if ( extension.equals("application/pdf") || extension.equals("text/plain") 
        || extension.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") 
        || extension.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")
        || extension.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") 
        || extension.equals("image/jpg")
        || extension.equals("image/png") ){
            //    attachment.get().setFileUrl(fileName);
            file.transferTo(new File(fullPath));
        }else {
          throw new CustomFileNotFoundException("File Not Found");
        }
    }
    //    Attachment saveAttachment=attachment.get();
    //    saveAttachment.setFileUrl(fileName);
    //    return this.attachmentRepo.save(saveAttachment);
    //    return new Attachment();
        Attachment attachment=new Attachment();
        attachment.setFileUrl(fileName);
        attachment.setActivity(activity);
        attachment.setCreatedDate(LocalDateTime.now());
        return this.attachmentRepo.save(attachment); 
    
    }
}
