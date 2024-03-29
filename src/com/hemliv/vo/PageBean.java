package com.hemliv.vo;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {
	//总条数
	private int totalCount;
	//总页数
	private int totalPage;
	//当前页数
	private int currentPage;
	//当前页显示的条数
	private int currentCount;
	//每页显示的数据
	private List<T> list;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
	
	
}
