package com.fastcampus.programming.dmaker.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum StatusCode {
    EMPLOYED("���"),
    RETIRED("����");

    private final String description;
}
