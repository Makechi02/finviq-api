package com.makbe.ims.controller.user.auth;

import com.makbe.ims.collections.Role;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRegisterRequest {
    private String name;
    @Email private String email;
    private String password;
    private Role role;
}