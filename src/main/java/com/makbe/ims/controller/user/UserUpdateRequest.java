package com.makbe.ims.controller.user;

import com.makbe.ims.collections.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateRequest {
    private String name;
    private String email;
    private Role role;
}
