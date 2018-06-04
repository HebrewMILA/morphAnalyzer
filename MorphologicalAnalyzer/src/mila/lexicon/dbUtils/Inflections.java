/*
 * Created on 07/07/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.lexicon.dbUtils;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mila.dataStructures.DBInflectionsRecord;

/**
 * @author daliabo
 */

@SuppressWarnings("unused")
public class Inflections extends Connected {

	private String surface;

	private String transliterated;

	private String register;

	private String spelling;

	private String basePos;

	private String baseTransliteratedLItem;

	private String baseUndottedLItem;

	private String baseLexiconPointer;

	private String baseAlternatePointer;

	private String binyan = "";

	private String root = "";

	private String tense = "";

	private String baseGender;

	private String baseNumber;

	private String basePerson;

	private String baseConjunctionType;

	private String basePronounType;

	private String baseNamedEntityType;

	private String suffixSurface;

	private String suffixTransliterated;

	private String suffixFunction;

	private String suffixPerson;

	private String suffixGender;

	private String suffixNumber;

	private String suffixStatus;

	private String PGN = "";

	private String type = "";

	private String value = "";

	private String baseDefinitness = "";

	private String prefixPerEntry = "unspecified";

	// private int baseId;

	// private String comment;

	PrintStream pOut = null; // declare a print stream object

	PrintStream pInflections = null; // declare a print stream object

	// ------------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method receives a transliterated form of a word and returns all
	 * inflections corresponding to the input i.e executes 'SELECT * FROM
	 * inflections WHERE transliterated=?' where ? = input.
	 * 
	 * @param input
	 *           - transliterated form of a word
	 * @return all inflections fitting to the input.
	 * @throws UnsupportedEncodingException
	 */
	public ArrayList<DBInflectionsRecord> get(String input) throws UnsupportedEncodingException {

		ArrayList<DBInflectionsRecord> result = new ArrayList<DBInflectionsRecord>();
		ResultSet rs = null;

		String sql = "SELECT * FROM inflections WHERE transliterated=?";

		try {
			rs = getData(sql, input);
			if (rs != null) {
				while (rs.next()) {
					DBInflectionsRecord inflectionsRecDB = new DBInflectionsRecord();
					inflectionsRecDB.setBaseLexiconPointer(rs.getString("baseLexiconPointer"));
					inflectionsRecDB.setBasePos(rs.getString("basePos"));
					inflectionsRecDB.setBaseTransliteratedLItem(rs.getString("baseTransliteratedLItem"));
					inflectionsRecDB.setRegister(rs.getString("register"));
					inflectionsRecDB.setSpelling(rs.getString("spelling"));
					inflectionsRecDB.setSuffixFunction(rs.getString("suffixFunction"));
					inflectionsRecDB.setStatus(rs.getString("suffixStatus"));
					inflectionsRecDB.setSurface(rs.getString("surface"));
					inflectionsRecDB.setTransliterated(rs.getString("transliterated"));
					inflectionsRecDB.setPGN(rs.getString("PGN"));
					inflectionsRecDB.setBaseUndottedLItem(rs.getString("baseUndottedLItem"));
					inflectionsRecDB.setBinyan(rs.getString("binyan"));
					inflectionsRecDB.setTense(rs.getString("tense"));
					inflectionsRecDB.setRoot(rs.getString("root"));
					inflectionsRecDB.setBaseNumber(rs.getString("baseNumber"));
					inflectionsRecDB.setBaseGender(rs.getString("baseGender"));
					inflectionsRecDB.setHAttribute(rs.getString("baseDefinitness"));
					inflectionsRecDB.setBasePerson(rs.getString("basePerson"));
					inflectionsRecDB.setType(rs.getString("type"));
					inflectionsRecDB.setDottedLexiconItem(rs.getString("dottedLexiconItem"));
					inflectionsRecDB.setPolarity(rs.getString("polarity"));
					inflectionsRecDB.setValue(rs.getString("value"));
					String foreign = rs.getString("hebForeign");
					if (foreign.length() > 0) {
						inflectionsRecDB.setForeign(Integer.parseInt(rs.getString("hebForeign")));
					}
					inflectionsRecDB.setPrefixPerEntry(rs.getString("prefix").charAt(0));
					inflectionsRecDB.setBaseAlternatePointer(rs.getString("baseAlternatePointer"));

					result.add(inflectionsRecDB);
				}
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// System.exit(1);
		} finally {
			releaseConnection();
		}

		return result;
	}

	/**
	 * @return Returns the baseConjunctionType.
	 */
	public String getBaseConjunctionType() {
		return baseConjunctionType;
	}

	/**
	 * @return Returns the baseDefinitness.
	 */
	public String getBaseDefinitness() {
		return baseDefinitness;
	}

	/**
	 * @return Returns the baseGender.
	 */
	public String getBaseGender() {
		return baseGender;
	}

	/**
	 * @return Returns the baseNamedEntityType.
	 */
	public String getBaseNamedEntityType() {
		return baseNamedEntityType;
	}

	/**
	 * @return Returns the baseNumber.
	 */
	public String getBaseNumber() {
		return baseNumber;
	}

	/**
	 * @return Returns the basePerson.
	 */
	public String getBasePerson() {
		return basePerson;
	}

	/**
	 * @return Returns the basePos.
	 */
	public String getBasePos() {
		return basePos;
	}

	/**
	 * @return Returns the basePronounType.
	 */
	public String getBasePronounType() {
		return basePronounType;
	}

	public String getBaseTransliteratedLItem() {
		return baseTransliteratedLItem;
	}

	/**
	 * @return Returns the baseundottedLItem.
	 */
	public String getBaseundottedLItem() {
		return baseUndottedLItem;
	}

	/**
	 * @return Returns the suffixFunction.
	 */
	public String getSuffixFunction() {
		return suffixFunction;
	}

	/**
	 * @return Returns the suffixGender.
	 */
	public String getSuffixGender() {
		return suffixGender;
	}

	/**
	 * @return Returns the suffixNumber.
	 */
	public String getSuffixNumber() {
		return suffixNumber;
	}

	/**
	 * @return Returns the suffixPerson.
	 */
	public String getSuffixPerson() {
		return suffixPerson;
	}

	/**
	 * @return Returns the suffixSurface.
	 */
	public String getSuffixSurface() {
		return suffixSurface;
	}

	/**
	 * @return Returns the suffixTransliterated.
	 */
	public String getSuffixTransliterated() {
		return suffixTransliterated;
	}

	/**
	 * @return Returns the surface.
	 */
	public String getSurface() {
		return surface;
	}

	/**
	 * @return Returns the transliterated.
	 */
	public String getTransliterated() {
		return transliterated;
	}

	/**
	 * @param baseConjunctionType
	 *           The baseConjunctionType to set.
	 */
	public void setBaseConjunctionType(String baseConjunctionType) {
		this.baseConjunctionType = baseConjunctionType;
	}

	/**
	 * @param baseDefinitness
	 *           The baseDefinitness to set.
	 */
	public void setBaseDefinitness(String baseDefinitness) {
		this.baseDefinitness = baseDefinitness;
	}

	/**
	 * @param baseGender
	 *           The baseGender to set.
	 */
	public void setBaseGender(String baseGender) {
		this.baseGender = baseGender;
	}

	/**
	 * @param baseNamedEntityType
	 *           The baseNamedEntityType to set.
	 */
	public void setBaseNamedEntityType(String baseNamedEntityType) {
		this.baseNamedEntityType = baseNamedEntityType;
	}

	/**
	 * @param baseNumber
	 *           The baseNumber to set.
	 */
	public void setBaseNumber(String baseNumber) {
		this.baseNumber = baseNumber;
	}

	/**
	 * @param basePerson
	 *           The basePerson to set.
	 */
	public void setBasePerson(String basePerson) {
		this.basePerson = basePerson;
	}

	/**
	 * @param basePos
	 *           The basePos to set.
	 */
	public void setBasePos(String basePos) {
		this.basePos = basePos;
	}

	/**
	 * @param basePronounType
	 *           The basePronounType to set.
	 */
	public void setBasePronounType(String basePronounType) {
		this.basePronounType = basePronounType;
	}

	/**
	 * @param baseTransliteratedLItem
	 *           The baseTransliteratedLItem to set.
	 */
	public void setBaseTransliteratedLItem(String baseTransliteratedLItem) {
		this.baseTransliteratedLItem = baseTransliteratedLItem;
	}

	/**
	 * @param baseundottedLItem
	 *           The baseundottedLItem to set.
	 */
	public void setBaseundottedLItem(String baseundottedLItem) {
		this.baseUndottedLItem = baseundottedLItem;
	}

	/**
	 * @param baseUndottedLItem
	 *           The baseUndottedLItem to set.
	 */
	public void setBaseUndottedLItem(String baseUndottedLItem) {
		this.baseUndottedLItem = baseUndottedLItem;
	}

	/**
	 * @param binyan
	 *           The binyan to set.
	 */
	public void setBinyan(String binyan) {
		this.binyan = binyan;
	}

	/**
	 * @param pgn
	 *           The pGN to set.
	 */
	public void setPGN(String pgn) {
		PGN = pgn;
	}

	/**
	 * @param script
	 *           The script to set.
	 */
	public void setRegister(String register) {
		this.register = register;
	}

	/**
	 * @param root
	 *           The root to set.
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	/**
	 * @param script
	 *           The script to set.
	 */
	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

	/**
	 * @param suffixFunction
	 *           The suffixFunction to set.
	 */
	public void setSuffixFunction(String suffixFunction) {
		this.suffixFunction = suffixFunction;
	}

	/**
	 * @param suffixGender
	 *           The suffixGender to set.
	 */
	public void setSuffixGender(String suffixGender) {
		this.suffixGender = suffixGender;
	}

	/**
	 * @param suffixNumber
	 *           The suffixNumber to set.
	 */
	public void setSuffixNumber(String suffixNumber) {
		this.suffixNumber = suffixNumber;
	}

	/**
	 * @param suffixPerson
	 *           The suffixPerson to set.
	 */
	public void setSuffixPerson(String suffixPerson) {
		this.suffixPerson = suffixPerson;
	}

	/**
	 * @param suffixStatus
	 *           The suffixStatus to set.
	 */
	public void setSuffixStatus(String suffixStatus) {
		this.suffixStatus = suffixStatus;
	}

	/**
	 * @param suffixSurface
	 *           The suffixSurface to set.
	 */
	public void setSuffixSurface(String suffixSurface) {
		this.suffixSurface = suffixSurface;
	}

	/**
	 * @param suffixTransliterated
	 *           The suffixTransliterated to set.
	 */
	public void setSuffixTransliterated(String suffixTransliterated) {
		this.suffixTransliterated = suffixTransliterated;
	}

	/**
	 * @param surface
	 *           The surface to set.
	 */
	public void setSurface(String surface) {
		this.surface = surface;
	}

	/**
	 * @param tense
	 *           The tense to set.
	 */
	public void setTense(String tense) {
		this.tense = tense;
	}

	/**
	 * @param transliterated
	 *           The transliterated to set.
	 */
	public void setTransliterated(String transliterated) {
		this.transliterated = transliterated;
	}

	/**
	 * @param type
	 *           The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

}
