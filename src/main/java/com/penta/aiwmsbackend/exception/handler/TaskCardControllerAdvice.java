package com.penta.aiwmsbackend.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.penta.aiwmsbackend.exception.custom.DuplicateTaskCardNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidBoardIdException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;

@RestControllerAdvice
public class TaskCardControllerAdvice {

    @ExceptionHandler({ DuplicateTaskCardNameException.class })
    public ResponseEntity<HttpResponse> dublicateTaskCardNameException(DuplicateTaskCardNameException e) {

        HttpResponse httpResponse = new HttpResponse(new Date(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), false);

        return new ResponseEntity<HttpResponse>(httpResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidBoardIdException.class })

    public ResponseEntity<HttpResponse> invalidBoardIdException(InvalidBoardIdException e) {

        HttpResponse httpResponse = new HttpResponse(new Date(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), false);

        return new ResponseEntity<HttpResponse>(httpResponse, HttpStatus.BAD_REQUEST);
    }
}
