package com.makbe.ims.dto.user;

import com.makbe.ims.collections.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        if (user == null) throw new NullPointerException("User should not be null");

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public ModelUserDto toModelUserDto(User user) {
        if (user == null) throw new NullPointerException("User should not be null");

        return  ModelUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
