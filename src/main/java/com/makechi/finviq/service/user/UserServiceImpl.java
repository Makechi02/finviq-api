package com.makechi.finviq.service.user;

import com.makechi.finviq.collections.User;
import com.makechi.finviq.controller.user.UserUpdateRequest;
import com.makechi.finviq.dto.user.UserDto;
import com.makechi.finviq.dto.user.UserMapper;
import com.makechi.finviq.exception.DuplicateResourceException;
import com.makechi.finviq.exception.RequestValidationException;
import com.makechi.finviq.exception.ResourceNotFoundException;
import com.makechi.finviq.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElse(null);
        if (user == null) throw new UsernameNotFoundException("user not found");
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper).toList();
    }

    @Override
    public List<UserDto> getAllUsers(String query) {
        List<User> users = userRepository.searchByKeyword(query);
        return users.stream().map(userMapper).toList();
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return userMapper.apply(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
        return userMapper.apply(user);
    }

    @Override
    public UserDto updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        boolean changes = false;

        if (request.name() != null && !request.name().isBlank() && !request.name().equals(user.getName())) {
            user.setName(request.name());
            changes = true;
        }

        if (request.email() != null && !request.email().isBlank() && !request.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.email())) {
                throw new DuplicateResourceException("User with email " + request.email() + " already exists");
            }

            user.setEmail(request.email());
            changes = true;
        }

        if (request.role() != null && !request.role().equals(user.getRole())) {
            user.setRole(request.role());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes");
        }

        user = userRepository.save(user);
        return userMapper.apply(user);
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
