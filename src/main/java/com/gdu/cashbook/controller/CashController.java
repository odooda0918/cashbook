package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.DayAndPrice;

@Controller
public class CashController {
	@Autowired private CashService cashService;
	
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(HttpSession session,
				Model model,
				@RequestParam(value="day", required = false) @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate day){
	
		if(session.getAttribute("loginMember")==null) {
				return "redirect:/";
		}
		
		System.out.println("day"+day);
		Calendar cDay = Calendar.getInstance(); 
		
		if(day==null) {
			day = LocalDate.now();
		}else {
			cDay.set(day.getYear(), day.getMonthValue()-1, day.getDayOfMonth());
		}
		
		// 일별 수입 지출 총액
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		int year = cDay.get(Calendar.YEAR);
		int month = cDay.get(Calendar.MONTH)+1;
		List<DayAndPrice> dayAndPriceList = cashService.getDayAndPriceList(memberId, year, month);
		System.out.println(dayAndPriceList);
		
		model.addAttribute("dayAndPriceList",dayAndPriceList);
		model.addAttribute("day",day);
		model.addAttribute("month", cDay.get(Calendar.MONTH)+1); //월
		model.addAttribute("lastDay",cDay.getActualMaximum(Calendar.DATE)); //마지막일
		
		Calendar firstDay = Calendar.getInstance();
		firstDay.set(Calendar.DATE,1); //일자만 1일로 변경하는것.
		//firstDay.get(calendar.DAY_OF_WEEK); 0 일 1 월 2화 3수 4목 5금 6토
		model.addAttribute("firstDayOfWeek", firstDay.get(Calendar.DAY_OF_WEEK));
		return "getCashListByMonth";
	}
	
	
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(HttpSession session, 
				Model model, 
				@RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if(day == null) {
			day = LocalDate.now();
		}
		System.out.println(day + " <--day");
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String strToday = "";
		strToday = dateTime.format(day); // 오늘날짜 출력.
		System.out.println(strToday+"<<--srtToday");
		
		
		String loginMemberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		Cash cash = new Cash(); 
		cash.setMemberId(loginMemberId);
		cash.setCashDate(day.toString());

		Map<String, Object> map = cashService.getCashListByDate(cash);
		model.addAttribute("cashList", map.get("cashList"));
		model.addAttribute("cashFindSum", map.get("cashFindSum"));
		model.addAttribute("day", day);

		return "getCashListByDate";
	}
}
