package com.demo.googletotp.biz.member;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface MemberMapper {

    Member getMbrInfoById(Member member);

    int insertMbrInfo(Member member);

}
