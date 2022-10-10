package com.penta.aiwmsbackend.model.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.model.entity.User;

public interface UserService {
    boolean isDuplicateEmail( String email );

    boolean createUser( User user );

    boolean sendVertification( String email ) throws DuplicateEmailException, UnsupportedEncodingException, MessagingException;

    boolean loginUser( User user ) throws BadCredentialsException , UsernameNotFoundException;
}
