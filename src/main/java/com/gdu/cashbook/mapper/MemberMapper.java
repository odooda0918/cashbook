package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper // @Mapper +@Component


public interface MemberMapper {
	public Member selectMemberOne(LoginMember loginMember);
	
	public String selectMemberId(String MemberIdCheck);
	
	public void insertMember(Member member);
	
	public LoginMember selectLoginMember(LoginMember loginMember);
}
