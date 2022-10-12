package com.penta.aiwmsbackend.model.service;

import org.springframework.web.servlet.view.RedirectView;

import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.exception.custom.JoinPermissionException;
import com.penta.aiwmsbackend.model.entity.Board;

/*
 * write abstract methods for bookService
 */
public interface BoardService {

    boolean createBoard(Board board);

    RedirectView joinBoard(String email, Integer code, Integer boardId)
            throws InvalidEmailException, JoinPermissionException;

}
