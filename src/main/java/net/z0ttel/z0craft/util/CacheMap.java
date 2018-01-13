package net.z0ttel.z0craft.util;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CacheMap<K, V> extends LinkedHashMap<K, V> {
	private long size;
	
	public CacheMap(long sizeIn) {
		super();
		size = sizeIn;
	}
	
	protected boolean removeEldestEntry(Map.Entry eldest) {
		return size() > size;
	}	
}

