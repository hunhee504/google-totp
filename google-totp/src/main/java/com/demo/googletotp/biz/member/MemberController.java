package com.demo.googletotp.biz.member;

import com.demo.googletotp.biz.googleauth.GoogleAuthService;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/member")
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private GoogleAuthService googleAuthService;

    @GetMapping("/login-main")
    public ModelAndView loginMain() {
        return new ModelAndView("member/login-main");
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> memberLogin(Member member) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean loginFlag = false;
        Member info = memberService.getMbrInfoById(member);
        System.out.println(info);

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

    @PostMapping("/join")
    @ResponseBody
    public Map<String, Object> mbrJoin(Member member) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member info = memberService.getMbrInfoById(member);

        if(Objects.isNull(info)) {
            // 회원가입
            int cnt = memberService.insertMbrInfo(member);
            if(cnt > 0) {
                map.put("resultCode", "success");
            } else {
                map.put("resultCode", "fail");
            }
        } else {
            map.put("resultCode", "duplication");
        }

        return map;
    }
}
