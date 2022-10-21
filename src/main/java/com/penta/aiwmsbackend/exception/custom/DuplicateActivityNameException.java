package com.penta.aiwmsbackend.exception.custom;

public class DuplicateActivityNameException extends Exception {
    public DuplicateActivityNameException( String message ){
        super(message);
    }
}
