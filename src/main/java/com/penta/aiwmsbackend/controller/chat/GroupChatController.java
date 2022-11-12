package com.penta.aiwmsbackend.controller.chat;

import java.time.LocalDateTime;
<<<<<<< HEAD
import java.time.LocalTime;
import java.util.List;
=======
>>>>>>> ee519c9305349d2d46cfb830e6b6e75a560893ea

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
=======
>>>>>>> ee519c9305349d2d46cfb830e6b6e75a560893ea
import com.penta.aiwmsbackend.model.service.BoardChatService;
import com.penta.aiwmsbackend.model.entity.BoardMessage;

@Controller
public class GroupChatController {

    private BoardChatService BoardChatService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public GroupChatController(BoardChatService BoardChatService, SimpMessagingTemplate simpMessagingTemplate) {
        this.BoardChatService = BoardChatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/boards/{id}/send-message")
    public BoardMessage boardMessage(@Payload BoardMessage message, @DestinationVariable("id") Integer boardId,
            SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(message.getContent());
        message.setCreatedDate(LocalDateTime.now());
        BoardMessage savBoardMessage = BoardChatService.saveBoardMessage(message);
        // headerAccessor.getSessionAttributes().put("username", message.getUser());
        this.simpMessagingTemplate.convertAndSend("/boards/" + message.getBoard().getId() + "/messages", message);
        return savBoardMessage;
    }

}