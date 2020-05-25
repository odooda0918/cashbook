package com.gdu.cashbook.service;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;
import com.gdu.cashbook.vo.MemberId;


@Service
@Transactional  //이클래스안에서 하나라도 예외가 생길경우 취소시킨다.
public class MemberService {
	@Autowired 	private MemberMapper memberMapper;
	@Autowired  private MemberidMapper memberidMapper;
	@Autowired	private JavaMailSender javaMailSender;
	@Value ("D:\\123\\cashbook\\cashbook\\src\\main\\resources\\static\\upload\\")
	public String path;
	
		//회원가입
	public void addMember(MemberForm memberForm) {
		
		MultipartFile mf = memberForm.getMemberPic();
		String originName = mf.getOriginalFilename();
		System.out.println(originName+"<--originName");
		
		String memberPic = null;
		
		if(originName.equals("")) {
			memberPic = "default.jpg";
		}else {
			int lastDot = originName.lastIndexOf(".");
			String extension = originName.substring(lastDot);
			System.out.println(extension+"<-extension");
			memberPic = memberForm.getMemberId()+extension;
			System.out.println(memberPic+"<--memberPic");
			
		}
		
		
		
		/* DB저장 */
		
		Member member = new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberDate(memberForm.getMemberDate());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberPic(memberPic);
		
		System.out.println(member+"<--memberservice.addmember.member");
		
		memberMapper.insertMember(member);

		//파일저장.
		
		File file = new File(path+memberPic);
		try {
			mf.transferTo(file);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		//return memberidMapper.insertMemberid(member);
		
	}
	
	// 비밀번호 찾기
	public int getMemberPw(Member member) {
		//비밀번호 추가하는거
		UUID uuid = UUID.randomUUID();
		String memberPw = uuid.toString().substring(0, 8);
		member.setMemberPw(memberPw);
		int row = memberMapper.updateMemberPw(member);
		if(row ==1) {
			System.out.println(memberPw+"<<--update memberPw");
			//메일로  무작위로 변경된 비밀번호를 전송
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage(); //받는사람
			simpleMailMessage.setTo(member.getMemberEmail()); //받는사람 
			simpleMailMessage.setFrom("운영자"); //보낸사람
			simpleMailMessage.setSubject("cashbook 비밀번호 찾기 메일"); //제목
			simpleMailMessage.setText("변경 비밀번호 :"+memberPw+"새 비밀번호로 변경하세요");
			javaMailSender.send(simpleMailMessage);//메일 
			
			// 메일객체 new JavaMailSender
			
		}
		return row;
	}
	
	//아이디 찾기
	public String getMemberIdByMember(Member member) { 
		return memberMapper.selectMemberIdByMember(member);
	}
	
	
	
	//삭제 
	
	public void removeMember(LoginMember loginMember) {
		// 멤버 이미지 파일 삭제.
		// 1_1 select member_pic from member 파일 이름 가져오기
		String memberPic = memberMapper.selectMemberPic(loginMember.getMemberId());
		
		
		// 파일삭제
		File file = new File(path+memberPic);
		if(file.exists()) {
			file.delete();
		}
		
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
	
	/*
	 * public void addMember(MemberForm memberForm) { // 회원가입을 위한 insert 서비스
	 * memberMapper.insertMember(memberForm); }
	 */
	
	
	public LoginMember login(LoginMember loginMember) {  //로그인을 하기위한  select 서비스
		return memberMapper.selectLoginMember(loginMember);
		
	}


	public void modifiyMember(MemberForm memberForm) {
		String originMemberpic = memberMapper.selectMemberPic(memberForm.getMemberId());
		MultipartFile mf = memberForm.getMemberPic();
		String originName = mf.getOriginalFilename();
		System.out.println(originName+"<-originalName");
		String memberPic = null;
		if(originName.equals("")) {
			memberPic = originMemberpic;
		}else{
			File file = new File(path+originMemberpic);
			//새 파일이 입력되면 기존파일 삭제.
			if(file.exists() &&! originMemberpic.equals("default.jpg")) {
				file.delete();
			}
			int lastDot = originName.indexOf(".");
			String extension = originName.substring(lastDot);
			System.out.println(extension+"<--extension");
			memberPic = memberForm.getMemberId();
			System.out.println(memberPic+"<--memberPic");
		}
		Member member = new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(member.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberDate(memberForm.getMemberDate());
		member.setMemberEmail(member.getMemberEmail());
		member.setMemberName(member.getMemberName());
		member.setMemberPhone(member.getMemberPhone());
		member.setMemberPic(memberPic);
		
		if(!originName.equals("")) {
			//file 저장
			File file = new File(path+member);
			try {
				mf.transferTo(file);
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}
}
