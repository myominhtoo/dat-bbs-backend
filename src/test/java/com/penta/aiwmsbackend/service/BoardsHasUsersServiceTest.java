package com.penta.aiwmsbackend.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.repo.BoardsHasUsersRepo;
import com.penta.aiwmsbackend.model.repo.UserRepo;
import com.penta.aiwmsbackend.model.service.BoardService;
import com.penta.aiwmsbackend.model.service.BoardsHasUsersService;
import com.penta.aiwmsbackend.model.service.UserService;

@SpringBootTest
public class BoardsHasUsersServiceTest {
    @Mock
    private BoardsHasUsersRepo boardsHasUsersRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private BoardRepo boardRepo;

    @InjectMocks
    private BoardsHasUsersService boardsHasUsersService;
    @InjectMocks
    private UserService userService;
    @InjectMocks
    private BoardService boardService;

    private static BoardsHasUsers boardsHasUsers;
    private static User user;
    private static Board board;

    private static List<BoardsHasUsers> boardsHasUsersList;

    @BeforeAll
    private static void beforeAllTests(){
        BoardsHasUsers boardsHasUsers1=new BoardsHasUsers();
        Board board1=new Board();
        User user1=new User();
        boardsHasUsers1.setId(1);
        boardsHasUsers1.setJoinedStatus(false);
        boardsHasUsers1.setJoinedDate(LocalDateTime.now());
        boardsHasUsers1.setBoard(board1);
        boardsHasUsers1.setUser(user1);

        BoardsHasUsers boardsHasUsers2=new BoardsHasUsers();
        Board board2=new Board();
        User user2=new User();
        boardsHasUsers2.setId(2);
        boardsHasUsers2.setJoinedStatus(false);
        boardsHasUsers2.setJoinedDate(LocalDateTime.now());
        boardsHasUsers2.setBoard(board2);
        boardsHasUsers2.setUser(user2);

        boardsHasUsers = boardsHasUsers1;
        board =board1;
        user=user1;

        boardsHasUsersList = new ArrayList<>();
        Collections.addAll( boardsHasUsersList , boardsHasUsers1 , boardsHasUsers2);
    }

    @Test
    public void saveBoardsHasUsers(){
        when(this.boardsHasUsersRepo.save(boardsHasUsers)).thenReturn(boardsHasUsers);
        this.boardsHasUsersService.save(boardsHasUsers);
        verify(this.boardsHasUsersRepo ,times(1)).save(boardsHasUsers);
     
    }

    @Test
    public void findByUserId(){
        when(this.boardsHasUsersRepo.findByUserId(1)).thenReturn(boardsHasUsersList);
        this.boardsHasUsersService.findByUserId(1);
        verify(this.boardsHasUsersRepo, times(1)).findByUserId(1);

    }

    @Test
    public void findByBoardId(){
        when(this.boardsHasUsersRepo.findUsersByBoardId(1)).thenReturn(boardsHasUsersList);
        this.boardsHasUsersService.findMember(1);
        verify(this.boardsHasUsersRepo, times(1)).findUsersByBoardId(1);
    }

    @Test
    public void findUserByUserIdAndBoardId(){
        Optional<BoardsHasUsers> boardsUsers =Optional.of(boardsHasUsers);
        when(this.boardsHasUsersRepo.findUserByUserIdAndBoardId(1,1)).thenReturn(boardsUsers);

    }

    @Test
    public void joinBoard(){
        when(this.boardsHasUsersRepo.save(boardsHasUsers)).thenReturn(boardsHasUsers);
        this.boardsHasUsersService.save(boardsHasUsers);
        verify(this.boardsHasUsersRepo ,times(1)).save(boardsHasUsers);
    }
}
