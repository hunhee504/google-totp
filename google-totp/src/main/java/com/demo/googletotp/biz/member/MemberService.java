package com.demo.googletotp.biz.member;

import com.demo.googletotp.biz.googleauth.GoogleAuthService;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private GoogleAuthService googleAuthService;

    public Member getMbrInfoById(Member member) {
        return memberMapper.getMbrInfoById(member);
    }

    public int insertMbrInfo(Member member) {
        return memberMapper.insertMbrInfo(member);
    }

    public Map<String, Object> memberLogin(Member member) {
        Map<String, Object> map = new HashMap<String, Object>();

        boolean loginFlag = false;
        Member info = this.getMbrInfoById(member);

        if(Objects.isNull(info)) {
            map.put("loginFlag", loginFlag);
            map.put("resultMsg", "ID,PW 일치하지않습니다.");
            return map;
        }

        String otpCode = googleAuthService.getTOTPCode(info.getSecretKey());
        String resultMsg = "";

        if(StringUtils.equals(member.getOtpCode(), otpCode)) {
            loginFlag = true;
            resultMsg = "로그인성공";
        } else {
            resultMsg = "OTP 코드 다름";
        }

        map.put("loginFlag", loginFlag);
        map.put("resultMsg", resultMsg);
        return map;
    }

    public Map<String, Object>memberJoin(Member member) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member info = this.getMbrInfoById(member);
        String resultCode = "";
        if(Objects.isNull(info)) {
            // 회원가입
            int cnt = memberMapper.insertMbrInfo(member);
            if(cnt > 0) {
                resultCode = "success";
            } else {
                resultCode = "fail";
            }
        } else {
            resultCode = "duplication";
        }

        map.put("resultCode", resultCode);
        return map;
    }
}
