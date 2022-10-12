package com.penta.aiwmsbackend.controller.board;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.exception.custom.JoinPermissionException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Board;

import com.penta.aiwmsbackend.model.service.impl.BoardServiceImpl;

/*
 * write rest controller for board
 */
@RestController
@RequestMapping(value = "/api")
public class BoardController {

    private BoardServiceImpl boardServiceImpl;

    @Autowired
    public BoardController(BoardServiceImpl boardServiceImpl) {
        this.boardServiceImpl = boardServiceImpl;
    }

    @PostMapping(value = "/create-board")
    public ResponseEntity<HttpResponse> createBoard(@RequestBody Board board) throws UnsupportedEncodingException, MessagingException {
        this.boardServiceImpl.createBoard( board );
        HttpResponse httpResponse = new HttpResponse(
            new Date(),
            HttpStatus.OK,
            HttpStatus.OK.value(),
            "Successfully Created!",
            "Ok",
            true
        );
        return new ResponseEntity<>(httpResponse,httpResponse.getHttpStatus());
    }

    // join-board?email=...&code=123
    @GetMapping(value = "/join-board")
    public RedirectView joinBoard(@RequestParam("email") String email,
            @RequestParam("code") Integer code,
            @RequestParam("board-id") Integer boardId) throws InvalidEmailException, JoinPermissionException {

        return boardServiceImpl.joinBoard(email, code, boardId);
    }

}
