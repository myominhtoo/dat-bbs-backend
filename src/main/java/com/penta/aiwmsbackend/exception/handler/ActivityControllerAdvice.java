package com.penta.aiwmsbackend.exception.handler;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.penta.aiwmsbackend.exception.custom.DuplicateActivityNameException;
import com.penta.aiwmsbackend.exception.custom.InvalidActivityIdException;
import com.penta.aiwmsbackend.exception.custom.InvalidTaskCardIdException;

import com.penta.aiwmsbackend.model.bean.HttpResponse;

@RestControllerAdvice
public class ActivityControllerAdvice {
    
    @ExceptionHandler({ InvalidTaskCardIdException.class})
    public ResponseEntity<HttpResponse<Boolean>> InvalidTaskCardIdException(InvalidTaskCardIdException e){
        HttpResponse<Boolean> httpResponse= new HttpResponse<>(LocalDate.now(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),
                                            e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), false , true );
        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse , HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ DuplicateActivityNameException.class })
    public ResponseEntity<HttpResponse<Boolean>> duplicateActivityNameException(DuplicateActivityNameException e) {
        HttpResponse<Boolean> httpResponse = new HttpResponse<>(LocalDate.now(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                                             e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), false , true );

        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidActivityIdException.class})
    public ResponseEntity<HttpResponse<Boolean>> InvalidActivityException ( InvalidActivityIdException e){
        HttpResponse<Boolean> httpResponse= new HttpResponse<>(LocalDate.now(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),
                                           e.getMessage(),HttpStatus.BAD_REQUEST.getReasonPhrase(),false ,true);
        return new ResponseEntity<HttpResponse<Boolean>>(httpResponse , HttpStatus.BAD_REQUEST);
    }
    
}
