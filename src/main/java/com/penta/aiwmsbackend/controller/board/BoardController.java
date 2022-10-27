package com.penta.aiwmsbackend.controller.board;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.penta.aiwmsbackend.exception.custom.CreatePermissionException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.exception.custom.JoinPermissionException;
import com.penta.aiwmsbackend.exception.handler.BoardControllerAdvice;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.service.BoardService;

/*
 * write rest controller for board
 */
@RestController
@RequestMapping(value = "/api")
public class BoardController extends BoardControllerAdvice {

    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardServiceImpl) {
        this.boardService = boardServiceImpl;
    }

    @PostMapping(value = "/create-board")
    public ResponseEntity<HttpResponse<Boolean>> createBoard(@RequestBody Board board)
            throws UnsupportedEncodingException, MessagingException, CreatePermissionException {
        this.boardService.createBoard(board);
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "Successfully Created!",
                "Ok",
                true,
                true);
        return new ResponseEntity<>(httpResponse, httpResponse.getHttpStatus());
    }
  
    // join-board?email=...&code=123
    @GetMapping(value = "/join-board")
    public RedirectView joinBoard(
            @RequestParam("email") String email,
            @RequestParam("code") Integer code,
            @RequestParam("boardId") Integer boardId) throws InvalidEmailException, JoinPermissionException {

        RedirectView redirectView = this.boardService.joinBoard(email, code, boardId);
        return redirectView;
    }

    /*
     * getting boards for target user
     */
    @GetMapping(value = "/users/{userId}/boards")
    public List<Board> getBoardsForUser(@PathVariable("userId") Integer userId) {
        List<Board> list = new ArrayList<>(this.boardService.getBoardsForUser(userId));

        // list.addAll(this.boardService.getBoardsForUser(userId));

        list.addAll(this.boardService.getUserJoinedBoards(userId));

        return list;

    }

    /*
     * getting board with board Id
     */
    @GetMapping(value = "/boards/{boardId}")
    public Board getBoard(@PathVariable("boardId") Integer boardId) throws InvalidBoardIdException {
        return this.boardService.getBoardWithBoardId(boardId);
    }

    @PostMapping(value = "/boards/{boardId}/invite-members")
    public ResponseEntity<HttpResponse<Boolean>> inviteMemebers(@RequestBody Board board)
            throws UnsupportedEncodingException, InvalidBoardIdException, MessagingException {
        boolean inviteStatus = this.boardService.inviteMembers(board);

        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                inviteStatus ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                inviteStatus ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                inviteStatus ? "Successfully Invited!" : "Error!",
                inviteStatus ? "OK" : "Error",
                inviteStatus,
                true);

        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }
}
