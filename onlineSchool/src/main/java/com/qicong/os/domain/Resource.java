package com.qicong.os.domain;

import java.util.Date;


public class Resource extends AbstractEntity{

	private String encoding;//
	private String hash;//
	private String content;//

	public String getEncoding(){
		return encoding;
	}
	public void setEncoding(String encoding){
		this.encoding = encoding;
	}

	public String getHash(){
		return hash;
	}
	public void setHash(String hash){
		this.hash = hash;
	}

	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}


}
