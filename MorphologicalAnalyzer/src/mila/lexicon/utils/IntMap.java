package mila.lexicon.utils;

import java.util.HashMap;
import java.util.Map;

public class IntMap {
	private final Map<String, Integer> map;

	public IntMap(int initialCapacity) {
		map = new HashMap<>(initialCapacity);
	}

	public final void clear() {
		map.clear();
	}

	public final int get(String key) {
		Integer res = map.get(key);
		return res == null ? -1 : res;
	}

	public final void put(String key, String g) {
		map.put(key, Integer.parseInt(g));
	}

}