package com.penta.aiwmsbackend.service;

import com.penta.aiwmsbackend.model.repo.BoardBookmarkRepo;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardBookmark;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.service.BoardBookmarkService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardBookMarkServiceTest {

    @Mock
    BoardBookmarkRepo boardBookmarkRepo;
    @InjectMocks
    BoardBookmarkService bookmarkService;
    static BoardBookmark boardBookmark;
    static BoardBookmark boardBookmark2;
    static BoardBookmark boardBookmark3;
    static Board board;
    static List<BoardBookmark> boardMarkList;
    static User user;

    @BeforeAll
    public static void beforeRunAll() {
        user = new User();
        user.setId(1);
        user.setDeleteStatus(false);
        user.setValidUser(true);
        board = new Board();
        board.setId(1);
        board.setBoardName("Daily Tasks");

        board.setUser(user);
        user.setUsername("ZayarMyoOo");
        boardBookmark = new BoardBookmark();
        boardBookmark.setId(1);
        boardBookmark.setBoard(board);
        boardBookmark.setUser(user);
        boardBookmark2 = new BoardBookmark();
        boardBookmark2.setId(2);
        boardBookmark2.setBoard(board);
        boardBookmark2.setUser(user);
        boardBookmark3 = new BoardBookmark();
        boardBookmark3.setId(3);
        boardBookmark3.setBoard(board);
        boardBookmark3.setUser(user);
        boardMarkList = new ArrayList<BoardBookmark>();
        Collections.addAll(boardMarkList, boardBookmark, boardBookmark2, boardBookmark3);
    }

    @Test
    void toggleBoardBookmarkDeleteTest() {
            when(this.boardBookmarkRepo.findByUserIdAndBoardId(user.getId(),board.getId())).thenReturn(Optional.of(boardBookmark));                                    
            this.boardBookmarkRepo.deleteById(boardBookmark.getId());            
            when(this.boardBookmarkRepo.save(boardBookmark)).thenReturn(boardBookmark);                                    

            this.bookmarkService.toggleBoardBookmark(boardBookmark); 
            verify(this.boardBookmarkRepo,times(1)).findByUserIdAndBoardId(user.getId(),board.getId());           
            
            // verify(this.boardBookmarkRepo,times(1)).save(boardBookmark);
    }

    @Test
    void getBoardBookmarksForUser() {
        when(this.boardBookmarkRepo.findByUserId(this.user.getId())).thenReturn(boardMarkList);
        List<BoardBookmark> actualBoardBookMark=this.bookmarkService.getBoardBookmarksForUser(this.user.getId());
        assertEquals(boardMarkList.size(), actualBoardBookMark.size());
        verify(this.boardBookmarkRepo,times(1)).findByUserId(this.user.getId());
    }

    @Test
    public void reportBookmark(){
        when(this.boardBookmarkRepo.findByUserId(1)).thenReturn(boardMarkList);
        List<BoardBookmark> bookmarks= this.bookmarkService.getBoardBookmarksForUser(1);
        verify(this.boardBookmarkRepo,times(1)).findByUserId(1);

    }

}
