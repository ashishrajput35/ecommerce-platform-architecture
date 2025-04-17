package com.ecommerce.platform.auth.common.exception.custom;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends CustomException{

   public EmailAlreadyExistsException(String message){

        super(message, HttpStatus.CONFLICT);
    }
}
