package com.qicong.os.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.domain.CourseComment;


@Mapper
public interface CourseCommentDao {

	public CourseComment getById(Long id);

	public List<CourseComment> queryAll(CourseComment queryEntity);

	public Integer getTotalItemsCount(CourseComment queryEntity);

	public List<CourseComment> queryPage(CourseComment queryEntity , Pagination<CourseComment> page);

	public void create(CourseComment entity);

	public void update(CourseComment entity);

	public void delete(CourseComment entity);

	//加载课程的评论
	public List<CourseComment> queryCourseComment(CourseComment queryEntity);

	//根据toUserName加载评论
	public List<CourseComment> queryUserCourseComment(String username);

}

