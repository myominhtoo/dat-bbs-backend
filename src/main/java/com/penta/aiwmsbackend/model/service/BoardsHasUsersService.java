package com.penta.aiwmsbackend.model.service;

import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.exception.custom.JoinPermissionException;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;

public interface BoardsHasUsersService {

    BoardsHasUsers findUserByIdAndBoardId(Integer userId, Integer boardId)
            throws InvalidEmailException, JoinPermissionException;

    BoardsHasUsers save(BoardsHasUsers boardsHasUsers);

    BoardsHasUsers findByUserId(Integer userId);

    boolean joinBoard(User user, Board board) throws InvalidEmailException , JoinPermissionException;

}
