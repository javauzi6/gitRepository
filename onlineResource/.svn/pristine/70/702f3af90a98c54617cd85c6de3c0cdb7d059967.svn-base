package com.qicong.os.common.util;

import com.alibaba.fastjson.JSONObject;

/**
 * User：祁大聪
 */
public class JsonView {
	
	public static final Integer SUCCESS_CODE = 0;//成功
	public static final Integer FAILURE_CODE = 1;//失败
	
	public static String successJson(Object data){
		JSONObject resultJson = new JSONObject();
		resultJson.put("errcode", SUCCESS_CODE);
		resultJson.put("data", data);
		return resultJson.toJSONString();
	}
	
	public static String successJson(String message) {
		JSONObject resultJson = new JSONObject();
		resultJson.put("errcode", SUCCESS_CODE);
		resultJson.put("message", message);
		return resultJson.toJSONString();
	}
	
	public static String successJson() {
		JSONObject resultJson = new JSONObject();
		resultJson.put("errcode", SUCCESS_CODE);
		return resultJson.toJSONString();
	}
	
	public static String failureJson(String message){
		JSONObject resultJson = new JSONObject();
		resultJson.put("errcode", FAILURE_CODE);
		resultJson.put("message", message);
		return resultJson.toJSONString();
	}
	
	public static String renderJson(Integer errcode, Object data) {
		JSONObject resultJson = new JSONObject();
		resultJson.put("errcode", errcode);
		resultJson.put("data", data);
		return resultJson.toJSONString();
	}
	
	public static String renderJson(Integer errcode) {
		JSONObject resultJson = new JSONObject();
		resultJson.put("errcode", errcode);
		return resultJson.toJSONString();
	}
	
}
