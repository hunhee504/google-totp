package com.demo.googletotp.biz.member;

import lombok.Data;

@Data
public class Member {

    private int mbrIdx;

    private String loginId;

    private String loginPw;

    private String secretKey;

    private String otpCode;
}
