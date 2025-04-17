package com.ecommerce.platform.user.common.exception.custom;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends CustomException{

    public EmailAlreadyExistsException(String message){

        super(message, HttpStatus.CONFLICT);
    }
}
