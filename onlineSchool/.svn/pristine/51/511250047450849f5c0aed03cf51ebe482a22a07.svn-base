package com.qicong.os.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.domain.Classify;


@Mapper
public interface ClassifyDao {

	public Classify getById(Long id);

	public List<Classify> queryAll(Classify queryEntity);

	public Integer getTotalItemsCount(Classify queryEntity);

	public List<Classify> queryPage(Classify queryEntity , Pagination<Classify> page);

	public void create(Classify entity);

	public void update(Classify entity);

	public void delete(Classify entity);

	public Integer querySubClassifyCount(Classify entity);

	public Classify getPreClassify(Classify entity);

	public Classify getNextClassify(Classify entity);

	public Classify getByCode(String code);

}

