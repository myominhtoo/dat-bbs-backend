package com.penta.aiwmsbackend.model.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.web.servlet.view.RedirectView;

import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.exception.custom.JoinPermissionException;
import com.penta.aiwmsbackend.model.entity.Board;

/*
 * write abstract methods for bookService
 */
public interface BoardService {

    void createBoard(Board board) throws UnsupportedEncodingException, MessagingException;

    RedirectView joinBoard(String email, Integer code, Integer boardId)
            throws InvalidEmailException, JoinPermissionException;

}
