package com.ecommerce.platform.user.controller;

import com.ecommerce.platform.user.common.response.ApiSuccessResponse;
import com.ecommerce.platform.user.dto.UserDto;
import com.ecommerce.platform.user.service.UserService;
import com.ecommerce.platform.user.util.ApiResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    @Operation(summary = "create a new user")
    public ResponseEntity<ApiSuccessResponse<UserDto>> createUser(@Valid @RequestBody UserDto userDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseUtil.success(userService.createUser(userDto),"User successfully created",201));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID")
    public ResponseEntity<ApiSuccessResponse<UserDto>> getUsersById(@PathVariable Long id){

//        return ResponseEntity.ok(userService.getUserById(id));
        return ResponseEntity.ok(ApiResponseUtil.success(userService.getUserById(id),"successfully fetched User Data",200));

    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<ApiSuccessResponse<List<UserDto>>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
        return ResponseEntity.ok(ApiResponseUtil.success(userService.getAllUsers(),"successfully fetched All User Data",200));

    }

//    @PutMapping("/{id}")
    @PatchMapping("/{id}")
    @Operation(summary = "Update user by ID")
    public ResponseEntity<ApiSuccessResponse<UserDto>> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
//        return ResponseEntity.ok(userService.updateUser(id, userDto));

        return ResponseEntity.ok(ApiResponseUtil.success(userService.updateUser(id,userDto),"Sucessfully Updated User Data",200));
    }

//        @PutMapping("/{id}")
        @PatchMapping("/patch2/{id}")
    @Operation(summary = "Partially update user by ID")
    public ResponseEntity<UserDto> partialUpdateUser(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(userService.partialUpdateUser(id, updates));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
