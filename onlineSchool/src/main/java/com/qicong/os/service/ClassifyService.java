package com.qicong.os.service;

import java.util.*;

import com.qicong.os.bean.ClassifyBean;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.common.util.BeanUtil;
import com.qicong.os.common.util.JsonView;
import com.qicong.os.dao.CourseDao;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.qicong.os.domain.Classify;
import com.qicong.os.service.ClassifyService;
import com.qicong.os.dao.ClassifyDao;


@Service
public class ClassifyService extends AbstractService<Classify>{

	@Autowired
	private ClassifyDao entityDao;

	@Autowired
	private CourseDao courseDao;

	public Classify getById(Long id){
		return entityDao.getById(id);
	}

	public Classify getByCode(String code){
		return entityDao.getByCode(code);
	}

	public Map<String, ClassifyBean> queryAllClassifyMap(){
		List<Classify> list = entityDao.queryAll(new Classify());
		Map<String, ClassifyBean> map = new LinkedHashMap<String,ClassifyBean>();
		Iterator<Classify> it = list.iterator();
		while(it.hasNext()){
			Classify c = it.next();
			if("0".equals(c.getParentCode())){
				ClassifyBean bean = new ClassifyBean();
				BeanUtils.copyProperties(c,bean);
				map.put(bean.getCode(), bean);
			}else{
				if(null != map.get(c.getParentCode())){
					ClassifyBean parentBean = map.get(c.getParentCode());
					parentBean.getSubClassifyList().add(c);//添加二级分类到对应的一级分类下
				}
			}
		}
		return map;
	}

	public List<Classify> queryAll(Classify queryEntity){
		return entityDao.queryAll(queryEntity);
	}

	public Pagination<Classify> queryPage(Classify queryEntity ,Pagination<Classify> page){
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		List<Classify> items = entityDao.queryPage(queryEntity,page);
		page.setItemsTotalCount(itemsTotalCount);
		page.setItems(items);
		return page;
	}

	public void create(Classify entity){
		entity.setCreateAt(new Date());
		entity.setUpdateAt(new Date());
		entityDao.create(entity);
	}

	public void update(Classify entity){
		entityDao.update(entity);
	}

	//实现分类的删除
	public String deleteClassify(Long id){
		if(null == id){
			return JsonView.failureJson("id错误");
		}
		Classify entity = entityDao.getById(id);
		if(null == entity){
			return JsonView.failureJson("分类不存在");
		}

		//判断是否有子分类
		int subCount = entityDao.querySubClassifyCount(entity);
		if(subCount > 0){
			return JsonView.failureJson("有子分类 不允许删除");
		}
		//判断分类下是否有课程
		int courseCount = courseDao.getCountByClassify(entity);
		if(courseCount > 0){
			return JsonView.failureJson("分类下有课程 不允许删除");
		}

		//执行删除
		entityDao.delete(entity);
		return JsonView.successJson();
	}

	//对课程分类的排序
	public String sortClassify(Long id, Integer sortType){
		if(null == id){
			return JsonView.failureJson("id错误");
		}
		Classify entity = entityDao.getById(id);
		if(null == entity){
			return JsonView.failureJson("分类不存在");
		}

		if(null == sortType || (sortType != 0 && sortType != 1)){
			return JsonView.failureJson("排序方式不正确");
		}

		Classify tmpClassify;
		if(0 == sortType){//上移
			//获取前一个分类
			tmpClassify = entityDao.getPreClassify(entity);
		}else{//下移
			//获取后一个分类
			tmpClassify = entityDao.getNextClassify(entity);
		}
		if(null != tmpClassify){
			long sort = entity.getSort();
			entity.setSort(tmpClassify.getSort());
			entityDao.update(entity);

			tmpClassify.setSort(sort);
			entityDao.update(tmpClassify);
		}
		return JsonView.successJson();
	}


}

