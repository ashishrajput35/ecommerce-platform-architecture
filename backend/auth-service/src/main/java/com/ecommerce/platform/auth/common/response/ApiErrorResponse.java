package com.ecommerce.platform.auth.common.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ApiErrorResponse extends ApiBaseResponse {

    private final List<String> errors;

    @Builder
    public ApiErrorResponse(LocalDateTime timestamp, int status, String message, List<String> errors) {
        super(timestamp, status, message);
        this.errors = errors;
    }
}
