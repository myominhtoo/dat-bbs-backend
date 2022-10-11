package com.penta.aiwmsbackend.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.service.BoardService;

/*
 * writer implementations methods for boardService
 */
@Service("bookService")
public class BoardServiceImpl implements BoardService {
    
    private BoardRepo boardRepo;

    @Autowired
    public BoardServiceImpl( BoardRepo boardRepo ){
        this.boardRepo = boardRepo;
    }

}
