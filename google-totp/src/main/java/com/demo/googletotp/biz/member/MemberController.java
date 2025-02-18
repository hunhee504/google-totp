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
        return memberService.memberLogin(member);
    }

    @PostMapping("/join")
    @ResponseBody
    public Map<String, Object> memberJoin(Member member) {
        return memberService.memberJoin(member);
    }
}
