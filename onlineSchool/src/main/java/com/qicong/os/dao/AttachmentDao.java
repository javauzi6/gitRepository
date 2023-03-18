package com.qicong.os.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.domain.Attachment;


@Mapper
public interface AttachmentDao {

	public Attachment getById(Long id);

	public List<Attachment> queryAll(Attachment queryEntity);

	public Integer getTotalItemsCount(Attachment queryEntity);

	public List<Attachment> queryPage(Attachment queryEntity , Pagination<Attachment> page);

	public void create(Attachment entity);

	public void update(Attachment entity);

	public void delete(Attachment entity);



}

