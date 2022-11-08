package com.penta.aiwmsbackend.controller.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.penta.aiwmsbackend.model.entity.BoardMessage;
import com.penta.aiwmsbackend.model.service.BoardMessageService;

@Controller
public class BoardChatController {
    
    private SimpMessagingTemplate simpMessagingTemplate;
    private BoardMessageService boardMessageService;

    @Autowired
    public BoardChatController( SimpMessagingTemplate simpMessagingTemplate ,BoardMessageService boardMessageService ){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.boardMessageService =boardMessageService;
    }

    @GetMapping("boards/{boardId}/boardMessages")
    public ResponseEntity<List<BoardMessage>> getBoardMessages ( @PathVariable("boardId") Integer id ){
        List<BoardMessage> getBoardMessages = boardMessageService.getBoardMessageByBoardId(id);
        return ResponseEntity.ok().body(getBoardMessages);
    }
}
