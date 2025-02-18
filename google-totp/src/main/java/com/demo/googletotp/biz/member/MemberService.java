package com.demo.googletotp.biz.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public Member getMbrInfoById(Member member) {
        return memberMapper.getMbrInfoById(member);
    }

    public int insertMbrInfo(Member member) {
        return memberMapper.insertMbrInfo(member);
    }
}
