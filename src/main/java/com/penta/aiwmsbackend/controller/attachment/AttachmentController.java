package com.penta.aiwmsbackend.controller.attachment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.exception.custom.CustomFileNotFoundException;
import com.penta.aiwmsbackend.exception.custom.FileNotSupportException;
import com.penta.aiwmsbackend.exception.custom.InvalidActivityIdException;
import com.penta.aiwmsbackend.exception.custom.MultipartFileNotFoundException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Attachment;
import com.penta.aiwmsbackend.model.service.AttachmentService;




@RestController
@RequestMapping(value = "/api")
@CrossOrigin( originPatterns = "*")
public class AttachmentController {
    private AttachmentService attachmentService;
    private ObjectMapper objectMapper;

    @Autowired
    public AttachmentController (AttachmentService attachmentService , ObjectMapper objectMapper ){
        this.attachmentService=attachmentService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/activities/{activityId}/create-attachment" )
    public ResponseEntity<HttpResponse<Attachment>> UploadFile (@PathVariable("activityId") Integer id ,  
    @RequestPart("file") MultipartFile file , 
    @RequestPart("data") String attachmentString  )
    throws FileNotSupportException, IllegalStateException, IOException, InvalidActivityIdException, CustomFileNotFoundException, MultipartFileNotFoundException{ 
            Attachment attachment = this.objectMapper.readValue(attachmentString, Attachment.class);
            attachment.setFile(file);
            Attachment attachmentStatus = this.attachmentService.uploadFile( attachment );
            attachmentStatus.setFile(null);
              HttpResponse<Attachment> httpResponse = new HttpResponse<>(
              LocalDate.now(),
              attachmentStatus !=null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
              attachmentStatus !=null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
              attachmentStatus !=null ? "Successfully uploaded File!" : "Failed to upload!",
              attachmentStatus !=null ? "OK!" : "Error Occured!",
              attachmentStatus !=null ,
              attachmentStatus              
              );
        return new ResponseEntity<HttpResponse<Attachment>>(httpResponse, httpResponse.getHttpStatus());
    }

    @GetMapping(value = "/activities/{activityId}/attachments")
    public ResponseEntity<List<Attachment>> showAllAttachments(@PathVariable("activityId") int id) {
        List<Attachment> attachments = attachmentService.showAllAttachments(id);
        return ResponseEntity.ok().body(attachments);
    }

    @DeleteMapping(value = "/attachments/{attachmentId}")
    public ResponseEntity<HttpResponse<Boolean>> deleteAttachment (@PathVariable("attachmentId") Integer id) throws MultipartFileNotFoundException{
        boolean deleteAttStatus= this.attachmentService.deleteAttachment(id);
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            deleteAttStatus ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
            deleteAttStatus ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
            deleteAttStatus ? "Successfully Delete!" : "Fail to delete!",
            deleteAttStatus ? "OK" : "Something went wrong",
            deleteAttStatus,
            true);
            return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }
}
