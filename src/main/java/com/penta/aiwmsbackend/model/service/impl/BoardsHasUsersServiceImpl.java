package com.penta.aiwmsbackend.model.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.exception.custom.JoinPermissionException;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardsHasUsersRepo;
import com.penta.aiwmsbackend.model.service.BoardsHasUsersService;

@Service("boardsHasUsersService")
public class BoardsHasUsersServiceImpl implements BoardsHasUsersService {

    private BoardsHasUsersRepo boardsHasUsersRepo;

    public BoardsHasUsersServiceImpl(BoardsHasUsersRepo boardsHasUsersRepo) {
        this.boardsHasUsersRepo = boardsHasUsersRepo;
    }

    @Override
    public boolean joinBoard(User user, Board board) {
        boolean joinedStatus = false;

        BoardsHasUsers boardsHasUsers = new BoardsHasUsers();
        boardsHasUsers.setBoard(board);
        boardsHasUsers.setUser(user);
        boardsHasUsers.setJoinedDate(new Date());
        boardsHasUsers.setJoinedStatus(false);

        if (this.boardsHasUsersRepo.save(boardsHasUsers) != null) {
            joinedStatus = true;
        }
        return joinedStatus;
    }

    @Override
    public BoardsHasUsers save(BoardsHasUsers boardsHasUsers) {
        return this.boardsHasUsersRepo.save(boardsHasUsers);
    }

    @Override
    public BoardsHasUsers findByUserId(Integer userId) {
        return this.boardsHasUsersRepo.findByUserId(userId);
    }

    @Override
    public BoardsHasUsers findUserByIdAndBoardId(Integer userId, Integer boardId) {
        return this.boardsHasUsersRepo.findUserByUserIdAndBoardId(userId, boardId).get();
    }

}
