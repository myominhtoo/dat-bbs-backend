package com.penta.aiwmsbackend.exception.handler;

import java.time.LocalDate;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.penta.aiwmsbackend.exception.custom.DuplicateStageNameInBoardException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;

@RestControllerAdvice
public class StageControllerAdvice {
    
    @ExceptionHandler({ DuplicateStageNameInBoardException.class })
    public ResponseEntity<HttpResponse<Boolean>> duplicateStageNameInBoardExceptin( DuplicateStageNameInBoardException e ){
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(
            LocalDate.now(),
            HttpStatus.ALREADY_REPORTED,
            HttpStatus.ALREADY_REPORTED.value(),
            "There was error in creating stage",
            "You can't use same stage name in a board!",
            false ,
            true
        );
        return new ResponseEntity<>( httpResponse , httpResponse.getHttpStatus() );
    }

}
