package com.qicong.os.web.shiro;

import java.util.Map;

import com.qicong.os.common.freemarker.FreeMarkerModel;
import com.qicong.os.common.freemarker.FreemarkerTag;
import com.qicong.os.common.freemarker.FreemarkerTagModel;
import com.qicong.os.domain.AuthUser;
import org.apache.commons.lang.StringUtils;

/**
*  User: 祁大聪
*  定义一个 shiro 标签，结合Shiro框架，获取登录用户信息
**/

@FreemarkerTag(value="shiro")
public class ShiroFreemarkerTag implements FreemarkerTagModel{
	
	static final String NAME = "name";

	@Override
	public Object getParams(Map params) {
		String name = getString(params, NAME);
		if(StringUtils.isEmpty(name))
			return null;

		Object model = null;
		if("user".equals(name)) {//返回当前登录用户信息
			model = getAuthUser(params);
		}

		return new FreeMarkerModel(name, model);
	}
	
	public AuthUser getAuthUser(Map params) {
		//从Shiro中获取当前用户
		return ShiroContext.getSessionUser();
	}

}
