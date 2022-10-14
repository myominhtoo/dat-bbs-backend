package com.penta.aiwmsbackend.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.penta.aiwmsbackend.exception.custom.DuplicateStageNameInBoardException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;

@RestControllerAdvice
public class StageControllerAdvice {
    
    @ExceptionHandler({ DuplicateStageNameInBoardException.class })
    public ResponseEntity<HttpResponse> duplicateStageNameInBoardExceptin( DuplicateStageNameInBoardException e ){
        HttpResponse httpResponse = new HttpResponse(
            new Date(),
            HttpStatus.ALREADY_REPORTED,
            HttpStatus.ALREADY_REPORTED.value(),
            "There was error in creating stage",
            "You can't use same stage name in a board!",
            false
        );
        return new ResponseEntity<>( httpResponse , httpResponse.getHttpStatus() );
    }

}
