package com.gdu.cashbook.vo;

public class MemberId {
	public int MemberNo;
	public String MemberId;
	public int getMemberNo() {
		return MemberNo;
	}
	public void setMemberNo(int memberNo) {
		MemberNo = memberNo;
	}
	public String getMemberId() {
		return MemberId;
	}
	public void setMemberId(String memberId) {
		MemberId = memberId;
	}
	@Override
	public String toString() {
		return "MemberId [MemberNo=" + MemberNo + ", MemberId=" + MemberId + "]";
	}
	
	
}
