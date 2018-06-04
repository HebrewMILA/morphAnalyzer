package mila.dataStructures;

/**
 * 
 * DBInflectionsRecord.java Purpose: A class used as a data structure (record)
 * to hold an entry from the inflections table/data file
 * 
 * @author Dalia Bojan
 * @version %G%
 */

public class DBInflectionsRecord {
	/** transliterated form of the inflected form */
	String transliterated = "";
	/** inflected form in Hebrew */
	String surface = "";
	/** The part of speech of the inflected form */
	String basePos = "";
	/**
	 * The lexicon id of the lexicon entry from which the inflected form was
	 * generated
	 */
	String baseLexiconPointer = "";
	String baseAlternatePointer = ""; // TODO add shit
	/** The lexicon item from which the inflected form was generated */
	String baseUndottedLItem = "";
	/**
	 * The transliterated form of the lexicon item from which the inflected form was
	 * generated
	 */
	String baseTransliteratedLItem = "";
	/** The register of the inflected form */
	String register = "";
	/** The spelling of the inflected form */
	String spelling = "";
	/** The startus (absolute/construct/unspecified) of the inflected form */
	String status = "";
	/**
	 * The suffix type of the inflected form - can be possessive/accusative or
	 * nominative/pronomial
	 */
	String suffixFunction = "";
	/** person/gender/number of the inflected form as appears in the suffix */
	String PGN = "";
	/**
	 * The binyan of the inflected form - relevant only for verb, participle etc
	 */
	String binyan = "";
	/**
	 * The tense of the inflected form - relevant only for verb, participle etc
	 */
	String tense = "";
	/**
	 * The root of the inflected form - relevant only for verb, participle etc
	 */
	String root = "";
	/** The number of the inflected form */
	String baseNumber = "";
	/** The gender of the inflected form */
	String baseGender = "";
	/** The person of the inflected form */
	String basePerson = "";
	/**
	 * The type of the inflected form - it is relevant for several part of speech
	 */
	String type = "";
	/**
	 * The h value of the inflected form - can be definited or not, can be
	 * subcoordinated for participle and modals
	 */
	String hAttribute = "";
	/** The dotted form of the lexicon item relevant to the inflected form */
	String dottedLexiconItem = "";
	/**
	 * The polarity of the inflected form - relevant only for several part of speech
	 * such as copula
	 */
	String polarity = "";
	/**
	 * The value of the inflected form - relevant in case of acronym - holds the
	 * expansion, in case of numeral it will hold the value of the numeral for
	 * example yer-> 10
	 */
	String value = "";
	/**
	 * The mood is relevant only for participle - in case of passive it is true
	 */
	String mood = "";
	/**
	 * The prefix per entry is relevant only for pronouns - it enables to define per
	 * lexicon entry which entry can be added cklm
	 */
	char prefixPerEntry = 'u';
	/**
	 * nouns and adjectives which are originated from foreign source like ainplcih
	 * are indicated
	 */
	int foreign = -1;

	/**
	 * @return
	 */
	public String getBaseGender() {
		return baseGender;
	}

	/**
	 * @return
	 */
	public String getBaseLexiconPointer() {
		return baseLexiconPointer;
	}

	public String getBaseAlternatePointer() {
		return baseAlternatePointer;
	}

	/**
	 * @return
	 */
	public String getBaseNumber() {
		return baseNumber;
	}

	/**
	 * @return
	 */
	public String getBasePerson() {
		return basePerson;
	}

	/**
	 * @return
	 */
	public String getBasePos() {
		return basePos;
	}

	/**
	 * @return
	 */
	public String getBaseTransliteratedLItem() {
		return baseTransliteratedLItem;
	}

	/**
	 * @return
	 */
	public String getBaseUndottedLItem() {
		return baseUndottedLItem;
	}

	/**
	 * @return
	 */
	public String getBinyan() {
		return binyan;
	}

	/**
	 * @return
	 */
	public String getDottedLexiconItem() {
		return dottedLexiconItem;
	}

	/**
	 * @return
	 */
	public int getForeign() {
		return foreign;
	}

	/**
	 * @return
	 */
	public String getHAttribute() {
		return hAttribute;
	}

	/**
	 * @return
	 */
	public String getMood() {
		return mood;
	}

	/**
	 * @return
	 */
	public String getPGN() {
		return PGN;
	}

	/**
	 * @return
	 */
	public String getPolarity() {
		return polarity;
	}

	/**
	 * @return
	 */
	public char getPrefixPerEntry() {
		return prefixPerEntry;
	}

	/**
	 * @return
	 */
	public String getRegister() {
		return register;
	}

	/**
	 * @return
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * @return
	 */
	public String getSpelling() {
		return spelling;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return
	 */
	public String getSuffixFunction() {
		return suffixFunction;
	}

	/**
	 * @return
	 */
	public String getSurface() {
		return surface;
	}

	/**
	 * @return
	 */
	public String getTense() {
		return tense;
	}

	/**
	 * @return
	 */
	public String getTransliterated() {
		return transliterated;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param baseGender
	 */
	public void setBaseGender(String baseGender) {
		this.baseGender = baseGender;
	}

	/**
	 * @param baseLexiconPointer
	 */
	public void setBaseLexiconPointer(String baseLexiconPointer) {
		this.baseLexiconPointer = baseLexiconPointer;
	}

	public void setBaseAlternatePointer(String baseAlternatePointer) {
		this.baseAlternatePointer = baseAlternatePointer;
	}

	/**
	 * @param baseNumber
	 */
	public void setBaseNumber(String baseNumber) {
		this.baseNumber = baseNumber;
	}

	/**
	 * @param basePerson
	 */
	public void setBasePerson(String basePerson) {
		this.basePerson = basePerson;
	}

	/**
	 * @param basePos
	 */
	public void setBasePos(String basePos) {
		this.basePos = basePos;
	}

	/**
	 * @param baseTransliteratedLItem
	 */
	public void setBaseTransliteratedLItem(String baseTransliteratedLItem) {
		this.baseTransliteratedLItem = baseTransliteratedLItem;
	}

	/**
	 * @param baseUndottedLItem
	 */
	public void setBaseUndottedLItem(String baseUndottedLItem) {
		this.baseUndottedLItem = baseUndottedLItem;
	}

	/**
	 * @param binyan
	 */
	public void setBinyan(String binyan) {
		this.binyan = binyan;
	}

	/**
	 * @param dottedLexiconItem
	 */
	public void setDottedLexiconItem(String dottedLexiconItem) {
		this.dottedLexiconItem = dottedLexiconItem;
	}

	/**
	 * @param foreign
	 */
	public void setForeign(int foreign) {
		this.foreign = foreign;
	}

	/**
	 * @param attribute
	 */
	public void setHAttribute(String attribute) {
		hAttribute = attribute;
	}

	/**
	 * @param mood
	 */
	public void setMood(String mood) {
		this.mood = mood;
	}

	/**
	 * @param pgn
	 */
	public void setPGN(String pgn) {
		PGN = pgn;
	}

	/**
	 * @param polarity
	 */
	public void setPolarity(String polarity) {
		this.polarity = polarity;
	}

	/**
	 * @param prefixPerEntry
	 */
	public void setPrefixPerEntry(char prefixPerEntry) {
		this.prefixPerEntry = prefixPerEntry;
	}

	/**
	 * @param register
	 */
	public void setRegister(String register) {
		this.register = register;
	}

	/**
	 * @param root
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	/**
	 * @param spelling
	 */
	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param suffixFunction
	 */
	public void setSuffixFunction(String suffixFunction) {
		this.suffixFunction = suffixFunction;
	}

	/**
	 * @param surface
	 */
	public void setSurface(String surface) {
		this.surface = surface;
	}

	/**
	 * @param tense
	 */
	public void setTense(String tense) {
		this.tense = tense;
	}

	/**
	 * @param transliterated
	 */
	public void setTransliterated(String transliterated) {
		this.transliterated = transliterated;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "transliterated: " + transliterated + "\nsurface: " + surface + "\nbasePos: " + basePos
				+ "\nbaseLexiconPointer: " + baseLexiconPointer + "\nbaseAlternatePointer: " + baseAlternatePointer
				+ "\nbaseUndottedLItem: " + baseUndottedLItem + "\nbaseTransliteratedLItem: " + baseTransliteratedLItem
				+ "\nregister: " + register + "\nspelling: " + spelling + "\nstatus: " + status + "\nsuffixFunction: "
				+ suffixFunction + "\nPGN: " + PGN + "\nbinyan: " + binyan + "\ntense: " + tense + "\nroot: " + root
				+ "\nbaseNumber: " + baseNumber + "\nbaseGender: " + baseGender + "\nbasePerson: " + basePerson + "\ntype: "
				+ type + "\nhAttribute: " + hAttribute + "\ndottedLexiconItem: " + dottedLexiconItem + "\npolarity: "
				+ polarity + "\nvalue: " + value + "\nmood: " + mood + "\nprefixPerEntry: " + prefixPerEntry + "\nforeign: "
				+ foreign;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((PGN == null) ? 0 : PGN.hashCode());
		result = prime * result + ((baseGender == null) ? 0 : baseGender.hashCode());
		result = prime * result + ((baseLexiconPointer == null) ? 0 : baseLexiconPointer.hashCode());
		result = prime * result + ((baseNumber == null) ? 0 : baseNumber.hashCode());
		result = prime * result + ((basePerson == null) ? 0 : basePerson.hashCode());
		result = prime * result + ((basePos == null) ? 0 : basePos.hashCode());
		result = prime * result + ((baseTransliteratedLItem == null) ? 0 : baseTransliteratedLItem.hashCode());
		result = prime * result + ((baseUndottedLItem == null) ? 0 : baseUndottedLItem.hashCode());
		result = prime * result + ((binyan == null) ? 0 : binyan.hashCode());
		result = prime * result + ((dottedLexiconItem == null) ? 0 : dottedLexiconItem.hashCode());
		result = prime * result + foreign;
		result = prime * result + ((hAttribute == null) ? 0 : hAttribute.hashCode());
		result = prime * result + ((mood == null) ? 0 : mood.hashCode());
		result = prime * result + ((polarity == null) ? 0 : polarity.hashCode());
		result = prime * result + prefixPerEntry;
		result = prime * result + ((register == null) ? 0 : register.hashCode());
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		result = prime * result + ((spelling == null) ? 0 : spelling.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((suffixFunction == null) ? 0 : suffixFunction.hashCode());
		result = prime * result + ((surface == null) ? 0 : surface.hashCode());
		result = prime * result + ((tense == null) ? 0 : tense.hashCode());
		result = prime * result + ((transliterated == null) ? 0 : transliterated.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBInflectionsRecord other = (DBInflectionsRecord) obj;
		if (PGN == null) {
			if (other.PGN != null)
				return false;
		} else if (!PGN.equals(other.PGN))
			return false;
		if (baseGender == null) {
			if (other.baseGender != null)
				return false;
		} else if (!baseGender.equals(other.baseGender))
			return false;
		if (baseLexiconPointer == null) {
			if (other.baseLexiconPointer != null)
				return false;
		} else if (!baseLexiconPointer.equals(other.baseLexiconPointer))
			return false;
		if (baseNumber == null) {
			if (other.baseNumber != null)
				return false;
		} else if (!baseNumber.equals(other.baseNumber))
			return false;
		if (basePerson == null) {
			if (other.basePerson != null)
				return false;
		} else if (!basePerson.equals(other.basePerson))
			return false;
		if (basePos == null) {
			if (other.basePos != null)
				return false;
		} else if (!basePos.equals(other.basePos))
			return false;
		if (baseTransliteratedLItem == null) {
			if (other.baseTransliteratedLItem != null)
				return false;
		} else if (!baseTransliteratedLItem.equals(other.baseTransliteratedLItem))
			return false;
		if (baseUndottedLItem == null) {
			if (other.baseUndottedLItem != null)
				return false;
		} else if (!baseUndottedLItem.equals(other.baseUndottedLItem))
			return false;
		if (binyan == null) {
			if (other.binyan != null)
				return false;
		} else if (!binyan.equals(other.binyan))
			return false;
		if (dottedLexiconItem == null) {
			if (other.dottedLexiconItem != null)
				return false;
		} else if (!dottedLexiconItem.equals(other.dottedLexiconItem))
			return false;
		if (foreign != other.foreign)
			return false;
		if (hAttribute == null) {
			if (other.hAttribute != null)
				return false;
		} else if (!hAttribute.equals(other.hAttribute))
			return false;
		if (mood == null) {
			if (other.mood != null)
				return false;
		} else if (!mood.equals(other.mood))
			return false;
		if (polarity == null) {
			if (other.polarity != null)
				return false;
		} else if (!polarity.equals(other.polarity))
			return false;
		if (prefixPerEntry != other.prefixPerEntry)
			return false;
		if (register == null) {
			if (other.register != null)
				return false;
		} else if (!register.equals(other.register))
			return false;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		if (spelling == null) {
			if (other.spelling != null)
				return false;
		} else if (!spelling.equals(other.spelling))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (suffixFunction == null) {
			if (other.suffixFunction != null)
				return false;
		} else if (!suffixFunction.equals(other.suffixFunction))
			return false;
		if (surface == null) {
			if (other.surface != null)
				return false;
		} else if (!surface.equals(other.surface))
			return false;
		if (tense == null) {
			if (other.tense != null)
				return false;
		} else if (!tense.equals(other.tense))
			return false;
		if (transliterated == null) {
			if (other.transliterated != null)
				return false;
		} else if (!transliterated.equals(other.transliterated))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
