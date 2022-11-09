package com.penta.aiwmsbackend.model.service;

import java.io.File;
import java.io.IOException;
import com.penta.aiwmsbackend.exception.custom.CustomFileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    // Integer id , MultipartFile file
    public Attachment uploadFile ( Attachment attachment )throws CustomFileNotFoundException, IllegalStateException, IOException, InvalidActivityIdException ,MultipartFileNotFoundException {
     int code = RandomCode.generate();
     String fullPath = PATH + code + attachment.getFile().getOriginalFilename();
     String fileName = code + StringUtils.cleanPath(attachment.getFile().getOriginalFilename());
     String extension = attachment.getFile().getContentType();
      
        Activity activity = this.activityRepo.findById( attachment.getActivity().getId() ).orElseThrow(() -> new InvalidActivityIdException("Activity id Not Found!"));

        if ( extension.equals("application/pdf") || extension.equals("text/plain") 
        || extension.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") 
        || extension.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")
        || extension.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") 
        || extension.equals("image/jpg")
        || extension.equals("image/png")
        || extension.equals("image/jpeg")
        || extension.equals("application/x-zip-compressed")){
            File file = new File(fullPath);

            attachment.getFile().transferTo(file);
        }else {
          throw new CustomFileNotFoundException("File Not Found");
        }
        attachment.setFileUrl(fileName);
        attachment.setActivity(activity);
        attachment.setCreatedDate(LocalDateTime.now());
        return this.attachmentRepo.save(attachment); 
    
    }


    public List<Attachment> showAllAttachments(int id) {
        List<Attachment> attachments = attachmentRepo.findAttachmentsByActivityId(id);
        return attachments;
    }

    public boolean deleteAttachment(int id) throws MultipartFileNotFoundException {
        boolean status = true;
        // Optional<Attachment> att = attachmentRepo.findById(id);
        Attachment attachment = this.attachmentRepo.findById(id).orElseThrow(() -> new MultipartFileNotFoundException("File path Not found!"));
        String path= attachment.getFileUrl();
        String filePath =PATH + path;
        try{
            File file=new File(filePath);
            file.delete();
            this.attachmentRepo.deleteById(id);
        }catch(Exception exception){
            status=false;
        }
        return status;
    }
}
