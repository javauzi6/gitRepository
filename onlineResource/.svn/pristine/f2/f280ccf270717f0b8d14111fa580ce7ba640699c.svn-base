package com.qicong.os.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.domain.UserCourseSection;


@Mapper
public interface UserCourseSectionDao {

	public UserCourseSection getById(Long id);

	public List<UserCourseSection> queryAll(UserCourseSection queryEntity);

	public List<UserCourseSection> queryUserCourse(UserCourseSection queryEntity);

	public Integer getTotalItemsCount(UserCourseSection queryEntity);

	public List<UserCourseSection> queryPage(UserCourseSection queryEntity , Pagination<UserCourseSection> page);

	public void create(UserCourseSection entity);

	public void update(UserCourseSection entity);

	public void delete(UserCourseSection entity);


	public UserCourseSection getUserCourseSection(UserCourseSection queryEntity);


}

