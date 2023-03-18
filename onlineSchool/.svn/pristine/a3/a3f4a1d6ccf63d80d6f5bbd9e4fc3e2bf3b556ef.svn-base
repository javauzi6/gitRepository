package com.qicong.os.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.domain.CourseSection;


@Mapper
public interface CourseSectionDao {

	public CourseSection getById(Long id);

	public List<CourseSection> queryAll(CourseSection queryEntity);

	public Integer getTotalItemsCount(CourseSection queryEntity);

	public List<CourseSection> queryPage(CourseSection queryEntity , Pagination<CourseSection> page);

	public void create(CourseSection entity);

	public void update(CourseSection entity);

	public void delete(CourseSection entity);

	//获取最大的排序sort的值
	public Integer getMaxSort(CourseSection entity);

	//批量创建
	public void createList(List<CourseSection> list);

	//获取课程的章节数据
	public List<CourseSection> queryChaptSections(CourseSection entity);

	//获取下一条数据
	public CourseSection getNextCourseSection(CourseSection entity);

	//获取上一条数据
	public CourseSection getPreCourseSection(CourseSection entity);

	//删除此章下面的所有小节
	public void deleteByParentId(CourseSection entity);

}

