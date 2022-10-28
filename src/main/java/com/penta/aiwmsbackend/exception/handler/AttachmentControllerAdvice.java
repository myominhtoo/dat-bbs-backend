package com.penta.aiwmsbackend.exception.handler;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.penta.aiwmsbackend.exception.custom.CustomFileNotFoundException;
import com.penta.aiwmsbackend.exception.custom.MultipartFileNotFoundException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;

@RestControllerAdvice
public class AttachmentControllerAdvice {
    @ExceptionHandler({ CustomFileNotFoundException.class})
    public ResponseEntity<HttpResponse<Boolean>> CustomFileNotFoundException (CustomFileNotFoundException e){
        HttpResponse<Boolean>httpResponse=new HttpResponse<>(LocalDate.now(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),
                                           e.getMessage(),HttpStatus.BAD_REQUEST.getReasonPhrase(),false,true);
        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({MultipartFileNotFoundException.class })
    public ResponseEntity<HttpResponse<Boolean>> MultipartFileException ( MultipartFileNotFoundException e){
        HttpResponse<Boolean>httpResponse=new HttpResponse<>(LocalDate.now(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),
                                           e.getMessage(),HttpStatus.BAD_REQUEST.getReasonPhrase(),false,true);
        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, HttpStatus.BAD_REQUEST);
    }
 }
