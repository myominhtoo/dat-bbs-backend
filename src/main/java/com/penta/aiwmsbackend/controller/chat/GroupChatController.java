package com.penta.aiwmsbackend.controller.chat;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
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