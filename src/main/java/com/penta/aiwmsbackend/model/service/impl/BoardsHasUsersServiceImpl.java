package com.penta.aiwmsbackend.model.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardsHasUsersRepo;
import com.penta.aiwmsbackend.model.service.BoardsHasUserService;

@Service("boardsHasUsersService")
public class BoardsHasUsersServiceImpl implements BoardsHasUserService {

    private BoardsHasUsersRepo boardsHasUserRepo;

    public BoardsHasUsersServiceImpl(BoardsHasUsersRepo boardsHasUsersRepo) {
        this.boardsHasUserRepo = boardsHasUsersRepo;
    }

    @Override
    public boolean joinBoard(User user, Board board) {
        boolean joinedStatus = false;

        BoardsHasUsers boardsHasUsers = new BoardsHasUsers();
        boardsHasUsers.setBoard(board);
        boardsHasUsers.setUser(user);
        boardsHasUsers.setJoinedDate(new Date());
        boardsHasUsers.setJoinedStatus(false);

        if (this.boardsHasUserRepo.save(boardsHasUsers) != null) {
            joinedStatus = true;
        }
        return joinedStatus;
    }

}
