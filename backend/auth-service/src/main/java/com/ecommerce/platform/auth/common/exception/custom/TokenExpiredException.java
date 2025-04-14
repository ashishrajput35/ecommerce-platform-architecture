package com.ecommerce.platform.auth.common.exception.custom;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends CustomException {
    public TokenExpiredException() {
        super("Token has expired or is invalid", HttpStatus.UNAUTHORIZED);
    }
}