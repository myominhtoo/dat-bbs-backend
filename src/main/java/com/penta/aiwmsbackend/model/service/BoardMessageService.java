package com.penta.aiwmsbackend.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.BoardMessage;
import com.penta.aiwmsbackend.model.repo.BoardMessageRepo;

@Service("boardMessageService")
public class BoardMessageService {
    
    private BoardMessageRepo boardMessageRepo;

    @Autowired
    public BoardMessageService( BoardMessageRepo boardMessageRepo){
        this.boardMessageRepo=boardMessageRepo;

    }
    public BoardMessage saveBoardMessage( BoardMessage message ){
        return this.boardMessageRepo.save( message );
    }

    public List<BoardMessage> getBoardMessageByBoardId ( Integer boardId ){
        return this.boardMessageRepo.findBoardMessageByBoardId ( boardId );
    }

}
