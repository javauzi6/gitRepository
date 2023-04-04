package com.qicong.os.domain;

import java.util.Date;


public class Classify extends AbstractEntity{

	private String name;//名称
	private String code;//
	private String parentCode;//父级别code
	private Long sort;//排序

	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}

	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code = code;
	}

	public String getParentCode(){
		return parentCode;
	}
	public void setParentCode(String parentCode){
		this.parentCode = parentCode;
	}

	public Long getSort(){
		return sort;
	}
	public void setSort(Long sort){
		this.sort = sort;
	}


}
