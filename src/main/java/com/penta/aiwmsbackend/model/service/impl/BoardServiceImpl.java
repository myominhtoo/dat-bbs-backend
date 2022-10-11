package com.penta.aiwmsbackend.model.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.repo.UserRepo;
import com.penta.aiwmsbackend.model.service.BoardService;
import com.penta.aiwmsbackend.util.RandomCode;

import antlr.collections.List;

/*
 * writer implementations methods for boardService
 */
@Service("bookService")
public class BoardServiceImpl implements BoardService {

    private final BoardRepo boardRepo;
    private final EmailServiceImpl emailServiceImpl;
    private final UserRepo userRepo;
    private BoardsHasUsersServiceImpl boardsHasUsersServiceImpl;

    @Autowired
    public BoardServiceImpl(final BoardRepo boardRepo,
            UserRepo userRepo, EmailServiceImpl emailServiceImpl,
            BoardsHasUsersServiceImpl boardsHasUsersServiceImpl) {
        this.boardRepo = boardRepo;
        this.userRepo = userRepo;
        this.emailServiceImpl = emailServiceImpl;
        this.boardsHasUsersServiceImpl = boardsHasUsersServiceImpl;
    }

    @Override
    public void createBoard(Board board) {
        board.setCreatedDate(new Date());
        board.setDeleteStatus(false);
        board.setCode(RandomCode.generate());
        Board createBoard = this.boardRepo.save(board);

        for (String email : createBoard.getInvitedEmails()) {

            Optional<User> optionalUser = this.userRepo.findByEmail(email);
            if (optionalUser.isPresent()) {
                this.boardsHasUsersServiceImpl.joinBoard(optionalUser.get(), createBoard);
            } else {
                User storeUser = new User();
                storeUser.setEmail(email);
                storeUser.setJoinedDate(new Date());
                storeUser.setCode(RandomCode.generate());
                this.userRepo.save(storeUser);
            }

            emailServiceImpl.InviteMember("sithulwin2627@gmail.com", "Invite Board",
                    email,
                    "Verify Your Invitation Code", "<h2>" + board.getId() + board.getCode() + email + "</h2>");
        }

    }

}
