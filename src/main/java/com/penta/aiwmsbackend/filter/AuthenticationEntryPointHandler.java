package com.penta.aiwmsbackend.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.aiwmsbackend.model.bean.ErrorResponse;

@Component
public class AuthenticationEntryPointHandler extends Http403ForbiddenEntryPoint {

    @Override
    public void commence(HttpServletRequest req , HttpServletResponse res , AuthenticationException arg2)
            throws IOException {
        ErrorResponse response = new ErrorResponse(
            new Date(),
            HttpStatus.FORBIDDEN,
            HttpStatus.FORBIDDEN.value(),
            HttpStatus.FORBIDDEN.getReasonPhrase(),
            "You need to login!"
        );
        res.setContentType( MediaType.APPLICATION_JSON_VALUE );
        res.setStatus(HttpStatus.FORBIDDEN.value());
        OutputStream outputStream = res.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue( outputStream, response );
        outputStream.flush();
    }
    

}
