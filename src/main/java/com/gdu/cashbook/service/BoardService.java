package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.mapper.CommentMapper;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Comment;

@Service
@Transactional
public class BoardService {
	@Autowired BoardMapper boardMapper;
	@Autowired CommentMapper commentMapper;
	
	//board 상세정보 출력
	public Map<String, Object> boardOne(Map<String, Object> mapp){
		
		int boardNo = (int)mapp.get("boardNo");
			System.out.println(boardNo+"<--boardNo");
			
		//댓글 페이징 currentPage
		int commentCurrentPage = (int)mapp.get("commentCurrentPage");
		System.out.println(commentCurrentPage+"<--commentCurrentPage");
		
		int rowPerPage = 3;
		int beginRow = (commentCurrentPage-1)*rowPerPage;
		int totalComment = commentMapper.totalComment(boardNo);
		System.out.println(totalComment+"<--totalComment");
		int commentLastPage = totalComment / rowPerPage;
		if(totalComment % rowPerPage !=0) {
				commentLastPage =+1;
		}
		System.out.println(totalComment+"<--totalComment");
		Map<String, Object> map2 =new HashMap<>();
		map2.put("beginRow", beginRow);
		map2.put("rowPerPage", rowPerPage);
		map2.put("boardNo", boardNo);
		
		List<Comment> commentList = commentMapper.getCommentList(boardNo);
		int firstBoardNo = boardMapper.firstBoardNo();
		int lastBoardNo = boardMapper.lastBoardNo();
		Board board = boardMapper.selectBoardOne(boardNo);
		int previousNo = boardMapper.previousNo(boardNo)	;
		int nextNo = boardMapper.nextNo(boardNo);
		
		System.out.println(firstBoardNo+"첫글");
		System.out.println(lastBoardNo+"마지막글");
		System.out.println(board+"<--board");
		System.out.println(nextNo+"<--다음");
		System.out.println(previousNo+"<--이전");
		
		//맵추가
		
		Map<String, Object>map = new HashMap<>();
		map.put("commentList", commentList);
		map.put("firstBoardNo", firstBoardNo); 
		map.put("lastBoardNo", lastBoardNo);
		map.put("board", board);
		map.put("previousNo", previousNo);
		map.put("nextNo", nextNo);
		map.put("commentLastPage", commentLastPage);
		
		return map;

 	}
	
	// boardList 출력
	public Map<String, Object> BoardList(int currentPage, String searchWord){
		
		//페이징
		int rowPerPage = 5;
		int beginRow = (currentPage-1)*rowPerPage;
		
		Map<String, Object> map = new HashMap<>();
		map.put("searchWord", searchWord);
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		
		
		//lastPage 출력
		
		int totalRow = boardMapper.getTotalRow(searchWord);
		int lastPage = totalRow/rowPerPage;
		if(totalRow%rowPerPage !=0) {
			lastPage+=1;
		}
		
		List<Board> list = boardMapper.selectBoardList(map);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("list",list);
		map2.put("lastPage", lastPage);
		
		
		return map2;
		}
		
	}
	
	
	
