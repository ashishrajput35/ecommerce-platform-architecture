package com.ecommerce.platform.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for User")
public class UserDto {

    private Long id;

    @NotBlank(message = "Full name is required")
    @Schema(example = "Ashish Rajput", description = "User's full name")
    private String fullName;

    @Email
    @NotBlank(message = "Email is required")
    @Schema(example = "raj@example.com", description = "User's email address")
    private String email;

    @Schema(example = "9876543210", description = "User's phone number")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be a valid 10-digit Indian mobile number")
    private String phone;

    @NotBlank(message = "Role is required")
    @Schema(example = "USER", description = "User role [ADMIN, USER, MANAGER]")
    private String role;
}
