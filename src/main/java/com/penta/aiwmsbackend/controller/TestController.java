package com.penta.aiwmsbackend.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.exception.handler.UserControllerAdvice;

@Controller
public class TestController extends UserControllerAdvice {

    @Autowired
    JavaMailSender mailSender;

    @GetMapping("/")
    public boolean test() throws DuplicateEmailException, UnsupportedEncodingException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom("myominhtoo2003@gmail.com", "You see");
        // First Method
        // helper.setTo(new String[] { "myominhtoo@ucsy.edu.mm",
        // "sunandaraung1211@gmail.com", "kyawswarapr2020@gmail.com",
        // "kaungmyat97k@gmail.com" });

        // Second Method
        // helper.setTo(
        // InternetAddress.parse("myominhtoo@ucsy.edu.mm, sunandaraung1211@gmail.com,
        // kyawswarapr2020@gmail.com"));
        helper.setSubject("Test");
        helper.setText("<code>const [state,setState] = useState()</code>", true);
        mailSender.send(mimeMessage);

        throw new DuplicateEmailException("Hi maung ly");

    }

}
