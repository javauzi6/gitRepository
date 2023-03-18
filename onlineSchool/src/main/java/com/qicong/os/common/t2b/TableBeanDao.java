package com.qicong.os.common.t2b;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TableBeanDao {
	public List<String> listTables(TableBean vo);
	public List<TableBean> listTableCols(TableBean vo);
}

