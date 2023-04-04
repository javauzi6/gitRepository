package com.qicong.os.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qicong.os.bean.ChaptSectionBean;
import com.qicong.os.bean.ClassifyBean;
import com.qicong.os.common.page.Pagination;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.qicong.os.domain.Course;
import com.qicong.os.service.CourseService;
import com.qicong.os.dao.CourseDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


@Service
public class CourseService extends AbstractService<Course>{

	@Autowired
	private CourseDao entityDao;

	@Autowired
	private CourseSectionService courseSectionService;

	public Course getById(Long id){
		return entityDao.getById(id);
	}

	public List<Course> queryAll(Course queryEntity){
		return entityDao.queryAll(queryEntity);
	}

	public Pagination<Course> queryPage(Course queryEntity ,Pagination<Course> page){
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		List<Course> items = entityDao.queryPage(queryEntity,page);
		page.setItemsTotalCount(itemsTotalCount);
		page.setItems(items);
		return page;
	}

	//基本信息初始化
	public void initCourse(Course entity){
		entity.setOnsale(0);
		entity.setTime("0");
		entity.setUsername("system");
		entity.setDirection("");
		entity.setWeight(0);
		entity.setStudyCount(0);
		entity.setRecommend(0);
		if(null == entity.getPicture()){
			entity.setPicture("0");
		}
	}

	public void create(Course entity){
		entity.setCreateAt(new Date());
		entity.setUpdateAt(new Date());
		entityDao.create(entity);
	}

	@Transactional
	public Course createCourse(Course entity, List<ChaptSectionBean> chaptSections){
		initCourse(entity);
		this.create(entity);

		if(!CollectionUtils.isEmpty(chaptSections)){
			//创建课程的章节数据
			Integer totalTime = courseSectionService.createCourseSections(entity.getId(),chaptSections);
			entity.setTime(totalTime.toString());//存的是时间的整数
			this.update(entity);
		}

		return entity;
	}

	public void update(Course entity){
		entity.setUpdateAt(new Date());
		entityDao.update(entity);
	}

	public void delete(Course entity){
		entityDao.delete(entity);
	}

	//更新课程时长
	public void updateCourseTime(Long courseId, Integer time){
		Course course = entityDao.getById(courseId);
		if(null != course && null != time){
			String courseTime = course.getTime();
			if(StringUtils.isNotEmpty(courseTime)){
				time = Integer.parseInt(courseTime) + time;
				course.setTime(time.toString());
				this.update(course);
			}
		}
	}

	//准备分类对应的推荐课程数据
	public void prepareClassifyCourses(Map<String, ClassifyBean> classifyMap){
		for(String key : classifyMap.keySet()){
			Course course = new Course();
			course.setClassify(key);
			List<Course> courseList = this.entityDao.queryRecommendCourse(course);

			classifyMap.get(key).setRecommendCourseList(courseList);
		}
	}

	//获取名师公开课
	public List<Course> queryPublicCourses(){
		return entityDao.queryPublicCourses();
	}

	//获取直播课
	public List<Course> queryLiveCourses(){
		return entityDao.queryLiveCourses();
	}

	//系列课
	public List<Course> querySeriesCourses(){
		return entityDao.querySeriesCourses();
	}

	//精品课
	public List<Course> queryFreeCourses(){
		return entityDao.queryFreeCourses();
	}

	//最新课
	public List<Course> queryNewCourses(){
		return entityDao.queryNewCourses();
	}


}

