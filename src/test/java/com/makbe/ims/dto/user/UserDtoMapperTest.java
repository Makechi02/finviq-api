package com.makbe.ims.dto.user;

import com.makbe.ims.collections.Role;
import com.makbe.ims.collections.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserDtoMapperTest {

    private final User user = User.builder()
            .id("123456789")
            .name("Makechi Eric")
            .email("makbe@love.com")
            .password("mkuu")
            .role(Role.ADMIN)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    @InjectMocks
    private UserDtoMapper userDtoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldMapUserToUserDTO() {
        UserDto userDto = userDtoMapper.apply(user);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getRole(), user.getRole());
        assertEquals(userDto.getCreatedAt(), user.getCreatedAt());
        assertEquals(userDto.getUpdatedAt(), user.getUpdatedAt());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenUserIsNull() {
        var exception = assertThrows(NullPointerException.class, () -> userDtoMapper.apply(null));
        assertEquals("User should not be null", exception.getMessage());
    }

}