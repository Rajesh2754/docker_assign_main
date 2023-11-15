package com.walmart.LoginModule.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserException extends RuntimeException{
    public UserException(String message){
        super(message);
    }
}
