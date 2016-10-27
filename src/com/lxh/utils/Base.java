package com.lxh.utils;

public class Base {
	
	private int curPage;
	private int pageSize;
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "Base [curPage=" + curPage + ", pageSize=" + pageSize + "]";
	}
	public Base(int curPage, int pageSize) {
		super();
		this.curPage = curPage;
		this.pageSize = pageSize;
	}
	public Base() {
		super();
	}
	
	
}
