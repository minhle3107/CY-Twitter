package com.global.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "UNCATEGORIZED ERROR"),
    INVALID_KEY(1001, "INVALID MESSAGE KEY")


    ;




    private int code;
    private String message;
}
