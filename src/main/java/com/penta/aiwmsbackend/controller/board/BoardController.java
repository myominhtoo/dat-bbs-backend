package com.penta.aiwmsbackend.controller.board;


import java.util.Date;
import java.util.Optional;
import java.util.Random;

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
        // Random rand = new Random();
        // board.setCode( rand.nextInt( ));
        // Board boardId = this.boardRepo.save( board );
        

        // for( String email : board.getInvitedEmails()){
        //     // email ka shi sys htl mhr shi tr ll phyit nai , ma shi tr ll possible
        //    Optional<User> foundUser = this.userRepo.findByEmailWithValidId( email );
        //    if( foundUser.isPresent() ){
        //     //shi yin 
        //     BoardsHasUsers boardsHasUsers = new BoardsHasUsers();
        //     boardsHasUsers.setBoard(board);
        //     boardsHasUsers.setJoinedDate( new Date());
        //     boardsHasUsers.setUser(foundUser.get()); 
        //     boardsHasUsers.setJoinedStatus(false);

        //    }else{
        //     // ma shi yin
        //     // Random rand  = new Random();
        //     User user = new User();
        //     user.setCode( rand.nextInt(10000000));
        //     user.setEmail( email );
        //     user.setDeleteStatus( false );
        //     user.setValidUser( false );
        //     User savedUser = this.userRepo.save(user);

        //     BoardsHasUsers boardsHasUsers = new BoardsHasUsers();
        //     boardsHasUsers.setBoard(board);
        //     boardsHasUsers.setJoinedDate( new Date());
        //     boardsHasUsers.setUser(savedUser); 
        //     boardsHasUsers.setJoinedStatus(false);
        //    }
        // }
        /*
         * board name
         * description
         * userId
         */
        return null;
    }

}
