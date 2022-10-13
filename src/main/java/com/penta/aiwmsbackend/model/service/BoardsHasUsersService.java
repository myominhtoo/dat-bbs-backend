package com.penta.aiwmsbackend.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardsHasUsersRepo;

@Service("boardsHasUsersService")
public class BoardsHasUsersService {

    private BoardsHasUsersRepo boardsHasUsersRepo;

    public BoardsHasUsersService(BoardsHasUsersRepo boardsHasUsersRepo) {
        this.boardsHasUsersRepo = boardsHasUsersRepo;
    }

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

    public BoardsHasUsers save(BoardsHasUsers boardsHasUsers) {
        return this.boardsHasUsersRepo.save(boardsHasUsers);
    }

    public List<BoardsHasUsers> findByUserId(Integer userId) {
        return this.boardsHasUsersRepo.findByUserId(userId);
    }

    public BoardsHasUsers findUserByIdAndBoardId(Integer userId, Integer boardId) {
        return this.boardsHasUsersRepo.findUserByUserIdAndBoardId(userId, boardId).get();
    }

}
