package com.ecommerce.platform.auth.common.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final int code;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.code = status.value();
    }
}