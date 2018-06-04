/*
 * Created on 05/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.mw;

import mila.lexicon.analyse.TextOutput;
import mila.lexicon.dbUtils.MWEinflectionsRecord;
import mila.lexicon.dbUtils.PrefixRecord;

/**
 * @author daliabo
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */

public class MWTextOutput extends TextOutput {

	static public void buildMWXMLOutput(MWCreateCorpusXML createXML, MWEinflectionsRecord mweinflectionsRec) {
		String mweId = mweinflectionsRec.getMweId();
		String mweBaseFormId = mweinflectionsRec.getMweBaseFormId();
		String surface = mweinflectionsRec.getSurface();
		String transliterated = mweinflectionsRec.getTransliterated();
		String pos = mweinflectionsRec.getPos();
		String type = mweinflectionsRec.getType();
		boolean prefix = mweinflectionsRec.isPrefix();
		createXML.createMWEAnalysis("", transliterated, surface, mweBaseFormId, pos, mweId, type, prefix, "unspecified");
	}

	static public void buildPrefixMWXMLOutput(PrefixRecord pr, MWCreateCorpusXML createXML,
			MWEinflectionsRecord mweinflectionsRec) {
		String definiteness = "";
		String mweId = mweinflectionsRec.getMweId();
		String mweBaseFormId = mweinflectionsRec.getMweBaseFormId();
		String surface = mweinflectionsRec.getSurface();
		String transliterated = mweinflectionsRec.getTransliterated();
		String pos = mweinflectionsRec.getPos();
		String type = mweinflectionsRec.getType();
		boolean prefix = mweinflectionsRec.isPrefix();
		if (pr.isDefiniteArticleTag())
			definiteness = "true";
		else
			definiteness = "false";
		String description = pr.getDescription();
		createXML.createMWEAnalysis(description, transliterated, surface, mweBaseFormId, pos, mweId, type, prefix,
				definiteness);
	}

}
