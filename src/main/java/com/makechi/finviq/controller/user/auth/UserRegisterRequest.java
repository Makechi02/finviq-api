package com.makechi.finviq.controller.user.auth;

import com.makechi.finviq.collections.Role;
import jakarta.validation.constraints.Email;

public record UserRegisterRequest(String name, @Email String email, String password, Role role) {
}