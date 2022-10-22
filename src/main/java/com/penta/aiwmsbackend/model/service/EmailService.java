package com.penta.aiwmsbackend.model.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService {

    JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendToOneUser(
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

    public boolean sendToManyUser(String fromMail,
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
