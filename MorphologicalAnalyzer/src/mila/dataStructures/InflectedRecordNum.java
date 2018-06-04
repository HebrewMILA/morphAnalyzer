package mila.dataStructures;

/**
 * 
 * InflectedRecordNum.java
 * Purpose: A class used as a data structure (record) to hold part of an entry from the inflections table/data file where the fields<bR>
 * are also found in DBInflectionsRecord but here they are coded in numbers - so that checking equality will be faster 
 * @author Dalia Bojan
 * @version     %G%
 */
import static mila.lexicon.analyse.Constants.*;

public class InflectedRecordNum {
	/** lexicon id of the lexicon entry relevant to the inflected form */
	int id = -1;
	int alternateId = -1;
	/** Hebrew form of the inflected form */
	String surface = "";
	/** transliterated form of the inflected form */
	String transliterated = "";
	/**
	 * transliterated form of the lexicon entry relevant to the inflected form
	 */
	String transliteratedLexiconItem = "";
	/** hebrew form of the lexicon entry relevant to the inflected form */
	String undottedLexiconItem = "";
	/**
	 * hebrew dotted form of the lexicon entry relevant to the inflected form
	 */
	String dottedLexiconItem = "";
	/** lexicon id of the lexicon entry relevant to the inflected form */
	int lexiconPointer = -1;
	/** part of speech code as defined in the constants class */
	ENUM_POS pos = null;
	/** output code as defined in the constants class */
	ENUM_OUTPUT_PATTERN outputPattern = null;
	int gender = -1;
	int number = -1;
	int person = -1;
	/** h attribute as defined in the constants class */
	ENUM_HATTRIBUTE hAttribute = null;
	/** status as defined in the constants class */
	ENUM_STATUS status = null;
	/** tense as defined in the constants class */
	ENUM_TENSE tense = null;
	int root = -1;
	/** binyan as defined in the constants class */
	ENUM_BINYAN binyan = null;
	int type = -1;
	int PGN = -1;
	/** suffix type as defined in the constants class */
	ENUM_SUFFIX_FUNCTION suffixFunction = null;
	int register = -1;
	int spelling = -1;
	int polarity = -1;
	int value = -1;
	int hebForeign = -1;
	char prefixPerEntry = '-';

	public ENUM_BINYAN getBinyan() {
		return binyan;
	}

	public String getDottedLexiconItem() {
		return dottedLexiconItem;
	}

	public int getGender() {
		return gender;
	}

	public ENUM_HATTRIBUTE getHAttribute() {
		return hAttribute;
	}

	public int getHebForeign() {
		return hebForeign;
	}

	public int getId() {
		return id;
	}

	public int getAlternateId() {
		return alternateId;
	}

	public int getLexiconPointer() {
		return lexiconPointer;
	}

	public int getNumber() {
		return number;
	}

	public ENUM_OUTPUT_PATTERN getOutputPattern() {
		return outputPattern;
	}

	public int getPerson() {
		return person;
	}

	public int getPGN() {
		return PGN;
	}

	public int getPolarity() {
		return polarity;
	}

	public ENUM_POS getPos() {
		return pos;
	}

	public char getPrefixPerEntry() {
		return prefixPerEntry;
	}

	public int getRegister() {
		return register;
	}

	public int getRoot() {
		return root;
	}

	public int getSpelling() {
		return spelling;
	}

	public ENUM_STATUS getStatus() {
		return status;
	}

	public ENUM_SUFFIX_FUNCTION getSuffixFunction() {
		return suffixFunction;
	}

	public String getSurface() {
		return surface;
	}

	public ENUM_TENSE getTense() {
		return tense;
	}

	public String getTransliterated() {
		return transliterated;
	}

	public String getTransliteratedLexiconItem() {
		return transliteratedLexiconItem;
	}

	public int getType() {
		return type;
	}

	public String getUndottedLexiconItem() {
		return undottedLexiconItem;
	}

	public int getValue() {
		return value;
	}

	public void setBinyan(ENUM_BINYAN binyan) {
		this.binyan = binyan;
	}

	public void setDottedLexiconItem(String dottedLexiconItem) {
		this.dottedLexiconItem = dottedLexiconItem;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setHAttribute(ENUM_HATTRIBUTE hAttributes) {
		this.hAttribute = hAttributes;
	}

	public void setHebForeign(int hebForeign) {
		this.hebForeign = hebForeign;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAlternateId(int alternateId) {
		this.alternateId = alternateId;
	}

	public void setLexiconPointer(int lexiconPointer) {
		this.lexiconPointer = lexiconPointer;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setOutputPattern(ENUM_OUTPUT_PATTERN outputPattern) {
		this.outputPattern = outputPattern;
	}

	public void setPerson(int person) {
		this.person = person;
	}

	public void setPGN(int pgn) {
		PGN = pgn;
	}

	public void setPolarity(int polarity) {
		this.polarity = polarity;
	}

	public void setPos(ENUM_POS pos) {
		this.pos = pos;
	}

	public void setPrefixPerEntry(char prefixPerEntry) {
		this.prefixPerEntry = prefixPerEntry;
	}

	public void setRegister(int register) {
		this.register = register;
	}

	public void setRoot(int root) {
		this.root = root;
	}

	public void setSpelling(int spelling) {
		this.spelling = spelling;
	}

	public void setStatus(ENUM_STATUS status) {
		this.status = status;
	}

	public void setSuffixFunction(ENUM_SUFFIX_FUNCTION suffixFunction) {
		this.suffixFunction = suffixFunction;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public void setTense(ENUM_TENSE tense) {
		this.tense = tense;
	}

	public void setTransliterated(String transliterated) {
		this.transliterated = transliterated;
	}

	public void setTransliteratedLexiconItem(String transliteratedLexiconItem) {
		this.transliteratedLexiconItem = transliteratedLexiconItem;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setUndottedLexiconItem(String undottedLexiconItem) {
		this.undottedLexiconItem = undottedLexiconItem;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
