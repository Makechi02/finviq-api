package com.makechi.finviq.controller.user;

import com.makechi.finviq.collections.Role;

public record UserUpdateRequest(String name, String email, Role role) {
}
