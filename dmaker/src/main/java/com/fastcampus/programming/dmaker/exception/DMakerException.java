package com.fastcampus.programming.dmaker.exception;

import lombok.Getter;

@Getter
public class DMakerException extends RuntimeException{
        private DMakerErrorCode dMakerErrorCode;
        private String detailMessage;

        //기본 에러 메시지
        public DMakerException(DMakerErrorCode errorCode){
                super(errorCode.getMessage());//RuntimeException에 담아줄 데이터를 넣어줌
                this.dMakerErrorCode = errorCode;
                this.detailMessage = errorCode.getMessage();
        }

        //디테일 메시지
        public DMakerException(DMakerErrorCode errorCode, String detailMessage){
                super(detailMessage);//RuntimeException에 담아줄 데이터를 넣어줌
                this.dMakerErrorCode = errorCode;
                this.detailMessage = detailMessage;
        }
}
