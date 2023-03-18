package com.qicong.os.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.qicong.os.bean.DownloadBean;
import com.qicong.os.bean.ImageBean;
import com.qicong.os.common.exception.ApiError;
import com.qicong.os.common.exception.ApiException;
import com.qicong.os.common.page.Pagination;
import com.qicong.os.common.util.HashUtil;
import com.qicong.os.common.util.IdUtil;
import com.qicong.os.common.util.ImageUtil;
import com.qicong.os.dao.ResourceDao;
import com.qicong.os.domain.Resource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.qicong.os.domain.Attachment;
import com.qicong.os.service.AttachmentService;
import com.qicong.os.dao.AttachmentDao;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;


@Service
public class AttachmentService extends AbstractService<Attachment>{

	@Autowired
	private AttachmentDao entityDao;

	@Autowired
	private ResourceDao resourceDao;

	public Attachment getById(Long id){
		return entityDao.getById(id);
	}

	@Transactional
	public Long createAttachment(String name, String recommdPicture){
		//保存资源
		String hash = HashUtil.sha256(recommdPicture);
		Resource r = this.resourceDao.getByHash(hash);
		if(null == r){
			r = new Resource();
			r.setId(IdUtil.nextId());
			r.setEncoding("BASE64");
			r.setHash(hash);
			r.setContent(recommdPicture);
			r.setCreateAt(new Date());
			r.setUpdateAt(new Date());
			this.resourceDao.create(r);
		}

		//创建附件
		byte[] data = Base64.getDecoder().decode(recommdPicture);
		ImageBean image = ImageUtil.readImage(data);
		Attachment a = new Attachment();
		a.setResourceId(r.getId());
		a.setUserId(0L);//现在还没有登录，用0先设置着
		a.setName(name);
		a.setMime(image.mime);
		a.setWidth(image.width);
		a.setHeight(image.height);
		a.setSize(data.length);
		this.entityDao.create(a);

		return a.getId();
	}

	public DownloadBean downloadAttachment(long id, char size){
		Attachment a = entityDao.getById(id);
		if(null == a){
			throw  new ApiException(ApiError.PARAMETER_INVALID,"id","Resouce not found");
		}
		Resource r = this.resourceDao.getById(a.getResourceId());
		if(null == r){
			throw  new ApiException(ApiError.PARAMETER_INVALID,"id","Resouce not found");
		}

		byte[] data = Base64.getDecoder().decode(r.getContent());
		if(size == '0'){
			return new DownloadBean(a.getMime(),data);
		}

		//其他大小
		int originWidth = a.getWidth();
		int targetWidth = originWidth;
		boolean resize = false;
		if (size == 's') {
			if (originWidth > 160) {
				targetWidth = 160;
				resize = true;
			}
		} else if (size == 'm') {
			if (originWidth > 320) {
				targetWidth = 320;
				resize = true;
			}
		} else if (size == 'l') {
			if (originWidth > 640) {
				targetWidth = 640;
				resize = true;
			}
		}
		if (!resize) {
			return new DownloadBean(a.getMime(), data);
		}
		BufferedImage resizedImage = null;
		try (ByteArrayInputStream input = new ByteArrayInputStream(data)) {
			BufferedImage originImage = ImageIO.read(input);
			resizedImage = ImageUtil.resizeKeepRatio(originImage, targetWidth);
		} catch (IOException e) {
			throw new ApiException(ApiError.PARAMETER_INVALID, null, "Could not resize image.");
		}
		try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			ImageIO.write(resizedImage, "jpeg", output);
			return new DownloadBean("image/jpeg", output.toByteArray());
		} catch (IOException e) {
			throw new ApiException(ApiError.PARAMETER_INVALID, null, "Could not resize image.");
		}

	}

	public List<Attachment> queryAll(Attachment queryEntity){
		return entityDao.queryAll(queryEntity);
	}

	public Pagination<Attachment> queryPage(Attachment queryEntity ,Pagination<Attachment> page){
		Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
		List<Attachment> items = entityDao.queryPage(queryEntity,page);
		page.setItemsTotalCount(itemsTotalCount);
		page.setItems(items);
		return page;
	}

	public void create(Attachment entity){
		entity.setCreateAt(new Date());
		entityDao.create(entity);
	}

	public void update(Attachment entity){
		entityDao.update(entity);
	}

	public void delete(Attachment entity){
		entityDao.delete(entity);
	}



}

