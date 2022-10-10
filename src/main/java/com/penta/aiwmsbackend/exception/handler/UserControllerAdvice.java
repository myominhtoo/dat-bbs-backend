package com.penta.aiwmsbackend.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.exception.custom.InvalidCodeException;
import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.model.bean.HttpResponse;

@RestControllerAdvice
public class UserControllerAdvice {

    
    @ExceptionHandler({DuplicateEmailException.class})
    public ResponseEntity<HttpResponse> duplicateEmailException( DuplicateEmailException e ){
        HttpResponse httpResponse = new HttpResponse( new Date() , HttpStatus.BAD_REQUEST , HttpStatus.BAD_REQUEST.value() ,
         e.getMessage() , HttpStatus.BAD_REQUEST.getReasonPhrase() , false );
        return new ResponseEntity<HttpResponse>( httpResponse , HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<HttpResponse> badCredentialException( BadCredentialsException e ){
        HttpResponse httpResponse = new HttpResponse( new Date() , HttpStatus.UNAUTHORIZED , HttpStatus.UNAUTHORIZED.value(),
         e.getMessage() , HttpStatus.UNAUTHORIZED.getReasonPhrase() , false );
        
        return new ResponseEntity<HttpResponse>( httpResponse , httpResponse.getHttpStatus());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<HttpResponse> usernameNotFoundException( UsernameNotFoundException e  ){
        HttpResponse httpResponse = new HttpResponse( new Date() , HttpStatus.UNAUTHORIZED , HttpStatus.UNAUTHORIZED.value(),
         e.getMessage() , HttpStatus.UNAUTHORIZED.getReasonPhrase() , false );
        
        return new ResponseEntity<HttpResponse>( httpResponse , httpResponse.getHttpStatus());
    }

    @ExceptionHandler({InvalidEmailException.class})
    public ResponseEntity<HttpResponse> invalidEmailException( InvalidEmailException e  ){
        HttpResponse httpResponse = new HttpResponse( new Date() , HttpStatus.BAD_REQUEST , HttpStatus.BAD_REQUEST.value(),
         e.getMessage() , HttpStatus.BAD_REQUEST.getReasonPhrase() , false );
        
        return new ResponseEntity<HttpResponse>( httpResponse , httpResponse.getHttpStatus());
    }

    @ExceptionHandler({InvalidCodeException.class})
    public ResponseEntity<HttpResponse> invalidCodeException( InvalidCodeException e  ){
        HttpResponse httpResponse = new HttpResponse( new Date() , HttpStatus.BAD_REQUEST , HttpStatus.BAD_REQUEST.value(),
         e.getMessage() , HttpStatus.BAD_REQUEST.getReasonPhrase() , false );
        
        return new ResponseEntity<HttpResponse>( httpResponse , httpResponse.getHttpStatus());
    }

}
