package com.ecommerce.platform.user.common.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public abstract class ApiBaseResponse {

    protected final LocalDateTime timestamp;
    protected final int status;
    protected final String message;

    protected ApiBaseResponse(LocalDateTime timestamp, int status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }
}
