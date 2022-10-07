package com.penta.aiwmsbackend.exception.custom;

public class DuplicateEmailException extends Exception {
    public DuplicateEmailException( String message ){
        super(message);
    }
}
