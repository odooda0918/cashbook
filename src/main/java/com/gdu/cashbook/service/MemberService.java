package com.gdu.cashbook.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberId;

@Service
@Transactional  //이클래스안에서 하나라도 예외가 생길경우 취소시킨다.
public class MemberService {
	@Autowired 	private MemberMapper memberMapper;
	@Autowired  private MemberidMapper memberidMapper;
	
	public int getMemberPw(Member member) {
		//비밀번호 추가하는거
		UUID uuid = UUID.randomUUID();
		String memberPw = uuid.toString().substring(0, 8);
		member.setMemberPw(memberPw);
		int row = memberMapper.updateMemberPw(member);
		if(row ==1) {
			System.out.println(memberPw+"<<--update memberPw");
			// 메일로 비번바뀐거 랜덤 비밀번호 전송
			// 메일객체 new JavaMailSender
		}
		return row;
	}
	
	
	public String getMemberIdByMember(Member member) { 
		return memberMapper.selectMemberIdByMember(member);
	}
	
	public void removeMember(LoginMember loginMember) {
		MemberId memberid = new MemberId();
		memberid.setMemberId(loginMember.getMemberId());
		memberidMapper.insertMemberid(memberid);
		
		
		memberMapper.deleteMember(loginMember);
	}
	
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	
	public String memberIdCheck(String memberIdCheck) {
		return memberMapper.selectMemberId(memberIdCheck); //아이디가 리턴되거나 , 결과물이없을시  null이 리턴된다.
	}
	
	public void addMember(Member member) { // 회원가입을 위한  insert 서비스
		memberMapper.insertMember(member);
	}
	
	public int deleteMember(LoginMember loginMember) {
		return memberMapper.deleteMember(loginMember);
	}
	
	public LoginMember login(LoginMember loginMember) {  //로그인을 하기위한  select 서비스
		return memberMapper.selectLoginMember(loginMember);
		
	}
}
