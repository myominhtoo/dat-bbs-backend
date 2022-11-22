package com.penta.aiwmsbackend.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardsHasUsersRepo;

@Service("boardsHasUsersService")
public class BoardsHasUsersService {

    private BoardsHasUsersRepo boardsHasUsersRepo;

    @Autowired
    public BoardsHasUsersService(BoardsHasUsersRepo boardsHasUsersRepo) {
        this.boardsHasUsersRepo = boardsHasUsersRepo;
    }

    public boolean joinBoard(User user, Board board) {
        boolean joinedStatus = false;

        BoardsHasUsers boardsHasUsers = new BoardsHasUsers();
        boardsHasUsers.setBoard(board);
        boardsHasUsers.setUser(user);
        boardsHasUsers.setJoinedDate(LocalDateTime.now());
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

    // public List<BoardsHasUsers> findBoardByUserId(Integer userId) {
    // return this.boardsHasUsersRepo.findBoardsByUserId(userId);
    // }

    public BoardsHasUsers findUserByIdAndBoardId(Integer userId, Integer boardId) {
        Optional<BoardsHasUsers> optionalBoardsHasUsers = this.boardsHasUsersRepo.findUserByUserIdAndBoardId(userId,
                boardId);
        return optionalBoardsHasUsers.isPresent() ? optionalBoardsHasUsers.get() : null;
    }

    public List<BoardsHasUsers> findMember(Integer boardId) {
        return this.boardsHasUsersRepo.findUsersByBoardId(boardId);
    }

    public List<BoardsHasUsers> findAllBoardsMembers(Integer userId) {
        List<BoardsHasUsers> boardsHasUsers =  this.boardsHasUsersRepo.findAllBoardsMembersByUserId(userId);
        System.out.println(boardsHasUsers.size());
        return boardsHasUsers;
    }

    public List<BoardsHasUsers> findAllJoinBoardMembers(Integer userId) {
        return this.boardsHasUsersRepo.joinedBoardUserByMember(userId);
    }

} 
 
