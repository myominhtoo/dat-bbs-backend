package com.penta.aiwmsbackend.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.repo.UserRepo;
import com.penta.aiwmsbackend.model.service.BoardService;

/*
 * writer implementations methods for boardService
 */
@Service("bookService")
public class BoardServiceImpl implements BoardService {
    
    private final BoardRepo boardRepo;
    private final UserRepo userRepo;

    @Autowired
    public BoardServiceImpl( final BoardRepo boardRepo , 
    UserRepo userRepo  ){
        this.boardRepo = boardRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void createBoard(Board board) {
        //loop pt board.getInviteEmails()
        // will get email
        // find user with emai 
        // user shi yin user ya  -> list htl mhr save htr
        // ma shi yin 
       // shi yin -> save( BoardsHasUsers )
       // maill poh 
    }

}
