package com.makbe.ims.service.user.auth;

import com.makbe.ims.controller.user.UpdatePasswordRequest;
import com.makbe.ims.controller.user.auth.UserAuthResponse;
import com.makbe.ims.controller.user.auth.UserLoginRequest;
import com.makbe.ims.controller.user.auth.UserRegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface UserAuthService {
    UserAuthResponse register(UserRegisterRequest request, HttpServletRequest servletRequest);

    UserAuthResponse login(UserLoginRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    UserAuthResponse updatePassword(String id, UpdatePasswordRequest request);
}
