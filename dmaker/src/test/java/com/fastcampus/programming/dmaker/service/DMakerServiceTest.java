package com.fastcampus.programming.dmaker.service;

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDetailDto;
import com.fastcampus.programming.dmaker.entity.Developer;
import com.fastcampus.programming.dmaker.exception.DMakerException;
import com.fastcampus.programming.dmaker.repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fastcampus.programming.dmaker.code.StatusCode.EMPLOYED;
import static com.fastcampus.programming.dmaker.constant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.fastcampus.programming.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;
import static com.fastcampus.programming.dmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.fastcampus.programming.dmaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEAR_NOT_MATCHED;
import static com.fastcampus.programming.dmaker.type.DeveloperLevel.JUNIOR;
import static com.fastcampus.programming.dmaker.type.DeveloperLevel.SENIOR;
import static com.fastcampus.programming.dmaker.type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {
    @Mock
    private DeveloperRepository developerRepository;

    @InjectMocks
    private DMakerService dMakerService;

    private final Developer defaultDeveloper = Developer.builder()
            .developerLevel(JUNIOR)
                   .developerSkillType(FRONT_END)
                       .experienceYears(3)
                       .statusCode(EMPLOYED)
                       .name("홍길동")
                       .age(24)
                       .memberId("Hong123")
                       .build();


    private CreateDeveloper.Request getCreateRequest(
            DeveloperLevel developerLevel,
            DeveloperSkillType developerSkillType,
            Integer experienceYears
    ) {
        return CreateDeveloper.Request.builder()
                .developerLevel(developerLevel)
                .developerSkillType(developerSkillType)
                .experienceYears(experienceYears)
                .name("홍길동")
                .age(24)
                .memberId("Hong123")
                .build();
    }


    @Test
    public void test(){
        //given
       given(developerRepository.findByMemberId(anyString()))
               .willReturn(Optional.of(defaultDeveloper));

        //when
        DeveloperDetailDto developerDetail = dMakerService.getDeveloperDetail("memberId");

        //then
        assertEquals(JUNIOR,developerDetail.getDeveloperLevel());
        assertEquals(FRONT_END,developerDetail.getDeveloperSkillType());
        assertEquals(24,developerDetail.getAge());

    }

    @Test
    void createDeveloperTest_success(){
        //given


        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());

        given(developerRepository.save(any()))
                .willReturn(defaultDeveloper);

        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        //when
        dMakerService.createDeveloper(getCreateRequest(SENIOR,FRONT_END,MIN_SENIOR_EXPERIENCE_YEARS));

        //then
        //developerRepository.save(developer) 동작 캡처 -> 성공적으로 save되는지 확인
        verify(developerRepository, times(1))
                .save(captor.capture());
        Developer saveDeveloper = captor.getValue();


        assertEquals(SENIOR, saveDeveloper.getDeveloperLevel());
        assertEquals(FRONT_END, saveDeveloper.getDeveloperSkillType());
        assertEquals(MIN_SENIOR_EXPERIENCE_YEARS, saveDeveloper.getExperienceYears());


    }

    @Test
    void createDeveloperTest_failed_with_duplicated() {

        //given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));

        //when
        //then
        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(getCreateRequest(SENIOR,FRONT_END,MIN_SENIOR_EXPERIENCE_YEARS)));


        assertEquals(DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());


    }

    @Test
    void createDeveloperTest_fail_with_unmatched_level(){
        //given
        //when
        //then
        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(
                        getCreateRequest(SENIOR,FRONT_END,MIN_SENIOR_EXPERIENCE_YEARS-1)));

        assertEquals(LEVEL_EXPERIENCE_YEAR_NOT_MATCHED,
                dMakerException.getDMakerErrorCode());

        dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(
                        getCreateRequest(JUNIOR,FRONT_END,MAX_JUNIOR_EXPERIENCE_YEARS+1)));

        assertEquals(LEVEL_EXPERIENCE_YEAR_NOT_MATCHED,
                dMakerException.getDMakerErrorCode());



    }

}