package com.exhanger.vvasiuk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DirectoryType {
    HOME_DIRECTORY("C:\\HOME"),
    DEV_DIRECTORY("C:\\DEV"),
    TEST_DIRECTORY("C:\\TEST");

    String code;
}
