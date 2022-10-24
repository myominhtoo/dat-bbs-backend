package com.penta.aiwmsbackend.model.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import com.penta.aiwmsbackend.exception.custom.CreatePermissionException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.exception.custom.JoinPermissionException;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.repo.UserRepo;
import com.penta.aiwmsbackend.util.MailTemplate;
import com.penta.aiwmsbackend.util.RandomCode;

@Service("boardService")
public class BoardService {

    private BoardRepo boardRepo;
    private BoardsHasUsersService boardsHasUsersService;
    private UserRepo userRepo;
    private EmailService emailService;

    @Autowired
    public BoardService(final BoardRepo boardRepo,
            UserRepo userRepo, EmailService emailService,
            BoardsHasUsersService boardsHasUsersService) {
        this.boardRepo = boardRepo;
        this.userRepo = userRepo;
        this.boardsHasUsersService = boardsHasUsersService;
        this.emailService = emailService;
    }

    public void createBoard(Board board)
            throws UnsupportedEncodingException, MessagingException, CreatePermissionException {

        Optional<User> createBoardUser = this.userRepo.findById(board.getUser().getId());

        if (createBoardUser.isEmpty() || !createBoardUser.get().isValidUser()) {
            throw new CreatePermissionException("You don't have permission to create board!");
        }

        board.setCreatedDate(LocalDateTime.now());
        board.setDeleteStatus(false);
        board.setCode(RandomCode.generate());

        Board createBoard = this.boardRepo.save(board);

        for (String email : createBoard.getInvitedEmails()) {

            Optional<User> optionalUser = this.userRepo.findByEmail(email);
            if (optionalUser.isPresent()) {
                this.boardsHasUsersService.joinBoard(optionalUser.get(), createBoard);
            } else {
                User storeUser = new User();
                storeUser.setEmail(email);
                storeUser.setJoinedDate(LocalDateTime.now());
                storeUser.setCode(RandomCode.generate());
                this.boardsHasUsersService.joinBoard(this.userRepo.save(storeUser), createBoard);
            }
            this.emailService.sendToOneUser("datofficial22@gamil.com", "DAT", email, "BBMS Invitiation",
                    MailTemplate.getTemplate("Invitiation To Board!", "Click Here To Join Board!",
                            "http://localhost:8080/api/join-board?email=" + email + "&code=" + board.getCode()
                                    + "&boardId=" + board.getId()));
        }

    }

    public RedirectView joinBoard(String email, Integer code, Integer boardId)
            throws InvalidEmailException, JoinPermissionException {
        Optional<User> savedUser = this.userRepo.findByEmail(email);

        if (savedUser.isEmpty()) {// email ရှိလား မရှိလားစစ်
            throw new InvalidEmailException("Invalid Email");
        } else {
            BoardsHasUsers joinedUser = this.boardsHasUsersService.findUserByIdAndBoardId(savedUser.get().getId(),
                    boardId);
            if (joinedUser == null) {
                throw new JoinPermissionException("You don't have permission to join this board!");
            }

            joinedUser.setJoinedDate(LocalDateTime.now());
            joinedUser.setJoinedStatus(true);
            boardsHasUsersService.save(joinedUser);

            if (savedUser.get().isValidUser()) {
                System.out.println(savedUser.get().getId());
                return new RedirectView("http://localhost:4200/home");// pyn change ya ml
            } else {
                return new RedirectView("http://localhost:4200/register?code=" + savedUser.get().getCode() + "&email="
                        + savedUser.get().getEmail());
            }
        }
    }

    public List<Board> getBoardsForUser(Integer userId) {
        return this.boardRepo.findBoardsByUserId(userId);
    }

    public Board getBoardWithBoardId(Integer boardId) throws InvalidBoardIdException {
        return this.boardRepo.findById(boardId)
                .orElseThrow(() -> new InvalidBoardIdException("Invalid Board Id!"));
    }

    public boolean inviteMembers(Board board)
            throws InvalidBoardIdException, UnsupportedEncodingException, MessagingException {
        boolean inviteStatus = false;

        Board savedBoard = this.boardRepo.findById(board.getId())
                .orElseThrow(() -> new InvalidBoardIdException("invalid board id!"));

        for (String email : board.getInvitedEmails()) {
            Optional<User> optionalUser = this.userRepo.findByEmail(email);
            boolean shouldInvite = false;

            BoardsHasUsers userInBoard = optionalUser.isPresent()
                    ? this.boardsHasUsersService.findUserByIdAndBoardId(optionalUser.get().getId(),
                            savedBoard.getId())
                    : null;
            shouldInvite = userInBoard == null ? true : false;

            if (optionalUser.isPresent()) {
                if (shouldInvite)
                    this.boardsHasUsersService.joinBoard(optionalUser.get(), savedBoard);
            } else {
                if (shouldInvite) {
                    User storeUser = new User();
                    storeUser.setEmail(email);
                    storeUser.setJoinedDate(LocalDateTime.now());
                    storeUser.setCode(RandomCode.generate());
                    this.boardsHasUsersService.joinBoard(this.userRepo.save(storeUser), savedBoard);
                }
            }

            try {
                if (shouldInvite) {
                    this.emailService.sendToOneUser("datofficial22@gamil.com", "DAT", email, "BBMS Invitiation",
                            MailTemplate.getTemplate("Invitiation To Board!", "Click Here To Join Board!",
                                    "http://localhost:8080/api/join-board?email=" + email + "&code=" + board.getCode()
                                            + "&boardId=" + board.getId()));
                }

                inviteStatus = true;
            } catch (Exception e) {
                inviteStatus = false;
            }
        }
        return inviteStatus;

    }
}
