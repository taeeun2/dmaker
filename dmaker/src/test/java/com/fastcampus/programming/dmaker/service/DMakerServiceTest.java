package com.fastcampus.programming.dmaker.service;

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDetailDto;
import com.fastcampus.programming.dmaker.entity.Developer;
import com.fastcampus.programming.dmaker.exception.DMakerErrorCode;
import com.fastcampus.programming.dmaker.exception.DMakerException;
import com.fastcampus.programming.dmaker.repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.repository.RetiredDeveloperRepository;
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
import static com.fastcampus.programming.dmaker.exception.DMakerErrorCode.*;
import static com.fastcampus.programming.dmaker.type.DeveloperLevel.JUNIOR;
import static com.fastcampus.programming.dmaker.type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {
    @Mock
    private DeveloperRepository developerRepository;
    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

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


    private final CreateDeveloper.Request defaultCreateRequest = CreateDeveloper.Request.builder()
            .developerLevel(DeveloperLevel.JUNIOR)
            .developerSkillType(DeveloperSkillType.FRONT_END)
            .experienceYears(3)
            .name("홍길동")
            .age(24)
            .memberId("Hong123")
            .build();

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


        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        //when
        CreateDeveloper.Response developer = dMakerService.createDeveloper(defaultCreateRequest);

        //then
        //developerRepository.save(developer) 동작 캡처 -> 성공적으로 save되는지 확인
        verify(developerRepository, times(1))
                .save(captor.capture());
        Developer saveDeveloper = captor.getValue();


        assertEquals(JUNIOR, saveDeveloper.getDeveloperLevel());
        assertEquals(FRONT_END, saveDeveloper.getDeveloperSkillType());
        assertEquals(3, saveDeveloper.getExperienceYears());


    }

    @Test
    void createDeveloperTest_failed_with_duplicated() {

        //given

        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));


        //when
        //then
        DMakerException dMakerException = assertThrows(DMakerException.class,
                () -> dMakerService.createDeveloper(defaultCreateRequest));


        assertEquals(DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());


    }

}