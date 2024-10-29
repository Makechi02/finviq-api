package com.makechi.invizio.service.user;

import com.makechi.invizio.controller.user.UserUpdateRequest;
import com.makechi.invizio.dto.user.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDto> getAllUsers();

    List<UserDto> getAllUsers(String query);

    UserDto getUserById(String id);

    UserDto getUserByEmail(String email);

    UserDto updateUser(String id, UserUpdateRequest request);

    void deleteUser(String id);
}
