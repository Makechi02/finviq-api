package com.makbe.ims.controller.user;

public record UpdatePasswordRequest(String currentPassword, String newPassword) {

}
