package com.penta.aiwmsbackend.exception.custom;

public class InvalidEmailException extends Exception {
    public InvalidEmailException( String message ){
        super(message);
    }
}
