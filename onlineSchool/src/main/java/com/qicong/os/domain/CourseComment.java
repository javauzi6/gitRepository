package com.qicong.os.domain;

import java.util.Date;


public class CourseComment extends AbstractEntity{

	private String username;//用户username
	private String toUsername;//评论对象username
	private Long courseId;//课程id
	private Long sectionId;//章节id
	private String sectionTitle;//章节标题
	private String content;//评论内容
	private Long refId;//引用id
	private String refContent;//引用内容
	private Integer type;//类型：0-评论；1-答疑QA

	//course_comment表中没有此字段，只是为了查询使用
	private String header;//用户的头像

	public String getUsername(){
		return username;
	}
	public void setUsername(String username){
		this.username = username;
	}

	public String getToUsername(){
		return toUsername;
	}
	public void setToUsername(String toUsername){
		this.toUsername = toUsername;
	}

	public Long getCourseId(){
		return courseId;
	}
	public void setCourseId(Long courseId){
		this.courseId = courseId;
	}

	public Long getSectionId(){
		return sectionId;
	}
	public void setSectionId(Long sectionId){
		this.sectionId = sectionId;
	}

	public String getSectionTitle(){
		return sectionTitle;
	}
	public void setSectionTitle(String sectionTitle){
		this.sectionTitle = sectionTitle;
	}

	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}

	public Long getRefId(){
		return refId;
	}
	public void setRefId(Long refId){
		this.refId = refId;
	}

	public String getRefContent(){
		return refContent;
	}
	public void setRefContent(String refContent){
		this.refContent = refContent;
	}

	public Integer getType(){
		return type;
	}
	public void setType(Integer type){
		this.type = type;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
}
