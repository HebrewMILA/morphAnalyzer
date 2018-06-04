//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.14 at 03:41:34 PM IDT 
//

package mila.generated.impl.runtime;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * Receives SAX2 events and send the equivalent events to
 * {@link com.sun.xml.bind.serializer.XMLSerializer}
 * 
 * @author Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public class ContentHandlerAdaptor implements ContentHandler {

	/** Stores newly declared prefix-URI mapping. */
	private final ArrayList prefixMap = new ArrayList();

	/** Events will be sent to this object. */
	private final XMLSerializer serializer;

	private final StringBuffer text = new StringBuffer();

	public ContentHandlerAdaptor(XMLSerializer _serializer) {
		this.serializer = _serializer;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		text.append(ch, start, length);
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		flushText();
		serializer.endElement();
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
	}

	private void flushText() throws SAXException {
		if (text.length() != 0) {
			serializer.text(text.toString(), null);
			text.setLength(0);
		}
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		text.append(ch, start, length);
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	@Override
	public void setDocumentLocator(Locator locator) {
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
	}

	@Override
	public void startDocument() throws SAXException {
		prefixMap.clear();
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {

		flushText();

		int len = atts.getLength();

		serializer.startElement(namespaceURI, localName);
		// declare namespace events
		for (int i = 0; i < len; i++) {
			String qname = atts.getQName(i);
			int idx = qname.indexOf(':');
			String prefix = (idx == -1) ? qname : qname.substring(0, idx);

			serializer.getNamespaceContext().declareNamespace(atts.getURI(i),
					prefix, true);
		}
		for (int i = 0; i < prefixMap.size(); i += 2) {
			String prefix = (String) prefixMap.get(i);
			serializer.getNamespaceContext()
					.declareNamespace((String) prefixMap.get(i + 1), prefix,
							prefix.length() != 0);
		}

		serializer.endNamespaceDecls();
		// fire attribute events
		for (int i = 0; i < len; i++) {
			serializer.startAttribute(atts.getURI(i), atts.getLocalName(i));
			serializer.text(atts.getValue(i), null);
			serializer.endAttribute();
		}
		prefixMap.clear();
		serializer.endAttributes();
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		prefixMap.add(prefix);
		prefixMap.add(uri);
	}

}
