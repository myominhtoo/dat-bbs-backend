package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.penta.aiwmsbackend.jasperReport.BookMarkReportService;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardBookmark;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.service.BoardBookmarkService;

@SpringBootTest
@AutoConfigureMockMvc
public class BookMarkControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardBookmarkService boardBookmarkService;

    @MockBean
    private BookMarkReportService bookMarkReportService;

    @Test
    @WithMockUser
    public void getBoardBookmarksForUserTest() throws Exception{
        List<BoardBookmark> bookmarks = new ArrayList<>();
        BoardBookmark bookmark = new BoardBookmark();
        bookmark.setId(1);
        bookmark.setUser(new User());
        bookmark.setBoard(new Board());
    
        when(this.boardBookmarkService.getBoardBookmarksForUser(1)).thenReturn(bookmarks);
        MvcResult  mvcResult = this.mockMvc.perform( get("/api/users/1/bookmarks") )
                               .andExpect( status().isOk() )
                               .andReturn();
        assertTrue( mvcResult.getResponse().getStatus() == 200 );
        assertNotNull( mvcResult.getResponse().getContentAsString() );
    }

    @Test
    @WithMockUser
    public void getBoardMarkReport() throws Exception {
        when(this.bookMarkReportService.exportBoardReport("pdf",1)).thenReturn("\\ForTesting.pdf");
            // when(this.archiveBoardReportService.archiveBoardReport("pdf",1)).thenReturn("\\ForTesting.pdf");
        MvcResult mvcResult= this.mockMvc.perform(get("/api/users/1/report-bookmark").param("format", "pdf") )
                            .andExpect(status().isOk())
                            .andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
        verify(this.bookMarkReportService,times(1)).exportBoardReport("pdf",1);
    }

}
