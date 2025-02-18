package com.demo.googletotp.biz.googleauth;

import com.demo.googletotp.biz.member.MemberService;
import com.demo.googletotp.biz.member.Member;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/google-auth")
@Controller
public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/rgst-page")
    public ModelAndView googleAuthRgstPage() {
        return new ModelAndView("googleauth/google-otp-rgst");
    }

    @GetMapping("/root-chk")
    @ResponseBody
    public Map<String, Object> rootChk(Member member) {
        Map<String, Object> map = new HashMap<String, Object>();
        Boolean rootFlag = false;

       Member info = memberService.getMbrInfoById(member);

        if(!Objects.isNull(info) && StringUtils.equals("root", member.getLoginId())) {
            rootFlag = true;
        }
//        String rootId = "root";
//        String rootPw = "1234";

//        if(StringUtils.equals(rootId, user.getLoginId()) && StringUtils.equals(rootPw, user.getLoginPw())) {
//            rootFlag = true;
//        }

        map.put("rootFlag", rootFlag);

        return map;
    }

    @GetMapping("/google-auth-rgst-info")
    @ResponseBody
    public Map<String, Object> getGoogleRgstInfo(Member member) {
        Map<String, Object> map = new HashMap<String, Object>();

        String secretKey = googleAuthService.getGoogleSecretKey();
        String googleAuthUrl = "";
        byte[] QRCodeImg = null;

        try {
            googleAuthUrl = googleAuthService.getGoogleAuthBarcode(secretKey, "3top", member.getLoginId());
            QRCodeImg = googleAuthService.getQRCodeImg(googleAuthUrl, 400, 400);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        map.put("secretKey", secretKey);
        map.put("QRImg", QRCodeImg);
        return map;
    }

    @GetMapping("/google-otp-chk")
    @ResponseBody
    public Map<String, Object> googleOtpChk(String otpCode, String secretKey) {
        Map<String, Object> map = new HashMap<String, Object>();

        String otp = googleAuthService.getTOTPCode(secretKey);
        map.put("serverOTP", otp);

        boolean chkFlag = StringUtils.equals(otp, otpCode) ? true : false;
        map.put("chkFlag", chkFlag);

        return map;
    }
}
