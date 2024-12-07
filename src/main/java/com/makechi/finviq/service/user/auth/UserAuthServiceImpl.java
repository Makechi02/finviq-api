package com.makechi.finviq.service.user.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makechi.finviq.collections.user.User;
import com.makechi.finviq.controller.user.UpdatePasswordRequest;
import com.makechi.finviq.controller.user.auth.UserAuthResponse;
import com.makechi.finviq.controller.user.auth.UserLoginRequest;
import com.makechi.finviq.controller.user.auth.UserRegisterRequest;
import com.makechi.finviq.dto.user.UserMapper;
import com.makechi.finviq.exception.DuplicateResourceException;
import com.makechi.finviq.exception.RequestValidationException;
import com.makechi.finviq.exception.ResourceNotFoundException;
import com.makechi.finviq.repository.UserRepository;
import com.makechi.finviq.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    public UserAuthResponse register(UserRegisterRequest request, final HttpServletRequest servletRequest) {
        boolean existsByEmail = userRepository.existsByEmail(request.email());
        if (existsByEmail) throw new DuplicateResourceException("email already taken");

        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();

        user = userRepository.save(user);
        log.info("User saved: {}", user);

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return UserAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message("Registration Success!")
                .user(userMapper.apply(user))
                .build();
    }


    @Override
    public UserAuthResponse login(UserLoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return UserAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message("Login success")
                .user(userMapper.apply(user))
                .build();
    }

    @Override
    public UserAuthResponse updatePassword(String id, UpdatePasswordRequest request) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        boolean changes = false;

        if (
                (request.currentPassword() != null && !request.currentPassword().isBlank()) &&
                (request.newPassword() != null && !request.newPassword().isBlank())
        ) {
            if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
                throw new RequestValidationException("Incorrect password");
            }

            user.setPassword(passwordEncoder.encode(request.newPassword()));
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes");
        }

        user = userRepository.save(user);

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return UserAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message("Update password success")
                .user(userMapper.apply(user))
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String refreshToken = authHeader.substring(7);
        String subject = jwtService.getSubject(refreshToken);

        if (subject != null) {
            var userDetails = userRepository.findByEmail(subject)
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);
                var authResponse = UserAuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
