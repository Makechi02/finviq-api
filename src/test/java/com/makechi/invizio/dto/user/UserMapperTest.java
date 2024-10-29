package com.makechi.invizio.dto.user;

import com.makechi.invizio.collections.Role;
import com.makechi.invizio.collections.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
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
        ModelUserDto modelUserDto = userMapper.toModelUserDto(user);

        assertNotNull(modelUserDto);
        assertEquals(modelUserDto.id(), user.getId());
        assertEquals(modelUserDto.name(), user.getName());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenUserIsNullToModelUserDto() {
        var exception = assertThrows(NullPointerException.class, () -> userMapper.toModelUserDto(null));
        assertEquals("User should not be null", exception.getMessage());
    }

}