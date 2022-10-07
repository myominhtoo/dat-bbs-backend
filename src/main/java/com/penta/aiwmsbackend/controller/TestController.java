package com.penta.aiwmsbackend.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.exception.handler.UserControllerAdvice;
import com.penta.aiwmsbackend.model.service.impl.UserServiceImpl;

@Controller
public class TestController extends UserControllerAdvice {

    @Autowired
    UserServiceImpl userServiceImpl;
    

    @GetMapping("/")
    public boolean test() throws DuplicateEmailException, UnsupportedEncodingException, MessagingException {
      userServiceImpl.sendVertification("myominhtoo@ucsy.edu.mm");
      return false;
    }

}
