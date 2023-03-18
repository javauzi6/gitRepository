package com.qicong.os.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.domain.Recommend;


@Mapper
public interface RecommendDao {

	public Recommend getById(Long id);

	public List<Recommend> queryAll(Recommend queryEntity);

	public Integer getTotalItemsCount(Recommend queryEntity);

	public List<Recommend> queryPage(Recommend queryEntity , Pagination<Recommend> page);

	public void create(Recommend entity);

	public void update(Recommend entity);

	public void delete(Recommend entity);

	public List<Recommend> queryRecommend(Integer count);

}

