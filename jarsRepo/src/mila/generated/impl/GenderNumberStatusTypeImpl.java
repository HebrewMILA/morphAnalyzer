//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.14 at 03:41:34 PM IDT 
//

package mila.generated.impl;

public class GenderNumberStatusTypeImpl implements
		mila.generated.GenderNumberStatusType, com.sun.xml.bind.JAXBObject,
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
				_Status = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
			} catch (java.lang.Exception e) {
				handleParseConversionException(e);
			}
		}

		private void eatText2(final java.lang.String value)
				throws org.xml.sax.SAXException {
			try {
				_Gender = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
			} catch (java.lang.Exception e) {
				handleParseConversionException(e);
			}
		}

		private void eatText3(final java.lang.String value)
				throws org.xml.sax.SAXException {
			try {
				_Number = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
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
				case 6:
					if (("status" == ___local) && ("" == ___uri)) {
						state = 7;
						return;
					}
					state = 9;
					continue outer;
				case 0:
					if (("gender" == ___local) && ("" == ___uri)) {
						state = 1;
						return;
					}
					state = 3;
					continue outer;
				case 9:
					revertToParentFromEnterAttribute(___uri, ___local, ___qname);
					return;
				case 3:
					if (("number" == ___local) && ("" == ___uri)) {
						state = 4;
						return;
					}
					state = 6;
					continue outer;
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
				case 6:
					attIdx = context.getAttribute("", "status");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 9;
						eatText1(v);
						continue outer;
					}
					state = 9;
					continue outer;
				case 0:
					attIdx = context.getAttribute("", "gender");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 3;
						eatText2(v);
						continue outer;
					}
					state = 3;
					continue outer;
				case 9:
					revertToParentFromEnterElement(___uri, ___local, ___qname,
							__atts);
					return;
				case 3:
					attIdx = context.getAttribute("", "number");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 6;
						eatText3(v);
						continue outer;
					}
					state = 6;
					continue outer;
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
					case 6:
						attIdx = context.getAttribute("", "status");
						if (attIdx >= 0) {
							final java.lang.String v = context
									.eatAttribute(attIdx);
							state = 9;
							eatText1(v);
							continue outer;
						}
						state = 9;
						continue outer;
					case 4:
						state = 5;
						eatText3(value);
						return;
					case 0:
						attIdx = context.getAttribute("", "gender");
						if (attIdx >= 0) {
							final java.lang.String v = context
									.eatAttribute(attIdx);
							state = 3;
							eatText2(v);
							continue outer;
						}
						state = 3;
						continue outer;
					case 9:
						revertToParentFromText(value);
						return;
					case 3:
						attIdx = context.getAttribute("", "number");
						if (attIdx >= 0) {
							final java.lang.String v = context
									.eatAttribute(attIdx);
							state = 6;
							eatText3(v);
							continue outer;
						}
						state = 6;
						continue outer;
					case 1:
						state = 2;
						eatText2(value);
						return;
					case 7:
						state = 8;
						eatText1(value);
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
				case 6:
					attIdx = context.getAttribute("", "status");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 9;
						eatText1(v);
						continue outer;
					}
					state = 9;
					continue outer;
				case 2:
					if (("gender" == ___local) && ("" == ___uri)) {
						state = 3;
						return;
					}
					break;
				case 0:
					attIdx = context.getAttribute("", "gender");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 3;
						eatText2(v);
						continue outer;
					}
					state = 3;
					continue outer;
				case 9:
					revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
					return;
				case 5:
					if (("number" == ___local) && ("" == ___uri)) {
						state = 6;
						return;
					}
					break;
				case 3:
					attIdx = context.getAttribute("", "number");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 6;
						eatText3(v);
						continue outer;
					}
					state = 6;
					continue outer;
				case 8:
					if (("status" == ___local) && ("" == ___uri)) {
						state = 9;
						return;
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
				case 6:
					attIdx = context.getAttribute("", "status");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 9;
						eatText1(v);
						continue outer;
					}
					state = 9;
					continue outer;
				case 0:
					attIdx = context.getAttribute("", "gender");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 3;
						eatText2(v);
						continue outer;
					}
					state = 3;
					continue outer;
				case 9:
					revertToParentFromLeaveElement(___uri, ___local, ___qname);
					return;
				case 3:
					attIdx = context.getAttribute("", "number");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 6;
						eatText3(v);
						continue outer;
					}
					state = 6;
					continue outer;
				}
				super.leaveElement(___uri, ___local, ___qname);
				break;
			}
		}

		@Override
		public java.lang.Object owner() {
			return mila.generated.impl.GenderNumberStatusTypeImpl.this;
		}

	}

	protected java.lang.String _Status;
	protected java.lang.String _Gender;
	protected java.lang.String _Number;
	public final static java.lang.Class version = (mila.generated.impl.JAXBVersion.class);

	private static com.sun.msv.grammar.Grammar schemaFragment;

	private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
		return (mila.generated.GenderNumberStatusType.class);
	}

	@Override
	public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
		if (schemaFragment == null) {
			schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer
					.deserialize(("\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
							+ "n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
							+ "mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
							+ "on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
							+ "expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceEx"
							+ "p\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000"
							+ "\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameCl"
							+ "ass;xq\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000\u001bco"
							+ "m.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/dat"
							+ "atype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/Str"
							+ "ingPair;xq\u0000~\u0000\u0003ppsr\u0000)com.sun.msv.datatype.xsd.EnumerationFace"
							+ "t\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0006valuest\u0000\u000fLjava/util/Set;xr\u00009com.sun.msv.datat"
							+ "ype.xsd.DataTypeWithValueConstraintFacet\"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com.s"
							+ "un.msv.datatype.xsd.DataTypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFi"
							+ "xedZ\u0000\u0012needValueCheckFlagL\u0000\bbaseTypet\u0000)Lcom/sun/msv/datatype/"
							+ "xsd/XSDatatypeImpl;L\u0000\fconcreteTypet\u0000\'Lcom/sun/msv/datatype/x"
							+ "sd/ConcreteType;L\u0000\tfacetNamet\u0000\u0012Ljava/lang/String;xr\u0000\'com.sun"
							+ ".msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000"
							+ "~\u0000\u0018L\u0000\btypeNameq\u0000~\u0000\u0018L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd"
							+ "/WhiteSpaceProcessor;xpt\u0000\u0000t\u0000\nGenderTypesr\u00005com.sun.msv.datat"
							+ "ype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.m"
							+ "sv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0000\u0000sr\u0000\"com.su"
							+ "n.msv.datatype.xsd.TokenType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datat"
							+ "ype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv"
							+ ".datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.da"
							+ "tatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0019t\u0000 http://www.w3.org"
							+ "/2001/XMLSchemat\u0000\u0005tokenq\u0000~\u0000 \u0001q\u0000~\u0000%t\u0000\u000benumerationsr\u0000\u0011java.uti"
							+ "l.HashSet\u00baD\u0085\u0095\u0096\u00b8\u00b74\u0003\u0000\u0000xpw\f\u0000\u0000\u0000\u0010?@\u0000\u0000\u0000\u0000\u0000\u0005t\u0000\u000bunspecifiedt\u0000\bfeminin"
							+ "et\u0000\nirrelevantt\u0000\u0016masculine and femininet\u0000\tmasculinexsr\u00000com."
							+ "sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000"
							+ "\u0003ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~"
							+ "\u0000\u0018L\u0000\fnamespaceURIq\u0000~\u0000\u0018xpq\u0000~\u0000\u001dq\u0000~\u0000\u001csr\u0000#com.sun.msv.grammar.Si"
							+ "mpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0018L\u0000\fnamespaceURIq\u0000~\u0000"
							+ "\u0018xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0006genderq\u0000~\u0000"
							+ "\u001csr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
							+ "\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\f\u0001q\u0000~\u00009sq\u0000~\u0000\u0007ppsq\u0000~\u0000\tq\u0000~\u0000\rpsq\u0000~\u0000\u000eppsq\u0000~\u0000\u0012q\u0000~\u0000"
							+ "\u001ct\u0000\nNumberTypeq\u0000~\u0000 \u0000\u0000q\u0000~\u0000%q\u0000~\u0000%q\u0000~\u0000(sq\u0000~\u0000)w\f\u0000\u0000\u0000\u0010?@\u0000\u0000\u0000\u0000\u0000\u0006t\u0000\u000bu"
							+ "nspecifiedt\u0000\u0013singular and pluralt\u0000\u0004dualt\u0000\u000fdual and pluralt\u0000\b"
							+ "singulart\u0000\u0006pluralxq\u0000~\u00001sq\u0000~\u00002q\u0000~\u0000?q\u0000~\u0000\u001csq\u0000~\u00004t\u0000\u0006numberq\u0000~\u0000\u001cq"
							+ "\u0000~\u00009sq\u0000~\u0000\u0007ppsq\u0000~\u0000\tq\u0000~\u0000\rpsq\u0000~\u0000\u000eppsq\u0000~\u0000\u0012q\u0000~\u0000\u001ct\u0000\nStatusTypeq\u0000~\u0000"
							+ " \u0000\u0000q\u0000~\u0000%q\u0000~\u0000%q\u0000~\u0000(sq\u0000~\u0000)w\f\u0000\u0000\u0000\u0010?@\u0000\u0000\u0000\u0000\u0000\u0004t\u0000\u000bunspecifiedt\u0000\tconst"
							+ "ructt\u0000\babsolutet\u0000\u0016absolute and constructxq\u0000~\u00001sq\u0000~\u00002q\u0000~\u0000Nq\u0000~"
							+ "\u0000\u001csq\u0000~\u00004t\u0000\u0006statusq\u0000~\u0000\u001cq\u0000~\u00009sr\u0000\"com.sun.msv.grammar.Expressio"
							+ "nPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Expressio"
							+ "nPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$Cl"
							+ "osedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/"
							+ "sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u0005\u0001pq\u0000~\u0000;q\u0000~\u0000\u0005q\u0000~\u0000\bq\u0000~\u0000\u0006q"
							+ "\u0000~\u0000Jx"));
		}
		return new com.sun.msv.verifier.regexp.REDocumentDeclaration(
				schemaFragment);
	}

	@Override
	public mila.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(
			mila.generated.impl.runtime.UnmarshallingContext context) {
		return new mila.generated.impl.GenderNumberStatusTypeImpl.Unmarshaller(
				context);
	}

	@Override
	public java.lang.String getGender() {
		if (_Gender == null) {
			return "masculine";
		} else {
			return _Gender;
		}
	}

	@Override
	public java.lang.String getNumber() {
		if (_Number == null) {
			return "singular";
		} else {
			return _Number;
		}
	}

	@Override
	public java.lang.Class getPrimaryInterface() {
		return (mila.generated.GenderNumberStatusType.class);
	}

	@Override
	public java.lang.String getStatus() {
		if (_Status == null) {
			return "absolute and construct";
		} else {
			return _Status;
		}
	}

	@Override
	public void serializeAttributes(mila.generated.impl.runtime.XMLSerializer context)
			throws org.xml.sax.SAXException {
		if (_Gender != null) {
			context.startAttribute("", "gender");
			try {
				context.text(_Gender, "Gender");
			} catch (java.lang.Exception e) {
				mila.generated.impl.runtime.Util.handlePrintConversionException(
						this, e, context);
			}
			context.endAttribute();
		}
		if (_Number != null) {
			context.startAttribute("", "number");
			try {
				context.text(_Number, "Number");
			} catch (java.lang.Exception e) {
				mila.generated.impl.runtime.Util.handlePrintConversionException(
						this, e, context);
			}
			context.endAttribute();
		}
		if (_Status != null) {
			context.startAttribute("", "status");
			try {
				context.text(_Status, "Status");
			} catch (java.lang.Exception e) {
				mila.generated.impl.runtime.Util.handlePrintConversionException(
						this, e, context);
			}
			context.endAttribute();
		}
	}

	@Override
	public void serializeBody(mila.generated.impl.runtime.XMLSerializer context)
			throws org.xml.sax.SAXException {
	}

	@Override
	public void serializeURIs(mila.generated.impl.runtime.XMLSerializer context)
			throws org.xml.sax.SAXException {
	}

	@Override
	public void setGender(java.lang.String value) {
		_Gender = value;
	}

	@Override
	public void setNumber(java.lang.String value) {
		_Number = value;
	}

	@Override
	public void setStatus(java.lang.String value) {
		_Status = value;
	}

}
