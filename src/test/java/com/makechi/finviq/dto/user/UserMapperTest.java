package com.makechi.finviq.dto.user;

import com.makechi.finviq.collections.Role;
import com.makechi.finviq.collections.User;
import com.makechi.finviq.exception.ResourceNotFoundException;
import com.makechi.finviq.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserMapperTest {

    private final User user = User.builder()
            .id("123456789")
            .name("Makechi Eric")
            .email("makbe@love.com")
            .password("mkuu")
            .role(Role.ADMIN)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    private UserMapper userMapper;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = new UserMapper(userRepository);
    }

    @Test
    public void shouldMapUserToUserDTO() {
        UserDto userDto = userMapper.apply(user);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getRole(), user.getRole());
        assertEquals(userDto.getCreatedAt(), user.getCreatedAt());
        assertEquals(userDto.getUpdatedAt(), user.getUpdatedAt());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenUserIsNull() {
        var exception = assertThrows(NullPointerException.class, () -> userMapper.apply(null));
        assertEquals("User should not be null", exception.getMessage());
    }

    @Test
    public void shouldMapUserToModelUserDTO() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        ModelUserDto modelUserDto = userMapper.toModelUserDto(user.getId());

        assertNotNull(modelUserDto);
        assertEquals(modelUserDto.id(), user.getId());
        assertEquals(modelUserDto.name(), user.getName());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenUserIdIsNotFoundToModelUserDto() {
        when(userRepository.findById("123456789")).thenReturn(Optional.empty());
        var exception = assertThrows(ResourceNotFoundException.class, () -> userMapper.toModelUserDto("123456789"));
        assertEquals("User with id 123456789 not found", exception.getMessage());
    }

}