package mila.lexicon.dbUtils;

public class MWEinflectionsRecord {
	String id = "";
	String transliterated = "";
	String surface = "";
	String pos = "";
	String mweId = "";
	String mweBaseFormId = "";
	String type = "";
	String PGN = "";
	String spelling = "";
	String register = "";
	String definiteness = "";

	boolean prefix = false;

	public String getDefiniteness() {
		return definiteness;
	}

	public String getId() {
		return id;
	}

	public String getMweBaseFormId() {
		return mweBaseFormId;
	}

	public String getMweId() {
		return mweId;
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

	public String getSurface() {
		return surface;
	}

	public String getTransliterated() {
		return transliterated;
	}

	public String getType() {
		return type;
	}

	public boolean isPrefix() {
		return prefix;
	}

	public void setDefiniteness(String definiteness) {
		this.definiteness = definiteness;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMweBaseFormId(String mweBaseFormId) {
		this.mweBaseFormId = mweBaseFormId;
	}

	public void setMweId(String mweId) {
		this.mweId = mweId;
	}

	public void setPGN(String pgn) {
		PGN = pgn;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public void setPrefix(boolean prefix) {
		this.prefix = prefix;
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

	public void setType(String type) {
		this.type = type;
	}

}
