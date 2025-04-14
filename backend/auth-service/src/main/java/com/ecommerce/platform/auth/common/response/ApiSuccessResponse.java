package com.ecommerce.platform.auth.common.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiSuccessResponse<T> extends ApiBaseResponse {

    private final T data;

    @Builder
    public ApiSuccessResponse(LocalDateTime timestamp, int status, String message, T data) {
        super(timestamp, status, message);
        this.data = data;
    }
}
