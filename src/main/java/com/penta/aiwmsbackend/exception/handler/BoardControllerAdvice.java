package com.penta.aiwmsbackend.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.penta.aiwmsbackend.exception.custom.CreatePermissionException;
import com.penta.aiwmsbackend.exception.custom.JoinPermissionException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;

/*
 * writer exceptions handeler methods for board
 */
@RestControllerAdvice
public class BoardControllerAdvice {

    @ExceptionHandler({ JoinPermissionException.class })
    public ResponseEntity<HttpResponse> joinPermissionException(JoinPermissionException e) {
        HttpResponse httpResponse = new HttpResponse(
                new Date(),
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.value(),
                e.getMessage(),
                "You don't have permission to join this board!",
                false);
        return new ResponseEntity<HttpResponse>(httpResponse, httpResponse.getHttpStatus());
    }

    @ExceptionHandler({ CreatePermissionException.class })
    public ResponseEntity<HttpResponse> createPermissionException( CreatePermissionException e) {
        HttpResponse httpResponse = new HttpResponse(
                new Date(),
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.value(),
                e.getMessage(),
                "You need first to create account to create board!",
                false);
        return new ResponseEntity<HttpResponse>(httpResponse, httpResponse.getHttpStatus());
    }


}
