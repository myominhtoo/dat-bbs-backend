package com.penta.aiwmsbackend.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardMessage;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.BoardMessageRepo;
import com.penta.aiwmsbackend.model.service.BoardChatService;

@SpringBootTest
public class BoardChatServiceTest {

    @Mock
    private BoardMessageRepo boardMessageRepo;

    @InjectMocks
    private BoardChatService boardMessageService;

    private static BoardMessage boardMessage;
    private static List<BoardMessage> boardMessages;

    @BeforeAll
    public static void doBeforeTests(){
        User user1= new User();
        user1.setId(1);
        Board board1= new Board();
        board1.setId(1);
        BoardMessage boardMessage1= new BoardMessage();
        boardMessage1.setId(1);
        boardMessage1.setContent("I'm content");
        boardMessage1.setCreatedDate(LocalDateTime.now());
        boardMessage1.setFileUrl("localhost://8080/api/boards/1/boardMessages");
        boardMessage1.setBoard(board1);
        boardMessage1.setUser(user1);

        BoardMessage boardMessage2= new BoardMessage();
        boardMessage2.setId(2);
        boardMessage2.setContent("I'm Boardmessage content");
        boardMessage2.setCreatedDate(LocalDateTime.now());
        boardMessage2.setBoard(board1);
        boardMessage2.setUser(user1);

        boardMessage= boardMessage1;
        boardMessages = new ArrayList<>();
        Collections.addAll(boardMessages, boardMessage1, boardMessage2);


    }
    @Test
    public void saveBoardMessage(){
        when (this.boardMessageRepo.save(boardMessage)).thenReturn(boardMessage);
        this.boardMessageService.saveBoardMessage(boardMessage);
        verify(this.boardMessageRepo , times(1)).save(boardMessage);
    }

    @Test
    public void getBoardMessageByBoardId(){
        when ( this.boardMessageRepo.findBoardMessageByBoardId(1)).thenReturn(boardMessages);
        List< BoardMessage > showBoardMessages = this.boardMessageService.getBoardMessageByBoardId(1);
        assertNotNull(this.boardMessageService.getBoardMessageByBoardId(1));
        
    } 
     
}
