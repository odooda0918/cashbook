package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;

@Mapper
public interface CashMapper {
	// 월별리스트
	public List<Cash> selectCashListByDate(Cash cash);

	//가게부 가져오기
	
	public Cash selectCashOne(int cashNo);
	
	
	//수입 지출 합산구하기
	public int selectCashFindSum(Cash cash);

	//월별 총액 구하기
	public List<DayAndPrice> selectDayAndPriceList(Map<String, Object> map);
	
	//삭제
	public void deleteCash(Cash cash);
	
	//수정
	public int updateCash(Cash cash);

	
}