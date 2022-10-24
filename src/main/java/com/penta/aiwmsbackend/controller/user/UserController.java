package com.penta.aiwmsbackend.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
@CrossOrigin(originPatterns = "*")
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

        httpResponse.setTimestamp(LocalDate.now());
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

    @PostMapping(value = "/login")
    public ResponseEntity<HttpResponse<User>> loginUser(@RequestBody User user)
            throws UsernameNotFoundException, BadCredentialsException {
        User savedUser = this.userService.loginUser(user);
        HttpResponse<User> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                savedUser != null ? HttpStatus.ACCEPTED : HttpStatus.UNAUTHORIZED,
                savedUser != null ? HttpStatus.ACCEPTED.value() : HttpStatus.UNAUTHORIZED.value(),
                savedUser != null ? "Successfully Logged In!" : "Failed to login!",
                savedUser != null ? "OK" : "Unknown error occured!",
                savedUser != null ? true : false,
                savedUser != null ? savedUser : null);
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Authorization", this.jwtProvider.generateToken(user.getEmail()));
        return new ResponseEntity<HttpResponse<User>>(httpResponse, httpHeaders, httpResponse.getHttpStatus());

    }

    @PostMapping(value = "/register")
    public ResponseEntity<HttpResponse<Boolean>> registerUser(@RequestBody User user)
            throws InvalidEmailException, InvalidCodeException {
        boolean registerStatus = this.userService.createUser(user);
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
                LocalDate.now(),
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

    @PutMapping(value = "/update-user")
    public ResponseEntity<HttpResponse<Boolean>> UpdateUser(@RequestBody User user)
            throws InvalidEmailException, InvalidCodeException {
        boolean registerStatus = this.userService.updateUser(user);
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                registerStatus ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                registerStatus ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                registerStatus ? "Successfully Updated!" : "Failed to update!",
                registerStatus ? "Ok" : "Unknown error occured!",
                registerStatus ? true : false,
                true);
        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }

    @GetMapping(value = "/users/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        return this.userService.findById(userId);
    }

    @PostMapping(value = "/users/{id}/upload-image")
    public ResponseEntity<HttpResponse<User>> UploadImage(@RequestPart("file") MultipartFile file,
            @PathVariable("id") Integer id, HttpServletRequest res)
            throws IOException, com.penta.aiwmsbackend.exception.custom.FileNotSupportException {

        User registerStatus = this.userService.updateImage(file, id);
        HttpResponse<User> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                registerStatus != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST,
                registerStatus != null ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value(),
                registerStatus != null ? "Successfully Upload Photo!" : "Failed To Upload",
                registerStatus != null ? "OK" : "Unknown error occured!",
                registerStatus != null ? true : false,
                registerStatus != null ? registerStatus : null);
        return new ResponseEntity<HttpResponse<User>>(httpResponse,
                httpResponse.getHttpStatus());
    }

}
