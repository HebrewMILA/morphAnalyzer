package mila.dataStructures;

import mila.lexicon.dbUtils.MWE1record;
import mila.lexicon.dbUtils.MWErecord;

/**
 * @author syjacob
 * 
 *         this class represmt a custom result set and is used in the
 *         postprocess to replace the resultSet that would have been returned if
 *         we used the mysql server.
 */
public class CustomResult {
	private String spelling;
	private String register;
	private String gender;
	private String number;
	private String definiteness;
	private String mwTransliterated;
	private String mwUndotted;
	// private String id;
	private String lexiconId;
	private String undottedLexiconItem;
	private String PGN;
	private String dottedLexiconItem;
	private String transliteratedLexiconItem;
	private String pos;
	private String type;

	public String getDefiniteness() {
		return definiteness;
	}

	public String getDottedLexiconItem() {
		return dottedLexiconItem;
	}

	public String getGender() {
		return gender;
	}

	public String getLexiconId() {
		return lexiconId;
	}

	public String getMwTransliterated() {
		return mwTransliterated;
	}

	public String getMwUndotted() {
		return mwUndotted;
	}

	public String getNumber() {
		return number;
	}

	public String getPGN() {
		return PGN;
	}

	public String getPos() {
		return pos;
	}

	public String getRegister() {
		return register;
	}

	public String getSpelling() {
		return spelling;
	}

	public String getTransliteratedLexiconItem() {
		return transliteratedLexiconItem;
	}

	public String getType() {
		return type;
	}

	public String getUndottedLexiconItem() {
		return undottedLexiconItem;
	}

	public void LoadRecord(MWE1record mwe1, MWErecord mwe) {
		definiteness = mwe.getDefiniteness();
		dottedLexiconItem = mwe.getDottedLexiconItem();
		gender = mwe.getGender();
		number = mwe.getNumber();
		definiteness = mwe.getDefiniteness();
		mwTransliterated = mwe.getMwTransliterated();
		mwUndotted = mwe.getMwUndotted();
		lexiconId = mwe.getLexiconId();
		undottedLexiconItem = mwe.getUndottedLexiconItem();
		PGN = mwe.getPGN();
		dottedLexiconItem = mwe.getDottedLexiconItem();
		transliteratedLexiconItem = mwe.getTransliteratedLexiconItem();
		register = mwe.getRegister();
		spelling = mwe.getSpelling();

		pos = mwe1.getPos();
		type = mwe1.getType();
	}

	public void Print() {
		System.out.println(spelling + ", " + register + ", " + gender + ", " + number + ", " + definiteness + ", "
				+ mwTransliterated + ", " + mwUndotted + ", " + lexiconId + ", " + ", " + undottedLexiconItem + ", " + PGN
				+ ", " + dottedLexiconItem + ", " + transliteratedLexiconItem + ", " + pos + ", " + type);
	}

	/*
	 * public void setId(String id) { this.id = id; } public String getId() { return
	 * id; }
	 */

	public void setDefiniteness(String definiteness) {
		this.definiteness = definiteness;
	}

	public void setDottedLexiconItem(String dottedLexiconItem) {
		this.dottedLexiconItem = dottedLexiconItem;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setLexiconId(String lexiconId) {
		this.lexiconId = lexiconId;
	}

	public void setMwTransliterated(String mwTransliterated) {
		this.mwTransliterated = mwTransliterated;
	}

	public void setMwUndotted(String mwUndotted) {
		this.mwUndotted = mwUndotted;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPGN(String pGN) {
		PGN = pGN;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

	public void setTransliteratedLexiconItem(String transliteratedLexiconItem) {
		this.transliteratedLexiconItem = transliteratedLexiconItem;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUndottedLexiconItem(String undottedLexiconItem) {
		this.undottedLexiconItem = undottedLexiconItem;
	}

}
