package com.penta.aiwmsbackend.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatController( SimpMessagingTemplate simpMessagingTemplate  ){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

}
