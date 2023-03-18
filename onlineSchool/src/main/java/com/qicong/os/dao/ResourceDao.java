package com.qicong.os.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.domain.Resource;


@Mapper
public interface ResourceDao {

	public Resource getById(Long id);

	public List<Resource> queryAll(Resource queryEntity);

	public Integer getTotalItemsCount(Resource queryEntity);

	public List<Resource> queryPage(Resource queryEntity , Pagination<Resource> page);

	public void create(Resource entity);

	public void update(Resource entity);

	public void delete(Resource entity);

	public Resource getByHash(String hash);

}

