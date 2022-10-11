package com.penta.aiwmsbackend.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
