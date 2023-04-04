package com.qicong.os.service;

import java.util.Date;
import java.util.List;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.domain.CourseSection;
import com.qicong.os.web.shiro.ShiroContext;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.qicong.os.domain.UserCourseSection;
import com.qicong.os.service.UserCourseSectionService;
import com.qicong.os.dao.UserCourseSectionDao;


@Service
public class UserCourseSectionService extends AbstractService<UserCourseSection>{

	@Autowired
	private UserCourseSectionDao entityDao;

	public UserCourseSection getById(Long id){
		return entityDao.getById(id);
	}

	public List<UserCourseSection> queryAll(UserCourseSection queryEntity){
		return entityDao.queryAll(queryEntity);
	}

	public List<UserCourseSection> queryUserCourse(UserCourseSection queryEntity){
		return entityDao.queryUserCourse(queryEntity);
	}

	public Pagination<UserCourseSection> queryPage(UserCourseSection queryEntity ,Pagination<UserCourseSection> page){
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		List<UserCourseSection> items = entityDao.queryPage(queryEntity,page);
		page.setItemsTotalCount(itemsTotalCount);
		page.setItems(items);
		return page;
	}

	public void create(UserCourseSection entity){
		entity.setCreateAt(new Date());
		entity.setUpdateAt(new Date());
		entityDao.create(entity);
	}

	public void update(UserCourseSection entity){
		entity.setUpdateAt(new Date());
		entityDao.update(entity);
	}

	public void delete(UserCourseSection entity){
		entityDao.delete(entity);
	}

	//获取当前用户对应的某个课程的最新小节
	public UserCourseSection getUserCourseSection(UserCourseSection queryEntity){
		return entityDao.getUserCourseSection(queryEntity);
	}

	//更新或插入用户的学习课程记录
	public void mergeUserCourseSection(CourseSection courseSection){
		if(ShiroContext.isLogin()){
			UserCourseSection entity = new UserCourseSection();
			entity.setUserId(ShiroContext.getSessionUser().getId());
			entity.setCourseId(courseSection.getCourseId());
			UserCourseSection resultEntity = entityDao.getUserCourseSection(entity);
			if(null == resultEntity){
				entity.setSectionId(courseSection.getId());
				entity.setStatus(0L);
				entity.setRate(0L);
				this.create(entity);
			}else{
				resultEntity.setSectionId(courseSection.getId());
				this.update(resultEntity);
			}
		}
	}


}

