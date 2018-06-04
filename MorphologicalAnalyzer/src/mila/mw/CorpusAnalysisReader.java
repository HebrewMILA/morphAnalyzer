package mila.mw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import mila.generated.Corpus;
import mila.lexicon.analyse.Constants;
import mila.tools.api.MilaException;

/**
 * This shows how to use JAXB to unmarshal an xml file Then display the
 * information from the content tree
 */

public class CorpusAnalysisReader {
	/** Types defined in the automatically generated code of jaxb */
	public final static JAXBContext jc = acquireJAXBContext();
	private Corpus collection;

	public CorpusAnalysisReader(String xmlFile) throws FileNotFoundException {
		collection = parse(new FileInputStream(xmlFile));
	}

	public CorpusAnalysisReader(InputStream input) {
		collection = parse(input);
	}

	// -------------------------------------------------------------------------------------------------------------------------------
	@SuppressWarnings("deprecation")
	public static Corpus parse(InputStream input) {
		try {
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			unmarshaller.setValidating(false);
			return (Corpus) unmarshaller.unmarshal(input);
		} catch (JAXBException e) {
			throw new MilaException(e);
		}
	}

	public Corpus getCorpus() {
		return collection;
	}

	public void save(PrintWriter pw) {
		try {
			Marshaller marshaller = jc.createMarshaller();
			marshaller.marshal(collection, pw);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	public String save(String whereTo) {
		try {
			Marshaller marshaller = jc.createMarshaller();
			marshaller.marshal(collection, new FileOutputStream(whereTo));
			return whereTo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static JAXBContext acquireJAXBContext() {
		try {
			return JAXBContext.newInstance(Constants.JAXB_PACKAGE);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new MilaException(e);
		}
	}
}
