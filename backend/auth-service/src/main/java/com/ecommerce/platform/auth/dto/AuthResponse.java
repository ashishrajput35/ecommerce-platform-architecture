package com.ecommerce.platform.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO is sent back to the client after successful login or registration.
 * it contains the JWT token and user info.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    // JWT token that will be used for authorization
    private String token;

    //Username of the authenticated user
    private String username;

    private String useremail;

    //Role of the user, e.g., USER or ADMIN
    private String role;

    // Token creation time (optional enhancement)
    private Long issuedAt;

    // Token expiration time in milliseconds since epoch (optional enhancement)
    private Long expiresIn;

}
