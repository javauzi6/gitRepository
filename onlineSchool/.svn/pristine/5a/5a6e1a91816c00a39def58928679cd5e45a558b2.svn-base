package com.qicong.os.service;

import java.util.*;

import com.qicong.os.bean.ChaptSectionBean;
import com.qicong.os.common.page.Pagination;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.qicong.os.domain.CourseSection;
import com.qicong.os.service.CourseSectionService;
import com.qicong.os.dao.CourseSectionDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


@Service
public class CourseSectionService extends AbstractService<CourseSection>{

	@Autowired
	private CourseSectionDao entityDao;

	@Autowired
	private CourseService courseService;

	public CourseSection getById(Long id){
		return entityDao.getById(id);
	}

	public List<CourseSection> queryAll(CourseSection queryEntity){
		return entityDao.queryAll(queryEntity);
	}

	public Pagination<CourseSection> queryPage(CourseSection queryEntity ,Pagination<CourseSection> page){
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		List<CourseSection> items = entityDao.queryPage(queryEntity,page);
		page.setItemsTotalCount(itemsTotalCount);
		page.setItems(items);
		return page;
	}

	public void create(CourseSection entity){
		entity.setCreateAt(new Date());
		entityDao.create(entity);
	}

	//完成课程章节的创建，返回章节的时长
	public Integer createCourseSections(Long courseId, List<ChaptSectionBean> chaptSections){
		Integer courseTotalTime = 0;

		//获取最大的sort
		Integer maxChaptSort = this.getMaxSort(courseId, 0L);
		if(null == maxChaptSort) maxChaptSort = 0;

		for(int i = 0; i < chaptSections.size(); i++){
			//处理章的数据
			ChaptSectionBean chaptBean = chaptSections.get(i);
			CourseSection chaptSection = new CourseSection();
			chaptSection.setCourseId(courseId);
			chaptSection.setName(chaptBean.getChaptName());
			chaptSection.setParentId(0L);//章
			chaptSection.setOnsale(1);//上架
			chaptSection.setVideoUrl("");
			chaptSection.setCreateAt(new Date());
			chaptSection.setUpdateAt(new Date());
			maxChaptSort += 1;
			chaptSection.setSort(maxChaptSort);
			chaptSection.setTime(0);

			this.entityDao.create(chaptSection);//创建章

			//处理小节的数据
			Integer chaptTotalTime = 0;
			if(!CollectionUtils.isEmpty(chaptBean.getSections())){
				//获取最大的sort
				Integer maxSectionSort = this.getMaxSort(courseId, chaptSection.getId());
				if(null == maxSectionSort) maxSectionSort = 0;

				for(int j = 0; j < chaptBean.getSections().size(); j++){
					CourseSection section = chaptBean.getSections().get(j);
					section.setCourseId(courseId);
					section.setParentId(chaptSection.getId());
					if(StringUtils.isEmpty(section.getName())){
						section.setName("");
					}
					if(StringUtils.isEmpty(section.getVideoUrl())){
						section.setVideoUrl("");
					}
					if(null == section.getTime()){
						section.setTime(0);
					}
					section.setOnsale(1);
					section.setCreateAt(new Date());
					section.setUpdateAt(new Date());
					maxSectionSort += 1;
					section.setSort(maxSectionSort);

					chaptTotalTime += section.getTime();//每一章的总时长
				}

				//批量创建所有的小节
				this.entityDao.createList(chaptBean.getSections());

				//更新章的总时长
				if(chaptTotalTime != 0){
					chaptSection.setTime(chaptTotalTime);
					this.entityDao.update(chaptSection);

					courseTotalTime += chaptTotalTime;//课程的总时长
				}
			}
		}

		return courseTotalTime;//返回课程总时间
	}

	public Integer getMaxSort(Long courseId, Long parentId){
		CourseSection entity = new CourseSection();
		entity.setCourseId(courseId);
		entity.setParentId(parentId);
		return entityDao.getMaxSort(entity);
	}

	//获取课程的章节数据
	public Map<Long, ChaptSectionBean> queryChaptSections(Long courseId){
		Map<Long, ChaptSectionBean> map = new LinkedHashMap<Long, ChaptSectionBean>();

		CourseSection queryEntity = new CourseSection();
		queryEntity.setCourseId(courseId);
		List<CourseSection> list = this.entityDao.queryChaptSections(queryEntity);

		if(!CollectionUtils.isEmpty(list)){
			Iterator<CourseSection> it = list.iterator();
			while(it.hasNext()){
				CourseSection item = it.next();
				//如果是章
				if(Long.valueOf(0).equals(item.getParentId())){
					ChaptSectionBean bean = new ChaptSectionBean();
					bean.setChaptName(item.getName());
					bean.setChaptId(item.getId());
					bean.setTime(item.getTime());
					bean.setSections(new ArrayList<CourseSection>());
					map.put(item.getId(), bean);
				}else{//如果是小节，那就放到对应的章的sections中
					map.get(item.getParentId()).getSections().add(item);
				}
			}
		}
		return map;
	}

	public void update(CourseSection entity){
		entityDao.update(entity);
	}

	public void delete(CourseSection entity){
		entityDao.delete(entity);
	}

	//更新课程章节
	@Transactional
	public void updateSection(CourseSection entity){
		CourseSection dbCourseSection = getById(entity.getId());
		if(null == entity.getTime()){
			entity.setTime(0);
		}

		if(0 != dbCourseSection.getParentId()){//修改的是小节
			//更新章的时间和课程的时间
			Integer time = entity.getTime() - dbCourseSection.getTime();
			if(time != 0){
				//更新课程时间
				courseService.updateCourseTime(dbCourseSection.getCourseId(), time);

				//更新章的时间
				CourseSection parent = this.getById(dbCourseSection.getParentId());
				Integer parentTime = parent.getTime();
				parent.setTime(parentTime + time);
				entityDao.update(parent);
			}
		}

		entityDao.update(entity);
	}

	//移动排序
	public void moveSection(Long id, String direct){
		CourseSection courseSection = entityDao.getById(id);
		if("down".equals(direct)){//向下移动
			CourseSection nextCourseSection = entityDao.getNextCourseSection(courseSection);
			Integer sort = courseSection.getSort();
			courseSection.setSort(nextCourseSection.getSort());
			nextCourseSection.setSort(sort);

			entityDao.update(nextCourseSection);
			entityDao.update(courseSection);
		}else{//向上移动
			CourseSection preCourseSection = entityDao.getPreCourseSection(courseSection);
			Integer sort = courseSection.getSort();
			courseSection.setSort(preCourseSection.getSort());
			preCourseSection.setSort(sort);

			entityDao.update(preCourseSection);
			entityDao.update(courseSection);
		}
	}

	//删除课程章节
	@Transactional
	public void deleteSection(Long id){
		CourseSection courseSection = entityDao.getById(id);

		if(null != courseSection){
			Integer time = courseSection.getTime();
			time = -time;
			courseService.updateCourseTime(courseSection.getCourseId(), time);
			entityDao.delete(courseSection);

			if(courseSection.getParentId() == 0){//删除章
				entityDao.deleteByParentId(courseSection);
			}else{//删除小节
				//更新章的时间
				CourseSection parent = this.getById(courseSection.getParentId());
				Integer parentTime = parent.getTime();
				parent.setTime(parentTime - courseSection.getTime());
				entityDao.update(parent);
			}
		}

	}

}

