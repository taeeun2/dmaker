package com.fastcampus.programming.dmaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeveloperSkillType {
    BACK_END("�鿣�� ������"),
    FRONT_END("����Ʈ���� ������"),
    FULL_STACK("Ǯ���� ������");

    private final String description;
}
