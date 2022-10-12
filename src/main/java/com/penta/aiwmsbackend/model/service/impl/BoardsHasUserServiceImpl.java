package com.penta.aiwmsbackend.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.repo.BoardsHasUsersRepo;
import com.penta.aiwmsbackend.model.service.BoardsHasUsersService;

@Service("boardsHasUserService")
public class BoardsHasUserServiceImpl implements BoardsHasUsersService {

    private BoardsHasUsersRepo boardsHasUsersRepo;

    @Autowired
    public BoardsHasUserServiceImpl(BoardsHasUsersRepo boardsHasUsersRepo) {
        this.boardsHasUsersRepo = boardsHasUsersRepo;
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
