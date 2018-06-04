package mila.lexicon.dbUtils;

public class MWErecord {
	private String aid;
	private String id;
	private String formerItemId;
	private String surface;
	private String transliterated;
	private String consecutive;
	private String lexiconId;
	private String transliteratedLexiconItem;
	private String dottedLexiconItem;
	private String undottedLexiconItem;
	private String mwTransliterated;
	private String mwUndotted;
	private String PGN;
	private String spelling;
	private String register;
	private String gender;
	private String number;
	private String definiteness;

	public String getAid() {
		return aid;
	}

	public String getConsecutive() {
		return consecutive;
	}

	public String getDefiniteness() {
		return definiteness;
	}

	public String getDottedLexiconItem() {
		return dottedLexiconItem;
	}

	public String getFormerItemId() {
		return formerItemId;
	}

	public String getGender() {
		return gender;
	}

	public String getId() {
		return id;
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

	public String getRegister() {
		return register;
	}

	public String getSpelling() {
		return spelling;
	}

	public String getSurface() {
		return surface;
	}

	public String getTransliterated() {
		return transliterated;
	}

	public String getTransliteratedLexiconItem() {
		return transliteratedLexiconItem;
	}

	public String getUndottedLexiconItem() {
		return undottedLexiconItem;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public void setConsecutive(String consecutive) {
		this.consecutive = consecutive;
	}

	public void setDefiniteness(String definiteness) {
		this.definiteness = definiteness;
	}

	public void setDottedLexiconItem(String dottedLexiconItem) {
		this.dottedLexiconItem = dottedLexiconItem;
	}

	public void setFormerItemId(String formerItemId) {
		this.formerItemId = formerItemId;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setRegister(String register) {
		this.register = register;
	}

	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public void setTransliterated(String transliterated) {
		this.transliterated = transliterated;
	}

	public void setTransliteratedLexiconItem(String transliteratedLexiconItem) {
		this.transliteratedLexiconItem = transliteratedLexiconItem;
	}

	public void setUndottedLexiconItem(String undottedLexiconItem) {
		this.undottedLexiconItem = undottedLexiconItem;
	}
}
