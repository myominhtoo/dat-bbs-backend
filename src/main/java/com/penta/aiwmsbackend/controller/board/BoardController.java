package com.penta.aiwmsbackend.controller.board;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.service.impl.BoardServiceImpl;

/*
 * write rest controller for board
 */
@RestController
@RequestMapping( value = "/api" )
public class BoardController {
    
    private BoardServiceImpl boardServiceImpl;

    @Autowired
    public BoardController( BoardServiceImpl boardServiceImpl ){
        this.boardServiceImpl = boardServiceImpl;
    }

    @PostMapping( value = "/create-board" )
    public ResponseEntity<HttpResponse> createBoard( @RequestBody Board board ){
        return null;
    }

}
