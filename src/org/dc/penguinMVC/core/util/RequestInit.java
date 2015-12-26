package org.dc.penguinMVC.core.util;

import java.util.HashMap;
import java.util.Map;

import org.dc.penguinMVC.core.handle.RequestHandle;

public class RequestInit {

    private static Map<String, RequestHandle> objMap = new HashMap<String, RequestHandle>();

	public static RequestHandle get(String key) {
		return objMap.get(key);
	}

	public static void put(String key ,RequestHandle value) throws Exception {
		//检查请求路径是否有冲突
		for (String k : objMap.keySet()) {
			if(k.equals(key)){
				throw new Exception("requestMapping映射URL有重复");
			}
		}
		objMap.put(key, value);
	}
}