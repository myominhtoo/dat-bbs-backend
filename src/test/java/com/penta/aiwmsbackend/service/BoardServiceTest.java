package com.penta.aiwmsbackend.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jayway.jsonpath.Option;
import com.penta.aiwmsbackend.exception.custom.CreatePermissionException;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.repo.BoardsHasUsersRepo;
import com.penta.aiwmsbackend.model.repo.UserRepo;
import com.penta.aiwmsbackend.model.service.BoardService;
import com.penta.aiwmsbackend.model.service.BoardsHasUsersService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BoardServiceTest {
    @Mock
    private BoardRepo boardRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private BoardsHasUsersRepo boardsHasUsersRepo;

    @InjectMocks
    private BoardService boardService;

    @InjectMocks
    private BoardsHasUsersService boardsHasUsersService;

    private static User user;
    private static Board board;

    private static BoardsHasUsers boardsHasUsers;

    @BeforeAll
    public static void setup() {
        user = new User();
        user.setUsername("Zayar");
        user.setId(1);
        user.setEmail("hanzohasashi880@gmail.com");
        user.setCode(123456);
        user.setValidUser(true);

        String[] starr = { "hanzohasashi880@gmail.com" };
        board = new Board();
        board.setId(1);
        board.setUser(user);
        board.setBoardName("Daily Work");
        board.setCode(123456);
        board.setDescription("To Work");
        board.setDeleteStatus(false);
        board.setInvitedEmails(starr);

        boardsHasUsers = new BoardsHasUsers();
        boardsHasUsers.setBoard(board);
        boardsHasUsers.setId(1);
        boardsHasUsers.setUser(user);
        boardsHasUsers.setJoinedStatus(false);

    }

    @DisplayName("Junit test for createBoard method")
    @Test
    public void createBoardTest() throws UnsupportedEncodingException, MessagingException, CreatePermissionException {
        //Arrange
        when(userRepo.findById(board.getUser().getId())).thenReturn(Optional.of(user));
        when(boardRepo.save(board)).thenReturn(board);
        when(userRepo.findByEmail("hanzohasashi880@gmail.com")).thenReturn(Optional.of(user));
        when(boardsHasUsersRepo.findUserByUserIdAndBoardId(user.getId(), board.getId())).thenReturn(Optional.of(boardsHasUsers));
        when(boardsHasUsersService.findUserByIdAndBoardId(user.getId(), board.getId())).thenReturn(boardsHasUsers);
        when(boardsHasUsersRepo.save(boardsHasUsers)).thenReturn(boardsHasUsers);
        
        

        //Act
        // boardsHasUsersService.save(boardsHasUsers);
        // boardService.createBoard(board);
        
        //Assert
        verify(boardRepo,times(1)).save(board);
        verifyNoInteractions(boardRepo);

        
        
    }

}
