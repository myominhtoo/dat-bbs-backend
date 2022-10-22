package com.penta.aiwmsbackend.exception.handler;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.exception.custom.FileNotSupportException;
import com.penta.aiwmsbackend.exception.custom.InvalidCodeException;
import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler({ DuplicateEmailException.class })
    public ResponseEntity<HttpResponse<Boolean>> duplicateEmailException(DuplicateEmailException e) {
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(LocalDate.now(), HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), false, true);
        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<HttpResponse<Boolean>> badCredentialException(BadCredentialsException e) {
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(LocalDate.now(), HttpStatus.UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), false, true);

        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }

    @ExceptionHandler({ UsernameNotFoundException.class })
    public ResponseEntity<HttpResponse<Boolean>> usernameNotFoundException(UsernameNotFoundException e) {
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(LocalDate.now(), HttpStatus.UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), false, true);

        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }

    @ExceptionHandler({ InvalidEmailException.class })
    public ResponseEntity<HttpResponse<Boolean>> invalidEmailException(InvalidEmailException e) {
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(LocalDate.now(), HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), false, true);

        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }

    @ExceptionHandler({ InvalidCodeException.class })
    public ResponseEntity<HttpResponse<Boolean>> invalidCodeException(InvalidCodeException e) {
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(LocalDate.now(), HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), false, true);

        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }

    @ExceptionHandler({ FileNotSupportException.class })
    public ResponseEntity<HttpResponse<Boolean>> FileNotSupportException(InvalidCodeException e) {
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(LocalDate.now(), HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), false, true);

        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }

}
