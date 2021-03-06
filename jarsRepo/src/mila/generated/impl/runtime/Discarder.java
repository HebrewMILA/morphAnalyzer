//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.14 at 03:41:34 PM IDT 
//

package mila.generated.impl.runtime;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * UnmarshallingEventHandler implementation that discards the whole sub-tree.
 * 
 * @author Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
class Discarder implements UnmarshallingEventHandler {

	private final UnmarshallingContext context;

	// nest level of elements.
	private int depth = 0;

	public Discarder(UnmarshallingContext _ctxt) {
		this.context = _ctxt;
	}

	@Override
	public void enterAttribute(String uri, String local, String qname)
			throws SAXException {
	}

	@Override
	public void enterElement(String uri, String local, String qname,
			Attributes atts) throws SAXException {
		depth++;
	}

	@Override
	public void leaveAttribute(String uri, String local, String qname)
			throws SAXException {
	}

	@Override
	public void leaveChild(int nextState) throws SAXException {
	}

	@Override
	public void leaveElement(String uri, String local, String qname)
			throws SAXException {
		depth--;
		if (depth == 0)
			context.popContentHandler();
	}

	@Override
	public Object owner() {
		return null;
	}

	@Override
	public void text(String s) throws SAXException {
	}

}
