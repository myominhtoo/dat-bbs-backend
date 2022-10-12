package com.penta.aiwmsbackend.model.service.impl;

import java.util.Date;
import java.util.Optional;

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

/*
 * writer implementations methods for boardService
 */
@Service("bookService")
public class BoardServiceImpl implements BoardService {

    private BoardRepo boardRepo;
    private BoardsHasUsersServiceImpl boardsHasUsersServiceImpl;
    private UserRepo userRepo;

    @Autowired
    public BoardServiceImpl(final BoardRepo boardRepo,
            UserRepo userRepo ,
            BoardsHasUsersServiceImpl boardsHasUsersServiceImpl ) {
        this.boardRepo = boardRepo;
        this.userRepo = userRepo;
        this.boardsHasUsersServiceImpl = boardsHasUsersServiceImpl;
    }

    @Override
    public boolean createBoard(Board board) {
        // loop pt board.getInviteEmails()
        // will get email
        // find user with emai
        // user shi yin user ya -> list htl mhr save htr
        // ma shi yin
        // shi yin -> save( BoardsHasUsers )
        // maill poh
        return false;
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
