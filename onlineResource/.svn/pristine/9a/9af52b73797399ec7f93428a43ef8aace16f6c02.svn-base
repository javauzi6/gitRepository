package com.qicong.os.service;

import java.util.Date;
import java.util.List;
import com.qicong.os.common.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.qicong.os.domain.Recommend;
import com.qicong.os.service.RecommendService;
import com.qicong.os.dao.RecommendDao;


@Service
public class RecommendService extends AbstractService<Recommend>{

	@Autowired
	private RecommendDao entityDao;

	public Recommend getById(Long id){
		return entityDao.getById(id);
	}

	public List<Recommend> queryAll(Recommend queryEntity){
		return entityDao.queryAll(queryEntity);
	}

	public List<Recommend> queryRecommend(Integer count){
		return entityDao.queryRecommend(count);
	}

	public Pagination<Recommend> queryPage(Recommend queryEntity ,Pagination<Recommend> page){
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		List<Recommend> items = entityDao.queryPage(queryEntity,page);
		page.setItemsTotalCount(itemsTotalCount);
		page.setItems(items);
		return page;
	}

	public void create(Recommend entity){
		entity.setCreateAt(new Date());
		entity.setUpdateAt(new Date());
		entityDao.create(entity);
	}

	public void update(Recommend entity){
		entity.setUpdateAt(new Date());
		entityDao.update(entity);
	}

	public void delete(Recommend entity){
		entityDao.delete(entity);
	}



}

