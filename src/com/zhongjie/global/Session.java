package com.zhongjie.global;

import java.util.HashMap;
import java.util.Map;

public class Session {
	
	private static Session mSession;
	
	private Map<String, Object> map = null;
	private long createTime;
	
	public static Session getSession(){
		if(null == mSession)
			mSession = new Session();
		return mSession;
	}

	private Session() {
		map = new HashMap<String, Object>();
	}


	public long getCreateTime() {
		return createTime;
	}

	public Object get(String key) {
		return map.get(key);
	}

	public String getString(String key) {
		return String.valueOf(map.get(key));
	}

	public int getInt(String key) {
		try {
			String str = String.valueOf(map.get(key));
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public double getDouble(String key) {
		try {
			String str = String.valueOf(map.get(key));
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return 0.0d;
		}
	}

	public boolean getBoolean(String key) {
		try {
			String str = String.valueOf(map.get(key));
			return Boolean.parseBoolean(str);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public long getLong(String key) {
		try {
			String str = String.valueOf(map.get(key));
			return Long.parseLong(str);
		} catch (NumberFormatException e) {
			return 0L;
		}
	}

	public float getFloat(String key) {
		try {
			String str = String.valueOf(map.get(key));
			return Float.parseFloat(str);
		} catch (NumberFormatException e) {
			return 0f;
		}
	}

	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Object remove(String key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public void put(String key, Object value) {
		map.put(key, value);
	}

	public void putAll(Map<String, ?> map2) {
		this.map.putAll(map2);
	}
	
	public void cleanUpSession(){
		map.clear();
		map = null;
	}
}
