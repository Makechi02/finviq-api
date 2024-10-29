package com.makechi.invizio.controller.user.auth;

import com.makechi.invizio.collections.Role;
import jakarta.validation.constraints.Email;

public record UserRegisterRequest(String name, @Email String email, String password, Role role) {
}