package com.penta.aiwmsbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.service.BoardsHasUsersService;
import com.penta.aiwmsbackend.model.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest
{

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BoardsHasUsersService boardsHasUsersService;

    private static List<User> users;
    private static User user;

    @BeforeAll
    public static void runBeforeAll(){
        user = new User();
        user.setId(1);
        user.setEmail("user1@gmail.com");
        user.setValidUser(true);

        User user2 = new User();
        user2.setId(2);
        user2.setEmail("user2@gmail.com");
        user2.setValidUser(true);

        users = new ArrayList<>();

        Collections.addAll( users , user , user2 );
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
}
