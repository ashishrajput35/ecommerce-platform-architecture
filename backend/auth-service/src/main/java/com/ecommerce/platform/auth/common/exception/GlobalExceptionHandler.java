package com.ecommerce.platform.auth.common.exception;

import com.ecommerce.platform.auth.common.exception.custom.CustomException;
import com.ecommerce.platform.auth.common.response.ApiErrorResponse;
import com.ecommerce.platform.auth.util.ApiResponseUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handles application-wide exceptions for cleaner API error responses.
 */
@RestControllerAdvice // Global exception handler for all @RestController classes
public class GlobalExceptionHandler {

    // 🔸 Handle custom exceptions (extends CustomException)
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomException(CustomException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(ApiResponseUtil.error(
                        Collections.singletonList(ex.getMessage()),
                        ex.getMessage(),
                        ex.getStatus().value()
                ));
    }

    // 🔸 Handle validation exceptions (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
                ApiResponseUtil.error(errors, "Validation failed", HttpStatus.BAD_REQUEST.value())
        );
    }

    // 🔸 Handle constraint violations (e.g., @NotNull at class level)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
                ApiResponseUtil.error(errors, "Constraint violation", HttpStatus.BAD_REQUEST.value())
        );
    }

    // 🔸 Handle all other exceptions (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponseUtil.error(
                        Collections.singletonList(ex.getMessage()),
                        "Something went wrong",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                )
        );
    }

}
