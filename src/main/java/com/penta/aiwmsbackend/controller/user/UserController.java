package com.penta.aiwmsbackend.controller.user;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.exception.custom.InvalidCodeException;
import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.exception.handler.UserControllerAdvice;
import com.penta.aiwmsbackend.model.bean.HttpResponse;
import com.penta.aiwmsbackend.model.entity.BoardsHasUsers;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.service.BoardsHasUsersService;
import com.penta.aiwmsbackend.model.service.UserService;
import com.penta.aiwmsbackend.util.JwtProvider;

@RestController
@RequestMapping(value = "/api")
public class UserController extends UserControllerAdvice {

    private UserService userService;
    private JwtProvider jwtProvider;
    private BoardsHasUsersService boardsHasUsersService;

    @Autowired
    public UserController(UserService userService,
            JwtProvider jwtProvider, BoardsHasUsersService boardsHasUsersService) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.boardsHasUsersService = boardsHasUsersService;
    }

    @GetMapping(value = "/send-verification")
    public ResponseEntity<HttpResponse<Boolean>> sendVerification(
            @RequestParam(value = "email", required = true) String email)
            throws UnsupportedEncodingException, DuplicateEmailException, MessagingException {
        HttpResponse<Boolean> httpResponse = new HttpResponse<>();

        httpResponse.setTimestamp(new Date());
        if (userService.sendVertification(email)) {
            httpResponse.setHttpStatus(HttpStatus.OK);
            httpResponse.setHttpStatusCode(HttpStatus.OK.value());
            httpResponse.setMessage("Successfully Sent!");
            httpResponse.setOk(true);
            httpResponse.setReason(HttpStatus.OK.getReasonPhrase());
        } else {
            httpResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            httpResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
            httpResponse.setMessage("Something went wrong!");
            httpResponse.setOk(false);
            httpResponse.setReason(HttpStatus.BAD_REQUEST.getReasonPhrase());
        }

        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }

<<<<<<< HEAD
    @PostMapping(value = "/login")
    public ResponseEntity<HttpResponse<Boolean>> loginUser(@RequestBody User user)
            throws UsernameNotFoundException, BadCredentialsException {
        boolean loginStatus = this.userService.loginUser(user);
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
                new Date(),
                loginStatus ? HttpStatus.ACCEPTED : HttpStatus.UNAUTHORIZED,
                loginStatus ? HttpStatus.ACCEPTED.value() : HttpStatus.UNAUTHORIZED.value(),
                loginStatus ? "Successfully Logged In!" : "Failed to login!",
                loginStatus ? "OK" : "Unknown error occured!",
                loginStatus ? true : false,
                true);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", this.jwtProvider.generateToken(user.getEmail()));
        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpHeaders, httpResponse.getHttpStatus());
=======
    @PostMapping( value = "/login" )
    public ResponseEntity<HttpResponse<User>> loginUser( @RequestBody User user ) throws UsernameNotFoundException , BadCredentialsException {
        User loginStatus = this.userService.loginUser( user );
        HttpResponse<User> httpResponse = new HttpResponse<>(
            new Date(),
            loginStatus != null ? HttpStatus.ACCEPTED: HttpStatus.UNAUTHORIZED ,
            loginStatus != null ? HttpStatus.ACCEPTED.value() : HttpStatus.UNAUTHORIZED.value(),
            loginStatus != null ? "Successfully Logged In!" : "Failed to login!",
            loginStatus != null ? "OK" : "Unknown error occured!",
            loginStatus != null ? true : false,
            loginStatus
            
        );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add( "Authorization", this.jwtProvider.generateToken( user.getEmail() ));
        return new ResponseEntity<HttpResponse<User>>( httpResponse ,httpHeaders , httpResponse.getHttpStatus() );
>>>>>>> b3e9d6eba296c033cb246ba241231311f3953c39
    }

    @PostMapping(value = "/register")
    public ResponseEntity<HttpResponse<Boolean>> registerUser(@RequestBody User user)
            throws InvalidEmailException, InvalidCodeException {
        boolean registerStatus = this.userService.createUser(user);
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
                new Date(),
                registerStatus ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                registerStatus ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                registerStatus ? "Successfully Created!" : "Failed to create!",
                registerStatus ? "Ok" : "Unknown error occured!",
                registerStatus ? true : false,
                true);
        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }

    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping(value = "/boards/{boardId}/members")
    public List<BoardsHasUsers> getMembers(@PathVariable("boardId") Integer boardId) {
        return this.boardsHasUsersService.findMember(boardId);
    }

}
