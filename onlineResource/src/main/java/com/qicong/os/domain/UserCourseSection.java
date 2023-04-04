package com.qicong.os.domain;

import java.util.Date;


public class UserCourseSection extends AbstractEntity{

	private Long userId;//用户id
	private Long courseId;//课程id
	private Long sectionId;//章节id
	private Long status;//状态：0-学习中；1-学习结束
	private Long rate;//

	//数据库中并没有此字段，为了查询展示使用
	private String courseName;
	private String sectionName;

	public Long getUserId(){
		return userId;
	}
	public void setUserId(Long userId){
		this.userId = userId;
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

	public Long getStatus(){
		return status;
	}
	public void setStatus(Long status){
		this.status = status;
	}

	public Long getRate(){
		return rate;
	}
	public void setRate(Long rate){
		this.rate = rate;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
}
