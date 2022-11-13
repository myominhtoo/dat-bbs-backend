package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.jasperReport.memberReportService;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardBookmark;
import com.penta.aiwmsbackend.model.entity.BoardMessage;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.service.BoardBookmarkService;
import com.penta.aiwmsbackend.model.service.BoardChatService;
import com.penta.aiwmsbackend.model.service.BoardsHasUsersService;
import com.penta.aiwmsbackend.model.service.UserService;
import com.penta.aiwmsbackend.util.JwtProvider;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private memberReportService reportService;

    @MockBean
    private UserService userService;

    @MockBean
    private BoardsHasUsersService boardsHasUsersService;

    @MockBean
    private BoardBookmarkService bookmarkService;

    @Autowired
    private JwtProvider jwtProvider;

    private static List<User> users;
    private static User user;
    private static Board board;
    private static List<BoardsHasUsers> boardsHasUsersList;

    static BoardBookmark boardBookmark;
    static BoardBookmark boardBookmark2;
    static BoardBookmark boardBookmark3;
    static List<BoardBookmark> boardMarkList;

    @BeforeAll
    public static void runBeforeAll() {
        user = new User();
        user.setId(1);
        user.setEmail("user1@gmail.com");
        user.setValidUser(true);

        User user2 = new User();
        user2.setId(2);
        user2.setEmail("user2@gmail.com");
        user2.setValidUser(true);

        users = new ArrayList<>();

        Collections.addAll(users, user, user2);

        board = new Board();
        board.setId(1);
        board.setUser(user);

        BoardsHasUsers boardsHasUsers = new BoardsHasUsers();
        boardsHasUsers.setId(1);
        boardsHasUsers.setUser(user);
        boardsHasUsers.setBoard(board);

        boardsHasUsersList = new ArrayList<>();
        boardsHasUsersList.add(boardsHasUsers);
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
    public void getUsersTest() throws Exception{
        when(this.userService.getUsers()).thenReturn(users);

        MvcResult mvcResult =   this.mockMvc.perform( get("/api/users"))
                                .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        String resUsers = mvcResult.getResponse().getContentAsString();
        assertEquals( resUsers , this.objectMapper.writeValueAsString(users));
    }

    @Test
    public void getUserTest() throws Exception{
        when(this.userService.findById(1)).thenReturn(user);

        MvcResult mvcResult = this.mockMvc.perform( get("/api/users/1") ).andExpect(status().isOk())
                              .andReturn();

       assertEquals( 200 , mvcResult.getResponse().getStatus());
       assertEquals( mvcResult.getResponse().getContentAsString(), this.objectMapper.writeValueAsString(user));
    }

    @Test
    public void registerTest() throws Exception{
        when(this.userService.createUser(user)).thenReturn(true);
        
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK ,
            HttpStatus.OK.value(),
            "Successfully Created!",
            "Ok",
            true ,
            true);

        MvcResult mvcResult = this.mockMvc.perform( post("/api/register").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(user)))
                              .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertEquals( this.objectMapper.writeValueAsString(httpResponse) ,  mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void sendVerificationTest() throws Exception{
        when(this.userService.sendVertification("user@gmail.com")).thenReturn(true);
        
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK,
            HttpStatus.OK.value(),
            "Successfully Sent!",
            HttpStatus.OK.getReasonPhrase(),
            true,
            null
        );

        MvcResult mvcResult = this.mockMvc.perform( get("/api/send-verification?email=user@gmail.com") )
                              .andExpect(status().isOk())
                              .andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus() );
        assertEquals( this.objectMapper.writeValueAsString(httpResponse), mvcResult.getResponse().getContentAsString());          

    }

    @Test
    public void loginTest() throws JsonProcessingException, Exception{
        when(this.userService.loginUser(user)).thenReturn(user);


        MvcResult mvcResult = this.mockMvc.perform( post("/api/login").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(user)) )
                              .andExpect(status().isAccepted())
                              .andReturn();
        
        assertEquals( 202 , mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse().getContentAsString());
        assertEquals( this.jwtProvider.generateToken("user1@gmail.com"), mvcResult.getResponse().getHeader("Authorization"));

        verify( this.userService , times(1)).loginUser(user);
    }

    @Test
    public void getMembersTest() throws Exception{
        when(this.boardsHasUsersService.findMember(1)).thenReturn(boardsHasUsersList);

        MvcResult mvcResult = this.mockMvc.perform( get("/api/boards/1/members") )
                              .andExpect( status().isOk() )
                              .andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        verify( this.boardsHasUsersService , times(1)).findMember(1);
        
    }

    @Test
    public void updateUserTest() throws JsonProcessingException, Exception{
        when(this.userService.updateUser(user)).thenReturn(user);

        MvcResult mvcResult = this.mockMvc.perform( put("/api/update-user").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(user)))
                              .andExpect( status().isOk() )
                              .andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull( mvcResult.getResponse().getContentAsString());
        verify( this.userService , times(1)).updateUser(user);
    }

    @Test
    public void updateImageTest() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "file.png",
                MediaType.IMAGE_PNG_VALUE,
                new FileInputStream(new java.io.File(
                        "D:\\fullstack_projects\\ojt\\ai-wms-backend\\src\\main\\resources\\static\\img\\jennie.jpg")));

        when(this.userService.updateImage(mockMultipartFile, 1)).thenReturn(user);

        MvcResult mvcResult = this.mockMvc
                .perform(multipart(HttpMethod.POST, "/api/users/1/upload-image").file(mockMultipartFile))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void toggleBoardmarkTest() throws JsonProcessingException, Exception {
        when(this.bookmarkService.toggleBoardBookmark(boardBookmark)).thenReturn(boardBookmark);
                      

        MvcResult mvcResult = this.mockMvc.perform( post("/api/users/1/board-bookmark").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(boardBookmark)))
                              .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        assertNotNull( mvcResult.getResponse().getContentAsString());
        verify(this.bookmarkService,times(1)).toggleBoardBookmark(boardBookmark);
        
    }

    @Test
    public void getBoardBookmarksForUserTest() throws Exception {
        when(this.bookmarkService.getBoardBookmarksForUser(user.getId())).thenReturn(boardMarkList);

        MvcResult mvcResult =   this.mockMvc.perform( get("/api/users/1/board-bookmarks"))
                                .andExpect(status().isOk()).andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus());
        String resUsers = mvcResult.getResponse().getContentAsString();
        assertEquals( resUsers , this.objectMapper.writeValueAsString(boardMarkList));

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void forgetPasswordUserTest() throws Exception{

        when(this.userService.forgetPassword("user@gmail.com")).thenReturn(true);
        
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK,
            HttpStatus.OK.value(),
            "Successfully !",
            HttpStatus.OK.getReasonPhrase(),
            true,
            null
        );

        MvcResult mvcResult = this.mockMvc.perform( get("/api/forget-password?email=user@gmail.com") )
                              .andExpect(status().isOk())
                              .andReturn();
        assertEquals( 200 , mvcResult.getResponse().getStatus() );
        assertEquals( this.objectMapper.writeValueAsString(httpResponse), mvcResult.getResponse().getContentAsString());     

    }

    @Test
    public void changePasswordUserTest() throws Exception{

        when(this.userService.changePassword(user)).thenReturn(true);
        
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK,
            HttpStatus.OK.value(),
            "Successfully Sent!",
            HttpStatus.OK.getReasonPhrase(),
            true,
            null
        );

        MvcResult mvcResult = this.mockMvc.perform( put("/api/change-password").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(user)))
          .andExpect( status().isOk() ) .andReturn();
           assertEquals( 200 , mvcResult.getResponse().getStatus());
           assertNotNull( mvcResult.getResponse().getContentAsString());
           verify( this.userService , times(1)).changePassword(user);         
    }


    @Test
    public void deleteImageTest() throws Exception{
        when(this.userService.deleteImage(user)).thenReturn(user);
        MvcResult mvcResult = this.mockMvc.perform( put("/api/delete-img").contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(user)) )
                              .andExpect(status().isOk())
                              .andReturn();
        assertTrue(mvcResult.getResponse().getStatus() == 200 );
        HttpResponse<User> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.OK,
            HttpStatus.OK.value(),
            "Successfully Delete!",
            "Ok",
            true,
            user
        );
        assertNotNull( mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void generateReportTest() throws Exception {
        when(this.reportService.exportReport("pdf")).thenReturn("report generated in path D:\\Penta\\JasperReport");
        MvcResult mvcResult = this.mockMvc.perform( get("/api/boards/1/members/report").param("format", "pdf") )
                             .andExpect(status().isOk())
                             .andReturn();
        verify(this.reportService,times(1)).exportReport("pdf");
        assertTrue( mvcResult.getResponse().getStatus() == 200 );
    }

}
