package com.qicong.os.service;

import java.util.Date;
import java.util.List;
import com.qicong.os.common.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.qicong.os.domain.CourseComment;
import com.qicong.os.service.CourseCommentService;
import com.qicong.os.dao.CourseCommentDao;


@Service
public class CourseCommentService extends AbstractService<CourseComment>{

	@Autowired
	private CourseCommentDao entityDao;

	public CourseComment getById(Long id){
		return entityDao.getById(id);
	}

	public List<CourseComment> queryAll(CourseComment queryEntity){
		return entityDao.queryAll(queryEntity);
	}

	public List<CourseComment> queryCourseComment(CourseComment queryEntity){
		return entityDao.queryCourseComment(queryEntity);
	}

	public Pagination<CourseComment> queryPage(CourseComment queryEntity ,Pagination<CourseComment> page){
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		List<CourseComment> items = entityDao.queryPage(queryEntity,page);
		page.setItemsTotalCount(itemsTotalCount);
		page.setItems(items);
		return page;
	}

	public void create(CourseComment entity){
		entity.setCreateAt(new Date());
		entity.setUpdateAt(new Date());
		entityDao.create(entity);
	}

	public void update(CourseComment entity){
		entityDao.update(entity);
	}

	public void delete(CourseComment entity){
		entityDao.delete(entity);
	}

	public List<CourseComment> queryUserCourseComment(String username){
		return entityDao.queryUserCourseComment(username);
	}

}

