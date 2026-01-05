package com.comiccon.exceptions;

import java.util.HashMap;
import java.util.Map;

public class AppException extends RuntimeException {
	
	private final Map<String, Object> details = new HashMap<>();

	public AppException(String msg) {
		super(msg);
	}
	
	public AppException addDetail(String key,Object value) {
		details.put(key, value);
		return this;
	}
	
	public Map<String,Object> getDeatil(){
		return details;
	}
	
	
}
