/*
 * Created on 10/10/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.lexicon.dbUtils;

/**
 * @author dalia bojan
 * @version 1.0
 * 
 */
public class PrefixRecord {
	String prefix = "";
	String description = "";
	boolean definiteArticleTag;
	boolean defArtHE;
	boolean relHE;
	boolean adverbKAF;
	boolean subConOrRelSHIN;
	boolean tempSubConKAFSHIN;
	boolean tempSubConMEMSHIN;
	boolean tempSubConLAMEDKAFSHIN;
	boolean tempSubConBETSHIN;
	boolean relativizerTag;
	boolean temporalSubConjTag;
	boolean subordinatingConjunctionTag;
	boolean prefPartUnit;
	boolean prepBET;
	boolean prepKAF;
	boolean prepLAMED;
	boolean prepMEM;
	boolean prepositionTag;
	boolean conjunctionTag;

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return Returns the prefix.
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @return Returns the adverbKAF.
	 */
	public boolean isAdverbKAF() {
		return adverbKAF;
	}

	/**
	 * @return Returns the conjunctionTag.
	 */
	public boolean isConjunctionTag() {
		return conjunctionTag;
	}

	/**
	 * @return Returns the defArtHE.
	 */
	public boolean isDefArtHE() {
		return defArtHE;
	}

	/**
	 * @return Returns the definiteArticleTag.
	 */
	public boolean isDefiniteArticleTag() {
		return definiteArticleTag;
	}

	/**
	 * @return Returns the prefPartUnit.
	 */
	public boolean isPrefPartUnit() {
		return prefPartUnit;
	}

	/**
	 * @return Returns the prepBET.
	 */
	public boolean isPrepBET() {
		return prepBET;
	}

	/**
	 * @return Returns the prepKAF.
	 */
	public boolean isPrepKAF() {
		return prepKAF;
	}

	/**
	 * @return Returns the prepLAMED.
	 */
	public boolean isPrepLAMED() {
		return prepLAMED;
	}

	/**
	 * @return Returns the prepMEM.
	 */
	public boolean isPrepMEM() {
		return prepMEM;
	}

	/**
	 * @return Returns the prepositionTag.
	 */
	public boolean isPrepositionTag() {
		return prepositionTag;
	}

	/**
	 * @return Returns the relativizerTag.
	 */
	public boolean isRelativizerTag() {
		return relativizerTag;
	}

	/**
	 * @return Returns the relHE.
	 */
	public boolean isRelHE() {
		return relHE;
	}

	/**
	 * @return Returns the subConOrRelSHIN.
	 */
	public boolean isSubConOrRelSHIN() {
		return subConOrRelSHIN;
	}

	/**
	 * @return Returns the subordinatingConjunctionTag.
	 */
	public boolean isSubordinatingConjunctionTag() {
		return subordinatingConjunctionTag;
	}

	/**
	 * @return Returns the temporalSubConjTag.
	 */
	public boolean isTemporalSubConjTag() {
		return temporalSubConjTag;
	}

	/**
	 * @return Returns the tempSubConBETSHIN.
	 */
	public boolean isTempSubConBETSHIN() {
		return tempSubConBETSHIN;
	}

	/**
	 * @return Returns the tempSubConKAFSHIN.
	 */
	public boolean isTempSubConKAFSHIN() {
		return tempSubConKAFSHIN;
	}

	/**
	 * @return Returns the tempSubConLAMEDKAFSHIN.
	 */
	public boolean isTempSubConLAMEDKAFSHIN() {
		return tempSubConLAMEDKAFSHIN;
	}

	/**
	 * @return Returns the tempSubConMEMSHIN.
	 */
	public boolean isTempSubConMEMSHIN() {
		return tempSubConMEMSHIN;
	}

	/**
	 * @param adverbKAF
	 *           The adverbKAF to set.
	 */
	public void setAdverbKAF(boolean adverbKAF) {
		this.adverbKAF = adverbKAF;
	}

	/**
	 * @param conjunctionTag
	 *           The conjunctionTag to set.
	 */
	public void setConjunctionTag(boolean conjunctionTag) {
		this.conjunctionTag = conjunctionTag;
	}

	/**
	 * @param defArtHE
	 *           The defArtHE to set.
	 */
	public void setDefArtHE(boolean defArtHE) {
		this.defArtHE = defArtHE;
	}

	/**
	 * @param definiteArticleTag
	 *           The definiteArticleTag to set.
	 */
	public void setDefiniteArticleTag(boolean definiteArticleTag) {
		this.definiteArticleTag = definiteArticleTag;
	}

	/**
	 * @param description
	 *           The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param prefix
	 *           The prefix to set.
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @param prefPartUnit
	 *           The prefPartUnit to set.
	 */
	public void setPrefPartUnit(boolean prefPartUnit) {
		this.prefPartUnit = prefPartUnit;
	}

	/**
	 * @param prepBET
	 *           The prepBET to set.
	 */
	public void setPrepBET(boolean prepBET) {
		this.prepBET = prepBET;
	}

	/**
	 * @param prepKAF
	 *           The prepKAF to set.
	 */
	public void setPrepKAF(boolean prepKAF) {
		this.prepKAF = prepKAF;
	}

	/**
	 * @param prepLAMED
	 *           The prepLAMED to set.
	 */
	public void setPrepLAMED(boolean prepLAMED) {
		this.prepLAMED = prepLAMED;
	}

	/**
	 * @param prepMEM
	 *           The prepMEM to set.
	 */
	public void setPrepMEM(boolean prepMEM) {
		this.prepMEM = prepMEM;
	}

	/**
	 * @param prepositionTag
	 *           The prepositionTag to set.
	 */
	public void setPrepositionTag(boolean prepositionTag) {
		this.prepositionTag = prepositionTag;
	}

	/**
	 * @param relativizerTag
	 *           The relativizerTag to set.
	 */
	public void setRelativizerTag(boolean relativizerTag) {
		this.relativizerTag = relativizerTag;
	}

	/**
	 * @param relHE
	 *           The relHE to set.
	 */
	public void setRelHE(boolean relHE) {
		this.relHE = relHE;
	}

	/**
	 * @param subConOrRelSHIN
	 *           The subConOrRelSHIN to set.
	 */
	public void setSubConOrRelSHIN(boolean subConOrRelSHIN) {
		this.subConOrRelSHIN = subConOrRelSHIN;
	}

	/**
	 * @param subordinatingConjunctionTag
	 *           The subordinatingConjunctionTag to set.
	 */
	public void setSubordinatingConjunctionTag(boolean subordinatingConjunctionTag) {
		this.subordinatingConjunctionTag = subordinatingConjunctionTag;
	}

	/**
	 * @param temporalSubConjTag
	 *           The temporalSubConjTag to set.
	 */
	public void setTemporalSubConjTag(boolean temporalSubConjTag) {
		this.temporalSubConjTag = temporalSubConjTag;
	}

	/**
	 * @param tempSubConBETSHIN
	 *           The tempSubConBETSHIN to set.
	 */
	public void setTempSubConBETSHIN(boolean tempSubConBETSHIN) {
		this.tempSubConBETSHIN = tempSubConBETSHIN;
	}

	/**
	 * @param tempSubConKAFSHIN
	 *           The tempSubConKAFSHIN to set.
	 */
	public void setTempSubConKAFSHIN(boolean tempSubConKAFSHIN) {
		this.tempSubConKAFSHIN = tempSubConKAFSHIN;
	}

	/**
	 * @param tempSubConLAMEDKAFSHIN
	 *           The tempSubConLAMEDKAFSHIN to set.
	 */
	public void setTempSubConLAMEDKAFSHIN(boolean tempSubConLAMEDKAFSHIN) {
		this.tempSubConLAMEDKAFSHIN = tempSubConLAMEDKAFSHIN;
	}

	/**
	 * @param tempSubConMEMSHIN
	 *           The tempSubConMEMSHIN to set.
	 */
	public void setTempSubConMEMSHIN(boolean tempSubConMEMSHIN) {
		this.tempSubConMEMSHIN = tempSubConMEMSHIN;
	}
}