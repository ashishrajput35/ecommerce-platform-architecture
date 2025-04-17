package com.ecommerce.platform.user.service;

import com.ecommerce.platform.user.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserDto userDto);
    UserDto partialUpdateUser(Long id, Map<String, Object> updates);
    void deleteUser(Long id);
}
