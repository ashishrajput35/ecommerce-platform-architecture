package com.ecommerce.platform.auth.controller;


import com.ecommerce.platform.auth.common.exception.custom.InvalidCredentialsException;
import com.ecommerce.platform.auth.common.response.ApiSuccessResponse;
import com.ecommerce.platform.auth.dto.AuthResponse;
import com.ecommerce.platform.auth.dto.LoginRequest;
import com.ecommerce.platform.auth.dto.RegisterRequest;
import com.ecommerce.platform.auth.services.AuthService;
import com.ecommerce.platform.auth.util.ApiResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * REST Controller that handles authentication-related endpoints like register and login.
 *
 * Base URL: /api/v1/auth
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor // Lombok: Generates constructor for final fields (used for DI)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request){

        // The service handles business logic; controller only routes requests
        AuthResponse response = authService.userRegister(request);

        // ❗️Check if the response is null (something went wrong)
        if (response == null) {
            throw new InvalidCredentialsException("User registration failed due to unknown error.");
        }

        // You can also add custom validation logic here, e.g.
        // if (response.getUserId() == null) { ... }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseUtil.success(response, "User successfully registered", 201));
    }

    /**
     * Endpoint for user login
     * @param request LoginRequest containing credentials
     * @return AuthResponse containing access token
     */
    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<AuthResponse>>  login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.userLogin(request);

        // ❗️Check if the response is null (something went wrong)
        if (response == null) {

            throw new InvalidCredentialsException("User Login failed due to unknown error.");
        }

        // You can also add custom validation logic here, e.g.
        // if (response.getUserId() == null) { ... }

        return ResponseEntity.ok(ApiResponseUtil.success(response, "Login successful", 200));


    }
}
