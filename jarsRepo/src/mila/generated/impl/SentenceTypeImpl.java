//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.14 at 03:41:34 PM IDT 
//

package mila.generated.impl;

public class SentenceTypeImpl implements mila.generated.SentenceType,
		com.sun.xml.bind.JAXBObject,
		mila.generated.impl.runtime.UnmarshallableObject,
		mila.generated.impl.runtime.XMLSerializable,
		mila.generated.impl.runtime.ValidatableObject {

	public class Unmarshaller extends
			mila.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl {

		public Unmarshaller(mila.generated.impl.runtime.UnmarshallingContext context) {
			super(context, "----------");
		}

		protected Unmarshaller(
				mila.generated.impl.runtime.UnmarshallingContext context,
				int startState) {
			this(context);
			state = startState;
		}

		private void eatText1(final java.lang.String value)
				throws org.xml.sax.SAXException {
			try {
				_TakenFrom = com.sun.xml.bind.WhiteSpaceProcessor
						.collapse(value);
			} catch (java.lang.Exception e) {
				handleParseConversionException(e);
			}
		}

		private void eatText2(final java.lang.String value)
				throws org.xml.sax.SAXException {
			try {
				_Id = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
			} catch (java.lang.Exception e) {
				handleParseConversionException(e);
			}
		}

		@Override
		public void enterAttribute(java.lang.String ___uri,
				java.lang.String ___local, java.lang.String ___qname)
				throws org.xml.sax.SAXException {
			int attIdx;
			outer: while (true) {
				switch (state) {
				case 3:
					if (("takenFrom" == ___local) && ("" == ___uri)) {
						state = 4;
						return;
					}
					state = 6;
					continue outer;
				case 9:
					revertToParentFromEnterAttribute(___uri, ___local, ___qname);
					return;
				case 7:
					if (("id" == ___local) && ("" == ___uri)) {
						_getToken().add(
								spawnChildFromEnterAttribute(
										(mila.generated.impl.TokenTypeImpl.class),
										8, ___uri, ___local, ___qname));
						return;
					}
					break;
				case 0:
					if (("id" == ___local) && ("" == ___uri)) {
						state = 1;
						return;
					}
					break;
				}
				super.enterAttribute(___uri, ___local, ___qname);
				break;
			}
		}

		@Override
		public void enterElement(java.lang.String ___uri,
				java.lang.String ___local, java.lang.String ___qname,
				org.xml.sax.Attributes __atts) throws org.xml.sax.SAXException {
			int attIdx;
			outer: while (true) {
				switch (state) {
				case 3:
					attIdx = context.getAttribute("", "takenFrom");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 6;
						eatText1(v);
						continue outer;
					}
					state = 6;
					continue outer;
				case 6:
					if (("token" == ___local) && ("" == ___uri)) {
						context.pushAttributes(__atts, false);
						state = 7;
						return;
					}
					break;
				case 9:
					if (("token" == ___local) && ("" == ___uri)) {
						context.pushAttributes(__atts, false);
						state = 7;
						return;
					}
					revertToParentFromEnterElement(___uri, ___local, ___qname,
							__atts);
					return;
				case 7:
					attIdx = context.getAttribute("", "id");
					if (attIdx >= 0) {
						context.consumeAttribute(attIdx);
						context.getCurrentHandler().enterElement(___uri,
								___local, ___qname, __atts);
						return;
					}
					break;
				case 0:
					attIdx = context.getAttribute("", "id");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 3;
						eatText2(v);
						continue outer;
					}
					break;
				}
				super.enterElement(___uri, ___local, ___qname, __atts);
				break;
			}
		}

		@Override
		public void handleText(final java.lang.String value)
				throws org.xml.sax.SAXException {
			int attIdx;
			outer: while (true) {
				try {
					switch (state) {
					case 3:
						attIdx = context.getAttribute("", "takenFrom");
						if (attIdx >= 0) {
							final java.lang.String v = context
									.eatAttribute(attIdx);
							state = 6;
							eatText1(v);
							continue outer;
						}
						state = 6;
						continue outer;
					case 4:
						state = 5;
						eatText1(value);
						return;
					case 9:
						revertToParentFromText(value);
						return;
					case 7:
						attIdx = context.getAttribute("", "id");
						if (attIdx >= 0) {
							context.consumeAttribute(attIdx);
							context.getCurrentHandler().text(value);
							return;
						}
						break;
					case 0:
						attIdx = context.getAttribute("", "id");
						if (attIdx >= 0) {
							final java.lang.String v = context
									.eatAttribute(attIdx);
							state = 3;
							eatText2(v);
							continue outer;
						}
						break;
					case 1:
						state = 2;
						eatText2(value);
						return;
					}
				} catch (java.lang.RuntimeException e) {
					handleUnexpectedTextException(value, e);
				}
				break;
			}
		}

		@Override
		public void leaveAttribute(java.lang.String ___uri,
				java.lang.String ___local, java.lang.String ___qname)
				throws org.xml.sax.SAXException {
			int attIdx;
			outer: while (true) {
				switch (state) {
				case 5:
					if (("takenFrom" == ___local) && ("" == ___uri)) {
						state = 6;
						return;
					}
					break;
				case 3:
					attIdx = context.getAttribute("", "takenFrom");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 6;
						eatText1(v);
						continue outer;
					}
					state = 6;
					continue outer;
				case 2:
					if (("id" == ___local) && ("" == ___uri)) {
						state = 3;
						return;
					}
					break;
				case 9:
					revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
					return;
				case 7:
					attIdx = context.getAttribute("", "id");
					if (attIdx >= 0) {
						context.consumeAttribute(attIdx);
						context.getCurrentHandler().leaveAttribute(___uri,
								___local, ___qname);
						return;
					}
					break;
				case 0:
					attIdx = context.getAttribute("", "id");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 3;
						eatText2(v);
						continue outer;
					}
					break;
				}
				super.leaveAttribute(___uri, ___local, ___qname);
				break;
			}
		}

		@Override
		public void leaveElement(java.lang.String ___uri,
				java.lang.String ___local, java.lang.String ___qname)
				throws org.xml.sax.SAXException {
			int attIdx;
			outer: while (true) {
				switch (state) {
				case 8:
					if (("token" == ___local) && ("" == ___uri)) {
						context.popAttributes();
						state = 9;
						return;
					}
					break;
				case 3:
					attIdx = context.getAttribute("", "takenFrom");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 6;
						eatText1(v);
						continue outer;
					}
					state = 6;
					continue outer;
				case 9:
					revertToParentFromLeaveElement(___uri, ___local, ___qname);
					return;
				case 7:
					attIdx = context.getAttribute("", "id");
					if (attIdx >= 0) {
						context.consumeAttribute(attIdx);
						context.getCurrentHandler().leaveElement(___uri,
								___local, ___qname);
						return;
					}
					break;
				case 0:
					attIdx = context.getAttribute("", "id");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 3;
						eatText2(v);
						continue outer;
					}
					break;
				}
				super.leaveElement(___uri, ___local, ___qname);
				break;
			}
		}

		@Override
		public java.lang.Object owner() {
			return mila.generated.impl.SentenceTypeImpl.this;
		}

	}

	protected com.sun.xml.bind.util.ListImpl _Token;
	protected java.lang.String _TakenFrom;
	protected java.lang.String _Id;
	public final static java.lang.Class version = (mila.generated.impl.JAXBVersion.class);

	private static com.sun.msv.grammar.Grammar schemaFragment;

	private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
		return (mila.generated.SentenceType.class);
	}

	protected com.sun.xml.bind.util.ListImpl _getToken() {
		if (_Token == null) {
			_Token = new com.sun.xml.bind.util.ListImpl(
					new java.util.ArrayList());
		}
		return _Token;
	}

	@Override
	public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
		if (schemaFragment == null) {
			schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer
					.deserialize(("\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
							+ "n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
							+ "mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
							+ "on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
							+ "expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsr\u0000 com.sun.msv.grammar.OneOrMor"
							+ "eExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
							+ "\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern"
							+ "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000"
							+ "\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclare"
							+ "dAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\npp\u0000sr\u0000"
							+ "\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsq\u0000~\u0000\u0007sr\u0000\u0011j"
							+ "ava.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv.gramm"
							+ "ar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\u000bxq\u0000~\u0000\u0003"
							+ "q\u0000~\u0000\u0014psr\u00002com.sun.msv.grammar.Expression$AnyStringExpression"
							+ "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u0013\u0001q\u0000~\u0000\u0018sr\u0000 com.sun.msv.grammar.AnyName"
							+ "Class\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
							+ "xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000"
							+ "\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0019q\u0000~\u0000\u001esr\u0000#com.sun.msv.grammar.SimpleNameClass"
							+ "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq"
							+ "\u0000~\u0000 xq\u0000~\u0000\u001bt\u0000\u0013generated.TokenTypet\u0000+http://java.sun.com/jaxb/"
							+ "xjc/dummy-elementssq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0015q\u0000~\u0000\u0014psr\u0000\u001bcom.sun.msv.gramma"
							+ "r.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L"
							+ "\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003pp"
							+ "sr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun"
							+ ".msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.ms"
							+ "v.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.dataty"
							+ "pe.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000 L\u0000\btypeN"
							+ "ameq\u0000~\u0000 L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpace"
							+ "Processor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005c"
							+ "om.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
							+ "\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
							+ "\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000"
							+ "\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\t"
							+ "localNameq\u0000~\u0000 L\u0000\fnamespaceURIq\u0000~\u0000 xpq\u0000~\u00001q\u0000~\u00000sq\u0000~\u0000\u001ft\u0000\u0004typet"
							+ "\u0000)http://www.w3.org/2001/XMLSchema-instanceq\u0000~\u0000\u001esq\u0000~\u0000\u001ft\u0000\u0005tok"
							+ "ent\u0000\u0000sq\u0000~\u0000\u0015ppsq\u0000~\u0000&ppsr\u0000\"com.sun.msv.datatype.xsd.TokenType\u0000"
							+ "\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001"
							+ "Z\u0000\risAlwaysValidxq\u0000~\u0000+q\u0000~\u00000t\u0000\u0005tokenq\u0000~\u00004\u0001q\u0000~\u00006sq\u0000~\u00007q\u0000~\u0000Dq\u0000~"
							+ "\u00000sq\u0000~\u0000\u001ft\u0000\u0002idq\u0000~\u0000>sq\u0000~\u0000\u0010ppsq\u0000~\u0000\u0015q\u0000~\u0000\u0014pq\u0000~\u0000@sq\u0000~\u0000\u001ft\u0000\ttakenFro"
							+ "mq\u0000~\u0000>q\u0000~\u0000\u001esr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001"
							+ "L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash"
							+ ";xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c"
							+ "\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/"
							+ "ExpressionPool;xp\u0000\u0000\u0000\b\u0001pq\u0000~\u0000Hq\u0000~\u0000\u0012q\u0000~\u0000$q\u0000~\u0000\u0006q\u0000~\u0000\u000eq\u0000~\u0000\u0011q\u0000~\u0000\tq\u0000"
							+ "~\u0000\u0005x"));
		}
		return new com.sun.msv.verifier.regexp.REDocumentDeclaration(
				schemaFragment);
	}

	@Override
	public mila.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(
			mila.generated.impl.runtime.UnmarshallingContext context) {
		return new mila.generated.impl.SentenceTypeImpl.Unmarshaller(context);
	}

	@Override
	public java.lang.String getId() {
		return _Id;
	}

	@Override
	public java.lang.Class getPrimaryInterface() {
		return (mila.generated.SentenceType.class);
	}

	@Override
	public java.lang.String getTakenFrom() {
		return _TakenFrom;
	}

	@Override
	public java.util.List getToken() {
		return _getToken();
	}

	@Override
	public void serializeAttributes(mila.generated.impl.runtime.XMLSerializer context)
			throws org.xml.sax.SAXException {
		int idx1 = 0;
		final int len1 = ((_Token == null) ? 0 : _Token.size());
		context.startAttribute("", "id");
		try {
			context.text(_Id, "Id");
		} catch (java.lang.Exception e) {
			mila.generated.impl.runtime.Util.handlePrintConversionException(this, e,
					context);
		}
		context.endAttribute();
		if (_TakenFrom != null) {
			context.startAttribute("", "takenFrom");
			try {
				context.text(_TakenFrom, "TakenFrom");
			} catch (java.lang.Exception e) {
				mila.generated.impl.runtime.Util.handlePrintConversionException(
						this, e, context);
			}
			context.endAttribute();
		}
		while (idx1 != len1) {
			idx1 += 1;
		}
	}

	@Override
	public void serializeBody(mila.generated.impl.runtime.XMLSerializer context)
			throws org.xml.sax.SAXException {
		int idx1 = 0;
		final int len1 = ((_Token == null) ? 0 : _Token.size());
		while (idx1 != len1) {
			context.startElement("", "token");
			int idx_0 = idx1;
			context.childAsURIs(
					((com.sun.xml.bind.JAXBObject) _Token.get(idx_0++)),
					"Token");
			context.endNamespaceDecls();
			int idx_1 = idx1;
			context.childAsAttributes(
					((com.sun.xml.bind.JAXBObject) _Token.get(idx_1++)),
					"Token");
			context.endAttributes();
			context.childAsBody(
					((com.sun.xml.bind.JAXBObject) _Token.get(idx1++)), "Token");
			context.endElement();
		}
	}

	@Override
	public void serializeURIs(mila.generated.impl.runtime.XMLSerializer context)
			throws org.xml.sax.SAXException {
		int idx1 = 0;
		final int len1 = ((_Token == null) ? 0 : _Token.size());
		while (idx1 != len1) {
			idx1 += 1;
		}
	}

	@Override
	public void setId(java.lang.String value) {
		_Id = value;
	}

	@Override
	public void setTakenFrom(java.lang.String value) {
		_TakenFrom = value;
	}

}
