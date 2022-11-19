package com.penta.aiwmsbackend.exception.handler;

import java.time.LocalDate;

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
    public ResponseEntity<HttpResponse<Boolean>> joinPermissionException(JoinPermissionException e) {
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.value(),
                e.getMessage(),
                "You don't have permission to join this board!",
                false ,
                true );
        return new ResponseEntity<>(httpResponse, httpResponse.getHttpStatus());
    }

    @ExceptionHandler({ CreatePermissionException.class })
    public ResponseEntity<HttpResponse<Boolean>> createPermissionException( CreatePermissionException e) {
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
                LocalDate.now(),
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.value(),
                e.getMessage(),
                "You need first to create account to create board!",
                false , 
                true );
        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, httpResponse.getHttpStatus());
    }


}
