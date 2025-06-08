package com.ecommerce.platform.auth.services;

import com.ecommerce.platform.auth.common.exception.custom.UserAlreadyExistsException;
import com.ecommerce.platform.auth.dto.AuthResponse;
import com.ecommerce.platform.auth.dto.LoginRequest;
import com.ecommerce.platform.auth.dto.RegisterRequest;
import com.ecommerce.platform.auth.entities.User;
import com.ecommerce.platform.auth.repository.UserRepository;
import com.ecommerce.platform.auth.roles.Role;
import com.ecommerce.platform.auth.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * AuthService handles core authentication logic such as
 * user registration and login
 *
 * This service is marked as final to prevent inheritance
 */

@Service
@RequiredArgsConstructor // Lombok annotation to auto-generate construction for all final fields.
public final class AuthService {

    // Dependency injection of required components using constructor injection (Recommended practice).
    private final UserRepository userRepository; //Interface to interact with the User database table
    private final PasswordEncoder passwordEncoder; // Encrypts and matches user passwords.
    private final JwtService jwtService; // Handles creation and validation of JWT tokens.

    /**
     * Register a new user in the system
     *
     * @param registerRequest contains registration details like username, email, password, role
     * @return AuthResponse
     */
    public AuthResponse userRegister (RegisterRequest registerRequest){

        try {

            var user = User.builder()
                    .username(registerRequest.getUsername())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(registerRequest.getRole() != null ? registerRequest.getRole(): Role.USER) //Default role assignment if null
                    .build();

            // Persist the new user to the database
            userRepository.save(user);

            // Generate JWT token using username and optional claims (empty map here)
//            var jwt = jwtService.generateToken(user.getUsername(),new HashMap<>());

            // Build response object
            return AuthResponse.builder()
//                    .token(jwt)
                    .username(user.getUsername())
                    .role(user.getRole().name())
                    .useremail(user.getEmail())
//                    .issuedAt(jwtService.extractIssuedAtMillis(jwt)) // Token issue time (can be used on client side).
//                    .expiresIn(jwtService.extractExpirationMillis(jwt)) // Expiry time extracted from JWT.
                    .build();

        }catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("users_details.UKmld8vik5lrt9308nbcic8me3w")) {
                throw new UserAlreadyExistsException();
            }
            throw ex; // rethrow if it's a different issue
        }

    }

    /**
     * Authenticates an existing user by verifying email and password
     *
     * @param loginRequest Contains
     */
    public AuthResponse userLogin(LoginRequest loginRequest){

        // fetch user by email; throw exception if not found.
        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new RuntimeException("Invalid email"));//Generic message (security best practice).

        // Match raw password from request with encoded password in DB
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        // Generate JWT token  on successful login
        var jwt = jwtService.generateToken(user.getUsername(), new HashMap<>());

        // Build response object
        return AuthResponse.builder()
                .token(jwt)
                .username(user.getUsername())
                .role(user.getRole().name())
                .issuedAt(jwtService.extractIssuedAtMillis(jwt)) // Token issue time (can be used on client side).
                .expiresIn(jwtService.extractExpirationMillis(jwt)) // Expiry time extracted from JWT.
                .build();
    }
}
