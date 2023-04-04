package com.qicong.os.web.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: 祁大聪
 * Shiro 配置
 */
@Configuration
public class ShiroConfiguration {

	//shiro拦截器，拦截所有url的请求
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		factoryBean.setSecurityManager(securityManager);
		factoryBean.setLoginUrl("/login");
		
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/res/**", "anon");

		filterChainDefinitionMap.put("/courses", "anon");
		filterChainDefinitionMap.put("/course/**", "anon");
		filterChainDefinitionMap.put("/learn/**", "anon");

		filterChainDefinitionMap.put("/file/attachment/**", "anon");
		filterChainDefinitionMap.put("/idcode/**", "anon");

		filterChainDefinitionMap.put("/dologin", "anon");
		filterChainDefinitionMap.put("/register", "anon");
		filterChainDefinitionMap.put("/doregister", "anon");

		filterChainDefinitionMap.put("/**", "authc");//authc

		factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return factoryBean;
	}

	//安全校验
	@Bean
	public SessionsSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(authRealm());//实现登录、权限的Realm
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	@Bean
	public AuthRealm authRealm() {
		return new AuthRealm();
	}

	//去掉jsessionid小尾巴
	@Bean
	public DefaultWebSessionManager sessionManager() {
		//SessionIdUrlRewritingEnabled的默认值是false
		return new DefaultWebSessionManager();
	}
	
}

