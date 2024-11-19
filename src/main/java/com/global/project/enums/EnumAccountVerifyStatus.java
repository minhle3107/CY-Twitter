package com.global.project.enums;

import lombok.Getter;

@Getter
public enum EnumAccountVerifyStatus {
    Verified(0),
    Banned(1);

    private final int value;

    EnumAccountVerifyStatus(int value) {
        this.value = value;
    }
}
