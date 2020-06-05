package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;

@Controller
public class MemberController {	
	@Autowired
	private MemberService memberService;
	
	
	//회원정보
	@GetMapping("/memberInfo")
	public String memberInfo(HttpSession session, Model model) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
			
		}
		Member member = memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));
		System.out.println(member+"<-member");
		model.addAttribute("member",member);
		return "memberInfo";
	}
	
	@PostMapping("/memberIdCheck") 
	public String memberIdCheck(Model model, HttpSession session, @RequestParam("memberIdCheck") String memberIdCheck) {
		//로그인 중일떄
				if(session.getAttribute("loginMember") !=null) {
					return "redirect:/";
				}
		String confirmMeberId = memberService.memberIdCheck(memberIdCheck);
		if(confirmMeberId == null) {
			//아이디를 사용할수 있습니다.
			System.out.println("아이디를 사용할수있습니다.");
			model.addAttribute("memberIdCheck", memberIdCheck);
		}else {
			//아이디를 사용할수 없습니다.
			System.out.println("아이디를 사용할수없어요.");
			model.addAttribute("msg", "사용중인 아이디입니다");
		}
		return "addmember";
	}
	
	@GetMapping("/login")
	public String login(HttpSession session) {
		//로그인 중일떄
		if(session.getAttribute("loginMember") !=null) {
			return "redirect:/";
		}
		//로그인이아닐떄
		return "login";
		
	}
	@PostMapping("/login")
	public String Login(Model model, LoginMember loginMember, HttpSession session) { //HttpSession session = request.getSession();
		//로그인 중일때
		if(session.getAttribute("loginMember") !=null) {
			return "redirect:/";
		}
		System.out.println(loginMember);
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println("returnLoginMember:"+returnLoginMember);
		if(returnLoginMember == null) { //로그인실패시
			model.addAttribute("msg", "아이디와 비밀번호를 확인바랍니다");
			return "login";
		}else { // 로그인 성공시
			session.setAttribute("loginMember", loginMember);
			return "redirect:/home"; ///index"; 같은거
		}
		
	}
	
	//아이디찾기 
	
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		if(session.getAttribute("loginMember")!=null) {
			return "redirect:/";
		}
		return "findMemberId";
	}
	
	@PostMapping("/findMemberId")
	public String findMemberId(HttpSession session, Model model, Member member) {
		if(session.getAttribute("loginMember")!=null) {
			return "redirect:/";
		}
		String memberIdPart = memberService.getMemberIdByMember(member);
		System.out.println(memberIdPart+"<--memberIdPart");
		if(memberIdPart == null) {
			model.addAttribute("msg","입력한 정보와 일치하지 않음.");
				return "findMemberId";
		}
		memberIdPart = "회원님의 아이디는" +memberIdPart +"입니다.";
		model.addAttribute("memberIdPart",memberIdPart);
		return "memberIdView";
	}
	
	//비번찾기
	
	@PostMapping("/findMemberPw")
	public String findMemberPw(HttpSession session, Model model, Member member ) {
		int row = memberService.getMemberPw(member);
		System.out.println(row+"<<-row");
		String msg = "아이디 메일 확인하세요";
		if(row == 1) {
			msg="비밀번호를 메일로 전송하였습니다.";
		}
		model.addAttribute("msg",msg);
		return "memberPwView";
	
	}
	
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
		if(session.getAttribute("loginMember")!= null) {
			return "redirect:/";
		}
		return "findMemberPw";
	}
	
	
	
	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		//로그인 아닐떄
		if(session.getAttribute("loginMember") ==null) {
			return "redirect:/";
		}
		session.invalidate();
		return "redirect:/"; //로그아웃 시 로그인창으로 가겠다.
	}
	
	//회원수정
	@GetMapping("/modifyMember")
	public String modifyMember(Model model, HttpSession session) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		Member member = memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));
		model.addAttribute("member",member);
		return "modifyMember";
	}
	
	
	@PostMapping("/modifyMember")
	public String modifyMember(RedirectAttributes rttr, MemberForm memberForm, HttpSession session) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		System.out.println(memberForm);
		//이미지 입력 안되어있을때
		MultipartFile mf = memberForm.getMemberPic();
		if(memberForm.getMemberPic() !=null && ! mf.getOriginalFilename().equals("")) {
			if(!memberForm.getMemberPic().getContentType().equals("image/jpeg")&& !memberForm.getMemberPic().getContentType().equals("image/gif") &&! memberForm.getMemberPic().getContentType().equals("image/gif")) {
				rttr.addFlashAttribute("msg1", "파일명을 확인해주세요");
			}
		}
		memberService.modifiyMember(memberForm);
		return "redirect:/memberInfo";
	}
	
	
	//회원 삭제
	
	@GetMapping("/removeMember")
	public String removeMember(HttpSession session) {
		if(session.getAttribute("loginMember") ==null) {
			return "redirect:/";
		}
			return "removeMember";
	}
	
	@PostMapping("/removeMember")
	public String removeMember(HttpSession session, @RequestParam("memberPw")String memberPw) {
		if(session.getAttribute("loginMember") ==null) {
			return "redirect:/";
		}
		LoginMember loginMember = (LoginMember)(session.getAttribute("loginMember"));
		loginMember.setMemberPw(memberPw);
		memberService.removeMember(loginMember);
			session.invalidate();
			return "redirect:/";
	}
	
	
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		//로그인 중일떄
		if(session.getAttribute("loginMember") !=null) {
			return "redirect:/";
		}
		return "addMember";
		
	}
	
	@PostMapping("/addMember")
	public String addMember(MemberForm memberForm, HttpSession session) {
		System.out.println(memberForm);
		//로그인 중일떄
		if(session.getAttribute("loginMember") !=null) {
			return "redirect:/";
		}
		System.out.println(memberForm+"<-memberForm");
		
		if(memberForm.getMemberPic()!=null) {
			if(memberForm.getMemberPic().getContentType().equals("image/jpg") && memberForm.getMemberPic().getContentType().equals("img/png")&& memberForm.getMemberPic().getContentType().equals("img/gif")) {
				return "redirect:/addMember"; //이미지 파일 png jpg 도 아니고 gif도 아닐 경우에는 리턴
			}
		}
		
		memberService.addMember(memberForm);
	//  service : memberForm -> member+ 폴더에 파일도 저장
		System.out.println(memberForm+"<-memberForm");
		return "redirect:/index";
	}
}
