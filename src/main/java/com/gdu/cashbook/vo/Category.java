package com.gdu.cashbook.vo;

public class Category {
	private String CategoryName;
	public String getCategoryName() {
		return CategoryName;
	}
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}
	public String getCategoryDesc() {
		return CategoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		CategoryDesc = categoryDesc;
	}
	private String CategoryDesc;
	@Override
	public String toString() {
		return "Category [CategoryName=" + CategoryName + ", CategoryDesc=" + CategoryDesc + "]";
	}
}
