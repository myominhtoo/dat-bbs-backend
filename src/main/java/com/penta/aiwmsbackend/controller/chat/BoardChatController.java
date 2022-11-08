package com.penta.aiwmsbackend.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class BoardChatController {
    
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public BoardChatController( SimpMessagingTemplate simpMessagingTemplate  ){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

}
