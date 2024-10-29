package com.makechi.invizio.controller.user;

import com.makechi.invizio.collections.Role;

public record UserUpdateRequest(String name, String email, Role role) {
}
