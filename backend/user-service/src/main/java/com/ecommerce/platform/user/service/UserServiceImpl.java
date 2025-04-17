package com.ecommerce.platform.user.service;

import com.ecommerce.platform.user.common.exception.custom.ConflictException;
import com.ecommerce.platform.user.common.exception.custom.EmailAlreadyExistsException;
import com.ecommerce.platform.user.common.exception.custom.ResourceNotFoundException;
import com.ecommerce.platform.user.dto.UserDto;
import com.ecommerce.platform.user.entity.User;
import com.ecommerce.platform.user.enums.Role;
import com.ecommerce.platform.user.mapper.UserMapper;
import com.ecommerce.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {


//        System.out.println("email - "+userDto.getEmail());
//        System.out.println("user mapper -"+userMapper.toString());
//        System.out.println("name- "+userDto.getFullName());
//        System.out.println("phone -"+userDto.getPhone());
//        System.out.println("role- "+userDto.getRole());
//        System.out.println("id -"+userDto.getId());

//        System.out.println("after mapping email - "+user.getEmail());
//        System.out.println("user mapper -"+userMapper);
//        System.out.println("name- "+user.getFullName());
//        System.out.println("phone -"+user.getPhone());
//        System.out.println("role- "+user.getRole());
//        System.out.println("id -"+user.getId());
        try {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new EmailAlreadyExistsException("Email already exists - "+userDto.getEmail());
            }
            User user = userMapper.toEntity(userDto);
            User saved = userRepository.save(user);
            return userMapper.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Duplicate Data");
        }

    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }


    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setRole(userDto.getRole());

        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    @Override
    public UserDto partialUpdateUser(Long id, Map<String, Object> updates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        updates.forEach((field, value) -> {
            switch (field) {
                case "fullName" -> user.setFullName((String) value);
                case "email" -> user.setEmail((String) value);
                case "phone" -> user.setPhone((String) value);
                case "role" -> user.setRole(value.toString());
                default -> throw new IllegalArgumentException("Unsupported field: " + field);
            }
        });

        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    @Override
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
