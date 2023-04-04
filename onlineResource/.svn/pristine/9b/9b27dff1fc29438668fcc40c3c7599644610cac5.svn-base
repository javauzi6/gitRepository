package com.qicong.os.web.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.qicong.os.domain.AuthUser;

/**
 * User: 祁大聪
 * 将登录的用户放到shiro session中
 * ShiroContext 作为统一的出、入口
 */
public class ShiroContext {
	
	public static final String SESSION_KEY = "_os_session_";

	public static String getSessionUsername() {
		return getSessionUser().getUsername();
	}
	
	public static AuthUser getSessionUser() {
		Session session = (Session) SecurityUtils.getSubject().getSession();
		AuthUser authUser = (AuthUser)session.getAttribute(SESSION_KEY);
		if(null != authUser) {
			authUser.setSalt(null);
			authUser.setPassword(null);
		}
		return authUser;
	}
	
	public static void updateSessionUser(AuthUser authUser) {
		Session session = (Session) SecurityUtils.getSubject().getSession();
		session.setAttribute(SESSION_KEY, authUser);
	}

	/**
	 * 是否已经登录
	 */
	public static boolean isLogin() {
		return null != getSession().getAttribute(SESSION_KEY);
	}
	
	public static Session getSession() {
		return (Session) SecurityUtils.getSubject().getSession();
	}
	
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

}


