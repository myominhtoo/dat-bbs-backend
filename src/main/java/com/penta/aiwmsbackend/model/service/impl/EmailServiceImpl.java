package com.penta.aiwmsbackend.model.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
    JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean verifyEmail(
            String fromMail,
            String mailHeader,
            String sentTo,
            String subject,
            String mailContent) throws UnsupportedEncodingException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(fromMail, mailHeader);
        helper.setTo(sentTo);
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(mimeMessage);
        return false;
    }

    @Override
    public boolean InviteMember(String fromMail,
            String mailHeader,
            String[] sentTo,
            String subject,
            String mailContent) throws UnsupportedEncodingException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(fromMail, mailHeader);
        helper.setTo(sentTo);
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(mimeMessage);
        return false;
    }

}
