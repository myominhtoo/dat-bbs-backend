package com.penta.aiwmsbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.exception.custom.DuplicateValidEmailException;
import com.penta.aiwmsbackend.exception.custom.InvalidCodeException;
import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.UserRepo;
import com.penta.aiwmsbackend.model.service.EmailService;
import com.penta.aiwmsbackend.model.service.UserService;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private static User user;

    private static List<User> users;

    @BeforeAll
    public static void runBeforeAll() {
        user = new User();
        user.setId(1);
        user.setUsername("test user");
        user.setEmail("user1@gmail.com");
        user.setCode(12345);
        user.setPassword("123");
        user.setValidUser(true);

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("test user2");
        user2.setEmail("user2@gmail.com");
        user2.setPassword("123");
        user.setValidUser(true);

        users = new ArrayList<>();

        Collections.addAll(users, user, user2);
    }

    @Test
    public void isDuplicateEmailTest(){
        when(this.userRepo.findByEmail("test@gmail.com")).thenReturn(Optional.empty());

        assertFalse(this.userService.isDuplicateEmail("test@gmail.com"));
        verify( this.userRepo , times(1)).findByEmail("test@gmail.com");
    }

    @Test
    public void findByIdTest(){
        when(this.userRepo.findById(1)).thenReturn(Optional.of(user));
        assertNotNull(this.userService.findById(1));

        when(this.userRepo.findById(1)).thenReturn(Optional.empty());
        assertNull(this.userService.findById(1));

        verify(this.userRepo  , times(2)).findById(1);

    }

    @Test
    public void getUsers(){
        when(this.userRepo.findAll()).thenReturn(users);

        assertEquals(this.userService.getUsers().size() , users.size());
        verify( this.userRepo , times(1)).findAll();
    }

    @Test
    public void createUserTest() throws InvalidEmailException, InvalidCodeException, DuplicateValidEmailException{
        when(this.userRepo.findByEmail("user1@gmail.com")).thenReturn(Optional.of(user));
        when(this.userRepo.save(user)).thenReturn(user);

        assertTrue(this.userService.createUser(user));

        when(this.userRepo.save(user)).thenReturn(null);
        assertTrue(!this.userService.createUser(user));

        verify(this.userRepo , times(2)).save(user);
    }

    @Test
    public void updateUserTest() throws InvalidEmailException,
    InvalidCodeException{
    when(this.userRepo.findById(1)).thenReturn(Optional.of(user));
    Optional<User> userId = this.userRepo.findById(user.getId());
    when(this.userRepo.save(user)).thenReturn(user);
    this.userRepo.save(user); 
    verify( this.userRepo , times(1)).save(user);
    }

    @Test
    public void loginUserTest() {
        User returnUser = new User();
        returnUser.setId(1);
        returnUser.setPassword(passwordEncoder.encode("123"));
        returnUser.setEmail("user1@gmail.com");
        when(this.userRepo.findByEmail("user1@gmail.com")).thenReturn(Optional.of(returnUser));
        when(passwordEncoder.matches("123", returnUser.getPassword())).thenReturn(true);

        user.setPassword("123");
        assertNotNull(this.userService.loginUser(user));

    }

    @Test
    public void sendVertificationtTest() throws UnsupportedEncodingException,
    MessagingException, DuplicateEmailException{
    when(this.userRepo.findByEmail("user1@gmail.com")).thenReturn(Optional.empty());
    when(this.userRepo.save(user)).thenReturn(user);
    when(this.emailService.sendToOneUser( "test@gmail.com", "header",
    "user1@gmail.com", "submit", "test")).thenReturn(true);
    when(this.userRepo.findByEmail("user1@gmail.com")).thenReturn(Optional.of(user));
    Optional<User> userEmail = this.userRepo.findByEmail("user1@gmail.com");
    assertEquals(userEmail.get().getEmail(), user.getEmail());
    this.userRepo.save(user);
    verify(this.userRepo, times(1)).save(user);    

    }
    @Test
    public void uploadImage() throws FileNotFoundException, IOException{
        String pathname =System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
        .replace("target\\test-classes", "")+"src\\main\\resources\\static\\img\\";

        MockMultipartFile multipartFile = new MockMultipartFile("file", "file.png",
                                          MediaType.IMAGE_PNG_VALUE,
                                          new FileInputStream(new java.io.File(pathname+"logo-png.png")));
        when ( this.userRepo.findById(1)).thenReturn(Optional.of(user));
        this.userRepo.save(user);
        verify(this.userRepo,times(1)).save(user);
        
    }         
    
    @Test
    public void deleteImage(){
        when( this.userRepo.findById(1)).thenReturn(Optional.of(user));
        user.setImageUrl(null);
        this.userRepo.save(user);
        verify(this.userRepo,times(1)).save(user);
    }

   


    @Test
    public void forgetPasswordTest() throws InvalidEmailException, UnsupportedEncodingException, DuplicateEmailException{

        when(this.userRepo.findByEmail("user1@gmail.com")).thenReturn(Optional.of(user));

        User u=new User();

        u.setId(user.getId());
        u.setEmail(u.getEmail());
        u.setCode(123);
        when(this.userRepo.save(user)).thenReturn(user);
        assertNotNull( this.userService.forgetPassword(user.getEmail()));

        try {
            when(this.emailService.sendToOneUser( "test@gmail.com", "header",
            "user1@gmail.com", "submit", "test")).thenReturn(true);
           assertTrue(this.userService.forgetPassword("user1@gmail.com"));

        } catch (UnsupportedEncodingException e) {
       
            e.printStackTrace();
        } catch (MessagingException e) {
           
            e.printStackTrace();
        }
     
      }

    @Test
      public void changePasswordTest()throws InvalidEmailException{

        when(this.userRepo.findByEmail("user1@gmail.com")).thenReturn(Optional.of(user));

        User u=new User();

        u.setId(user.getId());
        u.setEmail(u.getEmail());
        u.setPassword("abc");
        when(this.userRepo.save(user)).thenReturn(user);

        assertNotNull( this.userService.forgetPassword(user.getEmail()));

      }

      @Test
      void getRpMember(){
          when(this.userRepo.findReportMember(1)).thenReturn(users);
          assertNotNull(users);
      }


}
