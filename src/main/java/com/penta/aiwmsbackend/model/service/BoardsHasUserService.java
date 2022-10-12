package com.penta.aiwmsbackend.model.service;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.User;

public interface BoardsHasUserService {
    boolean joinBoard(User user, Board board);
}
