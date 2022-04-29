package com.fastcampus.programming.dmaker.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeveloperLevel {

    NEW("���� ������"),
    JUNIOR("�ִϾ� ������"),
    SENIOR("�ôϾ� ������");

    private final String description;
}
