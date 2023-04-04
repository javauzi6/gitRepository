package com.qicong.os.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.domain.AuthUser;


@Mapper
public interface AuthUserDao {

	public AuthUser getById(Long id);

	public AuthUser getByUsername(String username);

	public List<AuthUser> queryAll(AuthUser queryEntity);

	public Integer getTotalItemsCount(AuthUser queryEntity);

	public List<AuthUser> queryPage(AuthUser queryEntity , Pagination<AuthUser> page);

	public void create(AuthUser entity);

	public void update(AuthUser entity);

	public void delete(AuthUser entity);



}

