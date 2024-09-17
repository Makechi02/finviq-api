package com.makbe.ims.service.user;

import com.makbe.ims.collections.User;
import com.makbe.ims.controller.user.UserUpdateRequest;
import com.makbe.ims.dto.user.UserDto;
import com.makbe.ims.dto.user.UserDtoMapper;
import com.makbe.ims.exception.DuplicateResourceException;
import com.makbe.ims.exception.RequestValidationException;
import com.makbe.ims.exception.ResourceNotFoundException;
import com.makbe.ims.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElse(null);
        if (user == null) throw new UsernameNotFoundException("user not found");
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userDtoMapper).toList();
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return userDtoMapper.apply(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
        return userDtoMapper.apply(user);
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
        return userDtoMapper.apply(user);
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
