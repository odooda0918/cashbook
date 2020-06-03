package com.gdu.cashbook.vo;

public class Comment {
	private int commentNo;
	private String memberId;
	private String commentContent;
	private int boardNo;
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	@Override
	public String toString() {
		return "Comment [commentNo=" + commentNo + ", memberId=" + memberId + ", commentContent=" + commentContent
				+ ", boardNo=" + boardNo + "]";
	}
	

}
