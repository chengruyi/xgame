package com.brothers.game.scanner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class InvokerHoler {

	private static Map<Short, Map<Short, Invoker>> invokers = new ConcurrentHashMap<>();

	public static void addInvoker(short module, short cmd, Invoker invoker) {
		Map<Short, Invoker> map = invokers.get(module);
		if (map == null) {
			map = new HashMap<>();
			invokers.put(module, map);
		}
		map.put(cmd, invoker);
	}

	public static Invoker getInvoker(short module, short cmd) {
		Map<Short, Invoker> map = invokers.get(module);
		if (map != null) {
			return map.get(cmd);
		}
		return null;
	}

}
