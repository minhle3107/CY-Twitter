package com.global.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "UNCATEGORIZED ERROR"),
    INVALID_KEY(1001, "INVALID MESSAGE KEY"),

    USER_USERNAME_EXISTED(2002, "USER USERNAME ALREADY EXISTS"),
    USER_NOT_FOUND(2003, "USER NOT FOUND"),
    USER_CANT_CREATE_USER(2004, "USER CANT CREATE USER"),

    ACCOUNT_EMAIL_EXISTED(2003, "ACCOUNT EMAIL EXISTS"),
    ACCOUNT_NOT_FOUND(2004, "ACCOUNT NOT FOUND"),


    ;


    private int code;
    private String message;
}
