package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.tomcat.jni.Mmap;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper // @Mapper +@Component


public interface MemberMapper {
	
	public String selectMemberPic(String memberId);
	
	public String selectMemberIdByMember(Member member);
	
	public int updateMemberPw(Member member);
	
	public Member selectMemberOne(LoginMember loginMember);
	public String selectMemberId(String MemberIdCheck);
	public int insertMember(Member member);
	public LoginMember selectLoginMember(LoginMember loginMember);
	public void deleteMember(LoginMember loginMember);
	
}
