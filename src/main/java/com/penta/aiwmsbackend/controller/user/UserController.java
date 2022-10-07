package com.penta.aiwmsbackend.controller.user;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.exception.handler.UserControllerAdvice;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.service.impl.UserServiceImpl;

@RestController
@RequestMapping( value = "/api" )
public class UserController extends UserControllerAdvice {
    
    private UserServiceImpl userService;

    @Autowired
    public UserController( UserServiceImpl userService ){
        this.userService  = userService;
    }

    @GetMapping( value =  "/send-verification" )
    public ResponseEntity<HttpResponse> sendVerification( @RequestParam( value = "email" , required = true ) String email ) throws UnsupportedEncodingException, DuplicateEmailException, MessagingException{
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setTimestamp(new Date());
        if( userService.sendVertification(email) ){
            httpResponse.setHttpStatus(HttpStatus.OK);
            httpResponse.setHttpStatusCode(HttpStatus.OK.value());
            httpResponse.setMessage("Successfully Sent!");
            httpResponse.setOk(true);
            httpResponse.setReason(HttpStatus.OK.getReasonPhrase());
        }else{
            httpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            httpResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
            httpResponse.setMessage("Something went wrong!");
            httpResponse.setOk(false);
            httpResponse.setReason(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
        return new ResponseEntity<HttpResponse>( httpResponse, httpResponse.getHttpStatus());
    }

}
