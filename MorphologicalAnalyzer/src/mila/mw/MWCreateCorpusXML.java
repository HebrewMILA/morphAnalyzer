/*
 * Created on 08/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.mw;

import java.net.InetAddress;
import java.util.Calendar;

import javax.xml.bind.JAXBException;

import mila.corpus.CreateCorpusXML;
import mila.generated.BaseType;
import mila.generated.MWEType;

/**
 * @author daliabo
 */
public class MWCreateCorpusXML extends CreateCorpusXML {
	public MWCreateCorpusXML() {
		super();
	}

	public MWCreateCorpusXML(String outputFile) {
		this.outputFile = outputFile;
	}

	public void createMWEAnalysis(String description, String transliteratedLexiocnItem, String hebWord,
			String lexiconPointer, String pos, String mweid, String type, boolean prefix, String definiteness) {
		try {
			analysisCounter++;
			analysis = objFactory.createAnalysisType();
			analysis.setId(String.valueOf(analysisCounter));
			BaseType base = objFactory.createBaseType();
			if (!description.equals("")) {
				setPrefix(description);
			}

			setBase(base, transliteratedLexiocnItem, hebWord, "", hebWord);
			MWEType mwe1 = objFactory.createMWEType();
			mwe1.setConsecutive("true");
			if (mweid.length() == 1 && mweid.charAt(0) == '1') {
				mwe1.setPos(pos);
			}
			mwe1.setId(mweid);
			if (!definiteness.equals("unspecified")) {
				mwe1.setDefiniteness(definiteness);
			}
			mwe1.setMultiWordPrefixExist(prefix);
			if (pos.equalsIgnoreCase("propername") && type.charAt(0) != 'u') {
				mwe1.setType(type);
			}
			base.setMWE(mwe1);
			analysis.setBase(base);
			token.getAnalysis().add(analysis);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void createXMLDoc() {
		super.createXMLDoc();
		corpus.setName("Analysis Results (" + getHostname() + ") @ " + Calendar.getInstance().getTime());
	}

	private static String getHostname() {
		String hostname = "unknown.unknown";
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (final Throwable t) {

		}
		return hostname;
	}

	protected void setBase(BaseType base, String transliteratedLexiocnItem, String lexiconItem, String lexiconPointer,
			String dottedLexiconItem) {
		base.setTransliteratedLexiconItem(transliteratedLexiocnItem);
		base.setLexiconItem(lexiconItem);
		if (lexiconPointer.length() > 0) {
			base.setLexiconPointer(lexiconPointer);
		}
		base.setDottedLexiconItem(null);
	}

}
