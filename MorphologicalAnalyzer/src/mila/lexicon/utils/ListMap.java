package mila.lexicon.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ListMap<T> implements java.io.Serializable {
	private static final long serialVersionUID = 5845745764593500541L;

	private final Map<String, ArrayList<T>> map;

	public ListMap(int initialCapacity) {
		map = new HashMap<>(initialCapacity);
	}

	/** Erases the contents of the current HashChain. */
	public final void clear() {
		map.clear();
	}

	public final ArrayList<T> get(String key) throws Exception {
		return map.get(key);
	}

	public final int size() {
		return map.size();
	}

	public final void put(String key, T mwRecord) {
		if (mwRecord == null) {
			System.out.println("(F) T:put(): Bad idea! You tried to insert a null object into a Chain!");
		}
		if (!map.containsKey(key)) {
			ArrayList<T> value = new ArrayList<T>();
			map.put(key, value);
		}
		map.get(key).add(mwRecord);
	}

	public final void sput(String key, ArrayList<T> content) {
		if (content == null)
			throw new RuntimeException("Bad idea! You tried to insert " + "a null object into a Chain!");
		map.put(key, content);
	}
}