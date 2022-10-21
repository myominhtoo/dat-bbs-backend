package com.penta.aiwmsbackend.exception.handler;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.penta.aiwmsbackend.exception.custom.InvalidTaskCardIdException;
import com.penta.aiwmsbackend.exception.custom.DuplicateTaskCardNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;

@RestControllerAdvice
public class TaskCardControllerAdvice {

    @ExceptionHandler({ DuplicateTaskCardNameException.class })
    public ResponseEntity<HttpResponse<Boolean>> duplicateTaskCardNameException(DuplicateTaskCardNameException e) {

        HttpResponse<Boolean> httpResponse = new HttpResponse<>(LocalDate.now(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), false , true );

        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidBoardIdException.class })
    public ResponseEntity<HttpResponse<Boolean>> invalidBoardIdException(InvalidBoardIdException e) {

        HttpResponse<Boolean> httpResponse = new HttpResponse<>(LocalDate.now(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), false , true );

        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, HttpStatus.BAD_REQUEST);
    }

}
