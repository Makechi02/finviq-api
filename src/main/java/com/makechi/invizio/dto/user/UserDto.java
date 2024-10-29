package com.makechi.invizio.dto.user;

import com.makechi.invizio.collections.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {
    private String id;
    private String name;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
