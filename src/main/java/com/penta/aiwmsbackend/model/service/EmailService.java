package com.penta.aiwmsbackend.model.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

public interface EmailService {

    boolean verifyEmail(String fromMail, String mailHeader, String sentTo, String subject, String mailContent)
            throws MessagingException, UnsupportedEncodingException;

    boolean InviteMember(String fromMail, String mailHeader, String[] sentTo,
            String subject,
            String mailContent) throws UnsupportedEncodingException, MessagingException;
}
