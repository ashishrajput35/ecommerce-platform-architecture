package com.ecommerce.platform.auth.common.exception.custom;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException() {
        super("User already exists with this email or username", HttpStatus.CONFLICT);
    }
}
