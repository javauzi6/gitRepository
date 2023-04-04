package com.qicong.os.service;

import java.util.Date;
import java.util.List;
import com.qicong.os.common.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.qicong.os.domain.AuthUser;
import com.qicong.os.service.AuthUserService;
import com.qicong.os.dao.AuthUserDao;


@Service
public class AuthUserService extends AbstractService<AuthUser>{

	@Autowired
	private AuthUserDao entityDao;

	public AuthUser getById(Long id){
		return entityDao.getById(id);
	}

	public AuthUser getByUsername(String username){
		return entityDao.getByUsername(username);
	}

	public List<AuthUser> queryAll(AuthUser queryEntity){
		return entityDao.queryAll(queryEntity);
	}

	public Pagination<AuthUser> queryPage(AuthUser queryEntity ,Pagination<AuthUser> page){
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		List<AuthUser> items = entityDao.queryPage(queryEntity,page);
		page.setItemsTotalCount(itemsTotalCount);
		page.setItems(items);
		return page;
	}

	public void create(AuthUser entity){
		entity.setCreateAt(new Date());
		entity.setUpdateAt(new Date());
		entityDao.create(entity);
	}

	public void update(AuthUser entity){
		entity.setUpdateAt(new Date());
		entityDao.update(entity);
	}

	public void delete(AuthUser entity){
		entityDao.delete(entity);
	}



}

