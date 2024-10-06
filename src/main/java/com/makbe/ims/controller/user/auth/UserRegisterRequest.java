package com.makbe.ims.controller.user.auth;

import com.makbe.ims.collections.Role;
import jakarta.validation.constraints.Email;

public record UserRegisterRequest(String name, @Email String email, String password, Role role) {
}