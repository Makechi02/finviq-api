package com.makechi.invizio.service.user.auth;

import com.makechi.invizio.controller.user.UpdatePasswordRequest;
import com.makechi.invizio.controller.user.auth.UserAuthResponse;
import com.makechi.invizio.controller.user.auth.UserLoginRequest;
import com.makechi.invizio.controller.user.auth.UserRegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface UserAuthService {
    UserAuthResponse register(UserRegisterRequest request, HttpServletRequest servletRequest);

    UserAuthResponse login(UserLoginRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    UserAuthResponse updatePassword(String id, UpdatePasswordRequest request);
}
