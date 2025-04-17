package com.ecommerce.platform.user.util;

import com.ecommerce.platform.user.common.response.ApiErrorResponse;
import com.ecommerce.platform.user.common.response.ApiSuccessResponse;

import java.util.List;

public class ApiResponseUtil {

    public static <T> ApiSuccessResponse<T> success(T data, String message, int status) {
        return ApiSuccessResponse.<T>builder()
                .timestamp(ApiDateTimeUtil.currentDateTime())
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    public static ApiErrorResponse error(List<String> errors, String message, int status) {
        return ApiErrorResponse.builder()
                .timestamp(ApiDateTimeUtil.currentDateTime())
                .status(status)
                .message(message)
                .errors(errors)
                .build();
    }
}
