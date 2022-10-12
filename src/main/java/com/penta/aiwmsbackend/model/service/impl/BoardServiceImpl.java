package com.penta.aiwmsbackend.model.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.exception.custom.JoinPermissionException;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardRepo;

import com.penta.aiwmsbackend.model.repo.UserRepo;
import com.penta.aiwmsbackend.model.service.BoardService;
import com.penta.aiwmsbackend.util.MailTemplate;
import com.penta.aiwmsbackend.util.RandomCode;

/*
 * writer implementations methods for boardService
 */
@Service("bookService")
public class BoardServiceImpl implements BoardService {

    private BoardRepo boardRepo;
    private BoardsHasUsersServiceImpl boardsHasUsersServiceImpl;
    private UserRepo userRepo;
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    public BoardServiceImpl(final BoardRepo boardRepo,
            UserRepo userRepo, EmailServiceImpl emailServiceImpl,
            BoardsHasUsersServiceImpl boardsHasUsersServiceImpl) {
        this.boardRepo = boardRepo;
        this.userRepo = userRepo;
        this.boardsHasUsersServiceImpl = boardsHasUsersServiceImpl;
        this.emailServiceImpl = emailServiceImpl;
    }

    @Override
    public void createBoard(Board board) throws UnsupportedEncodingException, MessagingException {

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
                this.boardsHasUsersServiceImpl.joinBoard( this.userRepo.save(storeUser), createBoard);
            }
            this.emailServiceImpl.sendToOneUser("datofficial22@gamil.com", "DAT", email , "BBMS Invitiation", MailTemplate.getTemplate( "Invitiation To Board!" , "Click Here To Join Board!" , "http://localhost:8080/api/join-board?email="+email+"&code"+board.getCode()+"&board-id="+board.getId() ));
        }

    }

    @Override
    public RedirectView joinBoard(String email, Integer code, Integer boardId)
            throws InvalidEmailException, JoinPermissionException {
        Optional<User> savedUser = this.userRepo.findByEmail(email);

        if (savedUser.isEmpty()) {// email ရှိလား မရှိလားစစ်
            throw new InvalidEmailException("Invalid Email");
        } else {
            if (this.boardsHasUsersServiceImpl.findUserByIdAndBoardId(savedUser.get().getId(), boardId) == null) {
                throw new JoinPermissionException("You don't have permission to join this board!");
            }

            if (savedUser.get().isValidUser()) {

                BoardsHasUsers boardsHasUsers = boardsHasUsersServiceImpl.findByUserId(savedUser.get().getId());
                boardsHasUsers.setJoinedDate(new Date());
                boardsHasUsers.setJoinedStatus(true);
                boardsHasUsersServiceImpl.save(boardsHasUsers);
                return new RedirectView("http://localhost:4200/board?boardId=" + boardsHasUsers.getId());
            } else {

                return new RedirectView("http://localhost:4200/register?code=" + savedUser.get().getCode() + "&email="
                        + savedUser.get().getEmail());
            }
        }
    }

}
