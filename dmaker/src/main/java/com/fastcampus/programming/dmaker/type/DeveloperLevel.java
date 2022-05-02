package com.fastcampus.programming.dmaker.type;


import com.fastcampus.programming.dmaker.exception.DMakerException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

import static com.fastcampus.programming.dmaker.constant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.fastcampus.programming.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;
import static com.fastcampus.programming.dmaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEAR_NOT_MATCHED;

@AllArgsConstructor
@Getter
public enum DeveloperLevel {

    NEW("���� ������",years-> years == 0),
    JUNIOR("�ִϾ� ������",years -> years <= MAX_JUNIOR_EXPERIENCE_YEARS),
    SENIOR("�ôϾ� ������",years -> years >= MIN_SENIOR_EXPERIENCE_YEARS);

    private final String description;
    private final Function<Integer,Boolean> validateFunction;

    public void validateExperienceYears(Integer years){
        if(!validateFunction.apply(years))
            throw new DMakerException(LEVEL_EXPERIENCE_YEAR_NOT_MATCHED);
    }

}
