package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.exception.custom.CreatePermissionException;
import com.penta.aiwmsbackend.jasperReport.ArchiveBoardReportService;
import com.penta.aiwmsbackend.jasperReport.BoardReportService;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.service.BoardService;
import com.penta.aiwmsbackend.model.service.UserService;


@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BoardService boardService;

    @MockBean
    private BoardReportService boardReport;

    @MockBean
    private ArchiveBoardReportService archiveBoardReportService;

    private static Board board;
    private static User user;

    private static List<Board> boards;

    @BeforeAll
    public static void doBeforeTests() {
        // RedirectView redirectView=new RedirectView();
        String inviteEmail[] = { "a@gmail.com" };
        User user1 = new User();
        Board board1 = new Board();
        board1.setInvitedEmails(inviteEmail);
        board1.setId(1);
        board1.setBoardName("BoardName");
        board1.setCode(11);
        board1.setDescription("Description");
        board1.setCreatedDate(LocalDateTime.now());
        board1.setDeleteStatus(false);
        board1.setUser(user1);

        User user2 = new User();
        Board board2 = new Board();
        board2.setId(1);
        board2.setBoardName("I'm Board");
        board2.setUser(user2);

        board = board1;
        boards = new ArrayList<>();
        Collections.addAll(boards, board1, board2);

    }

    @Test
    @WithMockUser
    public void createBoardTest() throws JsonProcessingException, Exception{
        when(this.boardService.createBoard(new Board())).thenReturn(new Board());
        HttpResponse<Board> httpResponse = new HttpResponse<>(
            LocalDate.now(), 
            HttpStatus.OK,
            HttpStatus.OK.value(), 
            "Successfully Created!",
            "OK",
            true,
            board);
            MvcResult mvcResult = this.mockMvc.perform(post("/api/create-board")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(new Board())))
                                .andExpect(status().isOk())
                                .andReturn();
            assertEquals(200, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void getBoardTest() throws Exception{
        when(this.boardService.getBoardWithBoardId(1)).thenReturn(board);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/boards/1"))
                             .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void inviteMembersTest() throws JsonProcessingException, Exception {
        Board board = new Board();
        when(this.boardService.inviteMembers(board)).thenReturn(true);
        MvcResult mvcResult = this.mockMvc
                .perform(post("/api/boards/1/invite-members").contentType("application/json")
                        .content(this.objectMapper.writeValueAsBytes(board)))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(mvcResult.getResponse().getStatus() == 200);
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test

    public void joinBoardTest() throws JsonProcessingException, Exception {
        RedirectView redirectView = new RedirectView("https://localhost:4200");

        when(this.boardService.joinBoard("email@gmail.com", 1, 1)).thenReturn(redirectView);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/join-board?email=email@gmail.com&code=1&boardId=1"))
                .andExpect(status().is(302)).andReturn();
        assertEquals(302, mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void getBoardsForUserTest() throws Exception{
        when(this.boardService.getBoardsForUser(1)).thenReturn(boards);
        MvcResult mvcResult = this.mockMvc.perform( get("/api/users/1/boards"))
                              .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void updateBoard() throws JsonProcessingException, Exception {
        Board board = new Board();
        when(this.boardService.updateBoard(board)).thenReturn(board);
        HttpResponse<Board> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "Successfully Updated!",
                "OK",
                false,
                board);
        MvcResult mvcResult = this.mockMvc
                .perform(put("/api/update-board").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(board)))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void deleteBoard() throws Exception{
        when(this.boardService.updateDeleteStatus(1)).thenReturn(board);
        MvcResult mvcResult= this.mockMvc.perform(put("/api/boards/1/delete-board"))
                             .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
                         
    }

    @Test
    @WithMockUser
    public void getDeleteBoard() throws Exception{
        when(this.boardService.showdeletedBoards(1)).thenReturn(boards);
        MvcResult mvcResult= this.mockMvc.perform(get("/api/archive-boards?userId=1"))
                             .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser
    public void restoreBoard() throws Exception{
        when(this.boardService.updateBoardForDeleteStatus(board)).thenReturn(board);
        MvcResult mvcResult= this.mockMvc.perform(put("/api/boards/1/restoreBoard"))
                             .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());

    }

    @Test
    @WithMockUser
    public void reportBoard() throws Exception{
        String path = System.getProperty("java.class.path").split(";")[0].replace("target\\test-classes", "")
                      + "\\src\\main\\resources\\static\\Exported-Reports";
        when(this.boardReport.exportBoardReport("pdf",1)).thenReturn("\\ForTesting.pdf");
        MvcResult mvcResult= this.mockMvc.perform(get("/api/users/1/report-board").param("format", "pdf") )
                          //  .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)
                             .andExpect(status().isOk())
                             .andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
        verify(this.boardReport,times(1)).exportBoardReport("pdf",1);

    }

    @Test
    @WithMockUser
    public void reportArchiveBoard() throws Exception{
        when(this.archiveBoardReportService.archiveBoardReport("pdf",1)).thenReturn("\\ForTesting.pdf");
        MvcResult mvcResult= this.mockMvc.perform(get("/api/users/1/archive-board-report").param("format", "pdf") )
                             .andExpect(status().isOk())
                             .andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
        verify(this.archiveBoardReportService,times(1)).archiveBoardReport("pdf",1);
    }

}
