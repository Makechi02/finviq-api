package com.makbe.ims.controller.user;

import com.makbe.ims.collections.Role;

public record UserUpdateRequest(String name, String email, Role role) {
}
