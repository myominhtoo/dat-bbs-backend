package com.penta.aiwmsbackend.controller.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.model.entity.BoardMessage;
import com.penta.aiwmsbackend.model.service.BoardChatService;

@RestController
@RequestMapping(value = "/api" ,  produces = { MediaType.APPLICATION_JSON_VALUE} )
@CrossOrigin( originPatterns = "*")
public class BoardChatController {
    
    private BoardChatService boardMessageService;

    @Autowired
    public BoardChatController( BoardChatService boardMessageService ){
        this.boardMessageService =boardMessageService;
    }

    @GetMapping( value = "boards/{boardId}/messages")
    public ResponseEntity<List<BoardMessage>> getBoardMessages ( @PathVariable("boardId") Integer id ){
        List<BoardMessage> getBoardMessages = boardMessageService.getBoardMessageByBoardId(id);
        return ResponseEntity.ok().body(getBoardMessages);
    }
}
