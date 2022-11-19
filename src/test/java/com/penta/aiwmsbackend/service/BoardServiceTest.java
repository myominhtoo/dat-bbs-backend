package com.penta.aiwmsbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.penta.aiwmsbackend.exception.custom.CreatePermissionException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardRepo;
import com.penta.aiwmsbackend.model.repo.BoardsHasUsersRepo;
import com.penta.aiwmsbackend.model.repo.UserRepo;
import com.penta.aiwmsbackend.model.service.BoardService;
import com.penta.aiwmsbackend.model.service.BoardsHasUsersService;
import com.penta.aiwmsbackend.model.service.EmailService;

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
  @InjectMocks
  private EmailService emailService;

  private static User user;
  private static Board board;
  private static Board board2;
  private static List<Board> boardList;
  private static BoardsHasUsers boardsHasUsers;

  @BeforeAll
  public static void setup() {
    user = new User();
    user.setUsername("Zayar");
    user.setId(1);
    user.setEmail("hanzohasashi880@gmail.com");
    user.setCode(123456);
    user.setValidUser(true);
    user.setGender(0);
    user.setPassword("123");

    String[] starr = { "hanzohasashi880@gmail.com" };
    board = new Board();
    board.setId(1);
    board.setUser(user);
    board.setBoardName("Daily Work");
    board.setCode(123456);
    board.setDescription("To Work");
    board.setDeleteStatus(false);
    board.setInvitedEmails(starr);
    board.setCreatedDate(LocalDateTime.now());
    board.setImageUrl(null);

    board2 = new Board();
    board2.setId(1);
    board2.setUser(user);
    board2.setBoardName("Daily Work");
    board2.setCode(123456);
    board2.setDescription("To Work");
    board2.setDeleteStatus(false);
    board2.setInvitedEmails(starr);

    boardList = new ArrayList();
    Collections.addAll(boardList, board, board2);

    boardsHasUsers = new BoardsHasUsers();
    boardsHasUsers.setBoard(board);
    boardsHasUsers.setId(1);
    boardsHasUsers.setUser(user);
    boardsHasUsers.setJoinedStatus(false);
    boardsHasUsers.setJoinedDate(LocalDateTime.now());
  }

  @DisplayName("Junit test for createBoard method")
    @Test
    public void createBoardTest() throws UnsupportedEncodingException, MessagingException, CreatePermissionException {

        when(this.userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(boardRepo.save(board)).thenReturn(board);
        when(userRepo.findByEmail("hanzohasashi880@gmail.com")).thenReturn(Optional.of(user));
        when(this.boardsHasUsersRepo.save(boardsHasUsers)).thenReturn(boardsHasUsers);

        this.userRepo.findById(user.getId());
        this.boardRepo.save(board);
        this.userRepo.findByEmail("hanzohasashi880@gmail.com");
        this.boardsHasUsersRepo.save(boardsHasUsers);
        
     
     
        Optional<User> optionalUser = this.userRepo.findByEmail(user.getEmail());
        
        	this.boardsHasUsersService.joinBoard(user, board);	

        
          Assertions.assertThat(board.getId()).isGreaterThan(0);     
        	verify(this.userRepo,times(1)).findById(user.getId());
            verify(this.boardRepo,times(1)).save(board);
            verify(this.boardsHasUsersRepo,times(1)).save(boardsHasUsers);


        
    }

  @Test
    public void getBoardsForUserTest() {
            when(this.boardRepo.findBoardsByUserId(user.getId())).thenReturn(boardList);
            List<Board> actualBoardList= this.boardService.getBoardsForUser(user.getId());
          assertEquals(boardList.size(), actualBoardList.size());
          verify(this.boardRepo,times(1)).findBoardsByUserId(user.getId());

            
    }

 
  @Test
  public void getUserJoinedBoardsTest() {
    List<BoardsHasUsers> boardownUser = this.boardsHasUsersRepo.findBoardsByUserId(1);
    this.boardService.getUserJoinedBoards(board.getId());
    //when (this.boardsHasUsersRepo.findBoardsByUserId(1)).thenReturn(boardownUser);
    verify(this.boardsHasUsersRepo,times(2)).findBoardsByUserId(1);


  }

  @Test
    public void getBoardWithBoardIdTest() throws InvalidBoardIdException {
            when(this.boardRepo.findById(board.getId())).thenReturn(Optional.of(board));
            Board actualBoard=this.boardService.getBoardWithBoardId(board.getId());
            assertEquals(board, actualBoard);
            verify(this.boardRepo,times(1)).findById(board.getId());
    }

  @Test
    public void inviteMembers() throws UnsupportedEncodingException, MessagingException, InvalidBoardIdException{
        when(this.boardRepo.findById(board.getId())).thenReturn(Optional.of(board));
        Optional<Board> boardId = this.boardRepo.findById(board.getId());
        assertEquals(boardId.get().getId(), board.getId());
        when(this.userRepo.findByEmail("user1@gmail.com")).thenReturn(Optional.of(user));
        Optional<User> userEmail = this.userRepo.findByEmail("user1@gmail.com");
        assertEquals(userEmail.get().getEmail(), user.getEmail());
        assertTrue(this.boardService.inviteMembers(board));
    
    }

  @Test
    public void updateBoard() throws CreatePermissionException{
        when ( this.boardRepo.findById(board.getUser().getId())).thenReturn(Optional.of(board));
        Optional<Board> boarduser = this.boardRepo.findById(board.getUser().getId());
        assertEquals(Optional.of(board), boarduser);
        when ( this.boardRepo.save(board)).thenReturn(board);
        this.boardRepo.save(board);
        verify(this.boardRepo,times(1)).save(board);
  
    }

  @Test
    public void updateDeleteStatus(){
      when(this.boardRepo.updateDeleteStatusOnBoardsByBoardId(board.getId())).thenReturn(board);
      this.boardService.updateDeleteStatus(board.getId());
      verify(this.boardRepo, times(1)).updateDeleteStatusOnBoardsByBoardId(board.getId());
    }

  @Test
    public void updateBoardForDeleteStatus(){
      when(this.boardRepo.save(board)).thenReturn(board);
      this.boardService.updateBoardForDeleteStatus(board);
      verify(this.boardRepo, times(1)).save(board);
    }

  @Test
    public void showdeletedBoards(){
      when(this.boardRepo.findDeletedBoardsByUserId(user.getId())).thenReturn(boardList);
      this.boardService.showdeletedBoards(user.getId());
      verify(this.boardRepo, times(1)).findDeletedBoardsByUserId(user.getId());
    }

    @Test
    public void reportBoard(){
      when(this.boardRepo.findBoards(1)).thenReturn(boardList);
      this.boardService.reportBoard(1);
      verify(this.boardRepo, times(1)).findBoards(1);
    }

}
