package com.penta.aiwmsbackend.controller.board;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.penta.aiwmsbackend.exception.custom.CreatePermissionException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.exception.custom.JoinPermissionException;
import com.penta.aiwmsbackend.exception.handler.BoardControllerAdvice;
import com.penta.aiwmsbackend.jasperReport.BoardReportService;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.service.BoardService;

import net.sf.jasperreports.engine.JRException;

/*
 * write rest controller for board
 */
@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping(value = "/api" ,  produces = { MediaType.APPLICATION_JSON_VALUE} )
public class BoardController extends BoardControllerAdvice {

    private BoardService boardService;
    private BoardReportService boardReport;

    @Autowired
    public BoardController(BoardService boardServiceImpl, BoardReportService boardReport) {
        this.boardService = boardServiceImpl;
        this.boardReport = boardReport;
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

    @PutMapping(value = "/update-board")
    public ResponseEntity<HttpResponse<Board>> UpdateBoard(@RequestBody Board board)
            throws CreatePermissionException {
        Board updateBoardStatus = boardService.updateBoard(board);
        HttpResponse<Board> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                updateBoardStatus != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                updateBoardStatus != null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                updateBoardStatus != null ? "Successfully Updated!" : "Failed to Update!",
                updateBoardStatus != null ? "OK" : "Error occured!",
                updateBoardStatus != null,
                updateBoardStatus);
        return new ResponseEntity<HttpResponse<Board>>(httpResponse, httpResponse.getHttpStatus());

    }

    @PutMapping(value = "/boards/{boardId}/delete-board")
    public Board updateDeleteStatus(@PathVariable("boardId") Integer boardId) {

        Board board = boardService.updateDeleteStatus(boardId);

        Board deleteBoard = new Board();

        deleteBoard.setId(board.getId());
        deleteBoard.setBoardName(board.getBoardName());
        deleteBoard.setCode(board.getCode());
        deleteBoard.setCreatedDate(board.getCreatedDate());
        deleteBoard.setImageUrl(board.getImageUrl());
        deleteBoard.setDescription(board.getDescription());
        deleteBoard.setUser(board.getUser());
        deleteBoard.setDeleteStatus(true);

        return this.boardService.updateBoardForDeleteStatus(deleteBoard);

    }

    @GetMapping(value = "/archive-boards")
    public List<Board> showDeletedBoards(@RequestParam("userId") Integer userId) {
        // List<Board> boards = new
        // ArrayList<>(this.boardService.showdeletedBoards(id));
        return this.boardService.showdeletedBoards(userId);
    }

    @PutMapping(value = "/boards/{boardId}/restoreBoard")
    public Board restoreBoard(@PathVariable("boardId") Integer boardId) {

        Board board = boardService.updateDeleteStatus(boardId);
        Board restoreBoard = new Board();
        restoreBoard.setDeleteStatus(false);
        return this.boardService.updateBoardForDeleteStatus(restoreBoard);

    }

    // @GetMapping(value = "/reportBoard/{boardFormat}")
    // public ResponseEntity<Map<String, String>> generateReport(@PathVariable String boardFormat, HttpServletResponse response)
    //         throws JRException, IOException {
    //     String flag = boardReport.exportBoardReport(boardFormat, response);
    //     Map<String, String> responsetoangular = new HashMap<>();
    //     responsetoangular.put("flag", flag);
    //     return ResponseEntity.ok(responsetoangular);

    // }

    @GetMapping(value = "/reportBoard/{boardFormat}")
    public void generateReport(@PathVariable String boardFormat , HttpServletResponse response) throws JRException,IOException{
        boardReport.exportBoardReport(boardFormat, response);
        
    }

}
