package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;

@Mapper
public interface BoardMapper {
	// 다음넘버 가져오기
	public int nextNo(int boardNo);
	// 이전넘버 가져오기
	public int previousNo(int boardNo);
	// boardList 출력
	public List<Board> selectBoardList(Map<String,Object> map);
	//라스트페이지
	public int getTotalRow(String searchWord);
	//boardNo로 해당게시글 정보 받아오기
	public Board selectBoardOne(int boardNo);
	//게시판 첫글
	public int firstBoardNo();
	// 마지막 글 번호로 이전 다음 게시판글 출력
	public int lastBoardNo();
}
