package com.qicong.os.dao;

import java.util.List;

import com.qicong.os.domain.Classify;
import org.apache.ibatis.annotations.Mapper;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.domain.Course;


@Mapper
public interface CourseDao {

	public Course getById(Long id);

	public List<Course> queryAll(Course queryEntity);

	public Integer getTotalItemsCount(Course queryEntity);

	public List<Course> queryPage(Course queryEntity , Pagination<Course> page);

	public void create(Course entity);

	public void update(Course entity);

	public void delete(Course entity);

	//根据分类查询，分类中课程的数量
	public Integer getCountByClassify(Classify entity);

	//根据课程分类获取推荐的课程
	public List<Course> queryRecommendCourse(Course entity);

	//获取名师公开课
	public List<Course> queryPublicCourses();

	//获取直播课
	public List<Course> queryLiveCourses();

	//系列课
	public List<Course> querySeriesCourses();

	//精品课
	public List<Course> queryFreeCourses();

	//最新课
	public List<Course> queryNewCourses();

}

