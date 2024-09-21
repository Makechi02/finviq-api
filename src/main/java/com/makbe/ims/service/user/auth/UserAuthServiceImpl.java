package com.makbe.ims.service.user.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makbe.ims.collections.User;
import com.makbe.ims.controller.user.auth.UserAuthResponse;
import com.makbe.ims.controller.user.auth.UserLoginRequest;
import com.makbe.ims.controller.user.auth.UserRegisterRequest;
import com.makbe.ims.dto.user.UserMapper;
import com.makbe.ims.exception.DuplicateResourceException;
import com.makbe.ims.repository.UserRepository;
import com.makbe.ims.service.JwtService;
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
        boolean existsByEmail = userRepository.existsByEmail(request.getEmail());
        if (existsByEmail) throw new DuplicateResourceException("email already taken");

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
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
