package com.gdu.cashbook.vo;

public class Cash {
	private int cashNo;
	private String memberId;
	private String cashDate;
	private String cashFind;
	private String categoryName;
	private int cashPrice;
	private String cashPlace;
	private String cashMemo;
	@Override
	public String toString() {
		return "Cash [cashNo=" + cashNo + ", memberId=" + memberId + ", cashDate=" + cashDate + ", cashFind=" + cashFind
				+ ", categoryName=" + categoryName + ", cashPrice=" + cashPrice + ", cashPlace=" + cashPlace
				+ ", cashMemo=" + cashMemo + "]";
	}
	public int getCashNo() {
		return cashNo;
	}
	public void setCashNo(int cashNo) {
		this.cashNo = cashNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCashDate() {
		return cashDate;
	}
	public void setCashDate(String cashDate) {
		this.cashDate = cashDate;
	}
	public String getCashFind() {
		return cashFind;
	}
	public void setCashFind(String cashFind) {
		this.cashFind = cashFind;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getCashPrice() {
		return cashPrice;
	}
	public void setCashPrice(int cashPrice) {
		this.cashPrice = cashPrice;
	}
	public String getCashPlace() {
		return cashPlace;
	}
	public void setCashPlace(String cashPlace) {
		this.cashPlace = cashPlace;
	}
	public String getCashMemo() {
		return cashMemo;
	}
	public void setCashMemo(String cashMemo) {
		this.cashMemo = cashMemo;
	}
}
	
	