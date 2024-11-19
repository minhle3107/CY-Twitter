package com.global.project.services;

import com.global.project.entity.ViaCode;

public interface IAuthenticationService {
    int generateCode();

    int sendCodeToEmail(String to, String subject, String content);

    ViaCode insertCode(int code, String email);

    String handleSendCodeToMail(String email);
}
