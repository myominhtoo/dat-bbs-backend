package com.penta.aiwmsbackend.model.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.model.entity.User;

public interface UserService {
    boolean isDuplicateEmail( String email );

    boolean createUser( User user );

    void sendVertification( String email ) throws DuplicateEmailException, UnsupportedEncodingException, MessagingException;
}
