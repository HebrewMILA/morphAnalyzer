package mila.lexicon.utils;

import mila.lexicon.dbUtils.MWEinflectionsRecord;

public class MWinflections extends ListMap<MWEinflectionsRecord> {
	private static final long serialVersionUID = 1737934603049142268L;

	public MWinflections(int initialCapacity) {
		super(initialCapacity);
	}
}
