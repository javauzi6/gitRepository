package com.qicong.os.common.page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * User: 祁大聪
 * 分页类，直接拷贝使用即可
 */
public class Pagination<E> {
	private List<E> items;//分页记录
	private int pageTotalCount = 0;//总页数
	private int itemsTotalCount = 0;//总记录数
	private List<Integer> showNums = new ArrayList<Integer>();//显示的页码

	private int showPage = 10;//显示10个页码
	private int pageSize = 10;//每页10条数据
	private int pageNum = 1;//从第一页显示
	private boolean firstPage;//是否是第一页
	private boolean lastPage;//是否是最后一页
	private int startIndex;//分页开始

	private String sortField="createAt";//排序字段
	private String sortDirection = "DESC";//排序方向

	public Pagination() {}
	
    public Pagination(int pageNum, int pageSize , int itemsTotalCount , List<E> items) {
    	this.setItemsTotalCount(itemsTotalCount);
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
        this.setItems(items);
        this.initShowNum();
    }

	public int getShowPage() {
		return showPage;
	}

	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}
    
	public void setItemsTotalCount(int itemsTotalCount) {
		this.itemsTotalCount = itemsTotalCount;
		if(itemsTotalCount % this.pageSize == 0){
			this.pageTotalCount = itemsTotalCount/this.pageSize;
		}else{
			this.pageTotalCount = itemsTotalCount/this.pageSize + 1;
		}
		if(this.pageNum > this.pageTotalCount){
			this.pageNum = 1;
		}
		if(this.itemsTotalCount <= this.pageSize){
			this.firstPage = true;
			this.lastPage = true;
		}
		initShowNum();
	}
	
	private void initShowNum(){
		int startIndex;
		int endIndex;
		if(pageNum - showPage/2 > 1){
			startIndex = pageNum-showPage/2;
			endIndex = pageNum + showPage/2 - 1;
			if(endIndex > pageTotalCount){
				endIndex = pageTotalCount;
				startIndex = endIndex - showPage + 1;
			}
			if(startIndex <= 0) {
				startIndex = 1;
			}
		}else{
			startIndex = 1;
			endIndex = Math.min(pageTotalCount, showPage);
		}
		for(int i = startIndex; i <= endIndex ; i++){
			this.showNums.add(i);
		}
	}

	public void setItems(Collection<E> items) {
		if (items == null) items = Collections.emptyList();
		this.items = new ArrayList<E>(items);
		this.lastPage = this.pageNum == this.pageTotalCount;
		this.firstPage = this.pageNum == 1;
	}
	
	public List<Integer> getShowNums() {
		return showNums;
	}

	public int getPageTotalCount(){
		return this.pageTotalCount;
	}

	public int getFirstPageNum() {
		return 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		if (pageNum < 1) pageNum = 1;
		this.pageNum = pageNum;
	}

	public int getStartIndex() {
		this.startIndex = (this.pageNum - 1) * this.pageSize;
		if(this.startIndex <= 0){
			this.startIndex = 0;
		}
		return this.startIndex;
	}

	public List<E> getItems() {
		return items;
	}

	public boolean isFirstPage() {
		firstPage = (getPageNum() <= getFirstPageNum());
		return firstPage;
	}

	public boolean isLastPage() {
		lastPage = (getPageNum() >= pageTotalCount);
		return lastPage;
	}

	public int getPrePageNum() {
		return isFirstPage() ? getFirstPageNum() : getPageNum() - 1;
	}

	public int getNextPageNum() {
		return isLastPage() ? getPageNum() : getPageNum() + 1;
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public int getItemsTotalCount() {
		return itemsTotalCount;
	}

	public int getLastPageNum() {
		return itemsTotalCount;
	}

	/**
	 * 按照sortField升序
	 * @param sortField：指java bean中的属性
	 */
	public void ascSortField(String sortField) {
		this.sortField = sortField;
		this.sortDirection = " ASC ";
	}

	/**
	 * 按照sortField降序
	 * @param sortField ：指java bean中的属性
	 */
	public void descSortField(String sortField) {
		this.sortField = sortField;
		this.sortDirection = " DESC ";
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	@Override
	public String toString() {
		return "Page[" + this.getPageNum() + "]:" + items.toString();
	}
}

