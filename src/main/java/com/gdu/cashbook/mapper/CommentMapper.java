package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Comment;

@Mapper
public interface CommentMapper {
	//commentList 출력
	public List<Comment> getCommentList(int boardNo);
	
	
	//댓글 총 개수 표시
	public int totalComment(int boardNo);

}
