package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.mapper.CategoryMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;

@Service
@Transactional
public class CashService {
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired 
	private CashMapper cashMapper;
	
	
	
	public List<Category> getCategoryList(){
		
		List<Category> list = categoryMapper.selectCategoryList();
		for(Category c : list) {
			System.out.println(c.getCategoryName());
		}
		return categoryMapper.selectCategoryList();
		
	}
	
	
	//가게부 가져오기
	public Cash getCashOne(int cashNo) {
		return cashMapper.selectCashOne(cashNo);
	}
	
	
	//수정
	public int modifyCash(Cash cash) {
		return cashMapper.updateCash(cash);
	}
	
	//삭제
	public void removeCash(Cash cash) {
		cashMapper.deleteCash(cash);
	}
	
	
	//수입지출 총합리스트
	public List<DayAndPrice> getDayAndPriceList(String memberId, int year, int month) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("year", year);
		map.put("month", month);
		return cashMapper.selectDayAndPriceList(map);
	}
	
	//가게부리스트
	public Map<String, Object> getCashListByDate(Cash cash) {
		List<Cash> cashList = cashMapper.selectCashListByDate(cash);
		int cashFindSum = cashMapper.selectCashFindSum(cash);
		Map<String, Object> map = new HashMap<>();
		map.put("cashList", cashList);
		map.put("cashFindSum", cashFindSum);
		return map;
	}


	public void addCash(Cash cash) {
		cashMapper.insertCash(cash);
	}
}