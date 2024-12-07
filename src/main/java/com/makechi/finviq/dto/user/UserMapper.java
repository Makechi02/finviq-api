package com.makechi.finviq.dto.user;

import com.makechi.finviq.collections.user.User;
import com.makechi.finviq.exception.ResourceNotFoundException;
import com.makechi.finviq.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserMapper implements Function<User, UserDto> {

    private final UserRepository userRepository;

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

    public ModelUserDto toModelUserDto(@NonNull String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        return new ModelUserDto(user.getId(), user.getName());
    }
}
