package com.ecommerce.platform.user.common.exception.custom;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException{

    public BadRequestException(String message){

        super(message, HttpStatus.BAD_REQUEST);
    }
}
