package com.fastcampus.programming.dmaker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DMakerErrorCode {
    NO_DEVELOPER("�ش�Ǵ� �����ڰ� �����ϴ�."),
    DUPLICATED_MEMBER_ID("MemberId�� �ߺ��Ǵ� �����ڰ� �ֽ��ϴ�."),
    LEVEL_EXPERIENCE_YEAR_NOT_MATCHED("������ ������ ������ ���� �ʽ��ϴ�."),

    INTERNAL_SERVER_ERROR("������ ������ �߻��߽��ϴ�."),
    INVALID_REQUEST("�߸��� ��û�Դϴ�.");

    private final String message;
}
