package com.ecommerce.platform.user.common.exception.custom;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomException{

    public ResourceNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
