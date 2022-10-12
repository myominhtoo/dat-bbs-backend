package com.penta.aiwmsbackend.model.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.exception.custom.InvalidCodeException;
import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.model.entity.User;

public interface UserService {
    boolean isDuplicateEmail( String email );

    boolean createUser( User user ) throws InvalidEmailException ,InvalidCodeException;

    boolean sendVertification( String email ) throws DuplicateEmailException, UnsupportedEncodingException, MessagingException;

    boolean loginUser( User user ) throws BadCredentialsException , UsernameNotFoundException;

    List<User> getUsers();
}
