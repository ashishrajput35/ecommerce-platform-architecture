package com.ecommerce.platform.user.mapper;


import com.ecommerce.platform.user.dto.UserDto;
import com.ecommerce.platform.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring") // So Spring can inject it as a bean
public interface UserMapper{

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "id", target = "id")
    UserDto toDto(User user);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "id", target = "id")
    User toEntity(UserDto userDto);

    List<UserDto> toDtoList(List<User> users);

    List<User> toEntityList(List<UserDto> userDtos);
}
