package com.ecommerce.platform.auth.common.exception.custom;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends CustomException {
    public InvalidCredentialsException(String message) {
        super(message , HttpStatus.UNAUTHORIZED);
    }
}
