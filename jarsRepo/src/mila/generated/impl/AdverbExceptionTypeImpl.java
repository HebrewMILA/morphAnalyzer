//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.01 at 02:23:12 PM IST 
//

package mila.generated.impl;

public class AdverbExceptionTypeImpl implements mila.generated.AdverbExceptionType,
		com.sun.xml.bind.JAXBObject,
		mila.generated.impl.runtime.UnmarshallableObject,
		mila.generated.impl.runtime.XMLSerializable,
		mila.generated.impl.runtime.ValidatableObject {

	public class Unmarshaller extends
			mila.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl {

		public Unmarshaller(mila.generated.impl.runtime.UnmarshallingContext context) {
			super(context, "-----------------");
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
				_PossessiveSuffix = com.sun.xml.bind.WhiteSpaceProcessor
						.collapse(value);
			} catch (java.lang.Exception e) {
				handleParseConversionException(e);
			}
		}

		private void eatText2(final java.lang.String value)
				throws org.xml.sax.SAXException {
			try {
				_Dotted = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
			} catch (java.lang.Exception e) {
				handleParseConversionException(e);
			}
		}

		private void eatText3(final java.lang.String value)
				throws org.xml.sax.SAXException {
			try {
				_Script = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
			} catch (java.lang.Exception e) {
				handleParseConversionException(e);
			}
		}

		private void eatText4(final java.lang.String value)
				throws org.xml.sax.SAXException {
			try {
				_Transliterated = com.sun.xml.bind.WhiteSpaceProcessor
						.collapse(value);
			} catch (java.lang.Exception e) {
				handleParseConversionException(e);
			}
		}

		private void eatText5(final java.lang.String value)
				throws org.xml.sax.SAXException {
			try {
				_Undotted = com.sun.xml.bind.WhiteSpaceProcessor
						.collapse(value);
			} catch (java.lang.Exception e) {
				handleParseConversionException(e);
			}
		}

		private void eatText6(final java.lang.String value)
				throws org.xml.sax.SAXException {
			try {
				_Value = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
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
				case 16:
					revertToParentFromEnterAttribute(___uri, ___local, ___qname);
					return;
				case 3:
					if (("possessiveSuffix" == ___local) && ("" == ___uri)) {
						state = 4;
						return;
					}
					state = 6;
					continue outer;
				case 0:
					if (("dotted" == ___local) && ("" == ___uri)) {
						state = 1;
						return;
					}
					state = 3;
					continue outer;
				case 6:
					if (("script" == ___local) && ("" == ___uri)) {
						state = 7;
						return;
					}
					state = 9;
					continue outer;
				case 9:
					if (("transliterated" == ___local) && ("" == ___uri)) {
						state = 10;
						return;
					}
					break;
				case 12:
					if (("undotted" == ___local) && ("" == ___uri)) {
						state = 13;
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
				case 16:
					revertToParentFromEnterElement(___uri, ___local, ___qname,
							__atts);
					return;
				case 3:
					attIdx = context.getAttribute("", "possessiveSuffix");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 6;
						eatText1(v);
						continue outer;
					}
					state = 6;
					continue outer;
				case 0:
					attIdx = context.getAttribute("", "dotted");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 3;
						eatText2(v);
						continue outer;
					}
					state = 3;
					continue outer;
				case 6:
					attIdx = context.getAttribute("", "script");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 9;
						eatText3(v);
						continue outer;
					}
					state = 9;
					continue outer;
				case 9:
					attIdx = context.getAttribute("", "transliterated");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 12;
						eatText4(v);
						continue outer;
					}
					break;
				case 12:
					attIdx = context.getAttribute("", "undotted");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 15;
						eatText5(v);
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
					case 13:
						state = 14;
						eatText5(value);
						return;
					case 16:
						revertToParentFromText(value);
						return;
					case 4:
						state = 5;
						eatText1(value);
						return;
					case 3:
						attIdx = context.getAttribute("", "possessiveSuffix");
						if (attIdx >= 0) {
							final java.lang.String v = context
									.eatAttribute(attIdx);
							state = 6;
							eatText1(v);
							continue outer;
						}
						state = 6;
						continue outer;
					case 1:
						state = 2;
						eatText2(value);
						return;
					case 15:
						state = 16;
						eatText6(value);
						return;
					case 10:
						state = 11;
						eatText4(value);
						return;
					case 0:
						attIdx = context.getAttribute("", "dotted");
						if (attIdx >= 0) {
							final java.lang.String v = context
									.eatAttribute(attIdx);
							state = 3;
							eatText2(v);
							continue outer;
						}
						state = 3;
						continue outer;
					case 6:
						attIdx = context.getAttribute("", "script");
						if (attIdx >= 0) {
							final java.lang.String v = context
									.eatAttribute(attIdx);
							state = 9;
							eatText3(v);
							continue outer;
						}
						state = 9;
						continue outer;
					case 9:
						attIdx = context.getAttribute("", "transliterated");
						if (attIdx >= 0) {
							final java.lang.String v = context
									.eatAttribute(attIdx);
							state = 12;
							eatText4(v);
							continue outer;
						}
						break;
					case 7:
						state = 8;
						eatText3(value);
						return;
					case 12:
						attIdx = context.getAttribute("", "undotted");
						if (attIdx >= 0) {
							final java.lang.String v = context
									.eatAttribute(attIdx);
							state = 15;
							eatText5(v);
							continue outer;
						}
						break;
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
					if (("possessiveSuffix" == ___local) && ("" == ___uri)) {
						state = 6;
						return;
					}
					break;
				case 8:
					if (("script" == ___local) && ("" == ___uri)) {
						state = 9;
						return;
					}
					break;
				case 11:
					if (("transliterated" == ___local) && ("" == ___uri)) {
						state = 12;
						return;
					}
					break;
				case 16:
					revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
					return;
				case 3:
					attIdx = context.getAttribute("", "possessiveSuffix");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 6;
						eatText1(v);
						continue outer;
					}
					state = 6;
					continue outer;
				case 0:
					attIdx = context.getAttribute("", "dotted");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 3;
						eatText2(v);
						continue outer;
					}
					state = 3;
					continue outer;
				case 6:
					attIdx = context.getAttribute("", "script");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 9;
						eatText3(v);
						continue outer;
					}
					state = 9;
					continue outer;
				case 9:
					attIdx = context.getAttribute("", "transliterated");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 12;
						eatText4(v);
						continue outer;
					}
					break;
				case 2:
					if (("dotted" == ___local) && ("" == ___uri)) {
						state = 3;
						return;
					}
					break;
				case 14:
					if (("undotted" == ___local) && ("" == ___uri)) {
						state = 15;
						return;
					}
					break;
				case 12:
					attIdx = context.getAttribute("", "undotted");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 15;
						eatText5(v);
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
				case 16:
					revertToParentFromLeaveElement(___uri, ___local, ___qname);
					return;
				case 3:
					attIdx = context.getAttribute("", "possessiveSuffix");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 6;
						eatText1(v);
						continue outer;
					}
					state = 6;
					continue outer;
				case 0:
					attIdx = context.getAttribute("", "dotted");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 3;
						eatText2(v);
						continue outer;
					}
					state = 3;
					continue outer;
				case 6:
					attIdx = context.getAttribute("", "script");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 9;
						eatText3(v);
						continue outer;
					}
					state = 9;
					continue outer;
				case 9:
					attIdx = context.getAttribute("", "transliterated");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 12;
						eatText4(v);
						continue outer;
					}
					break;
				case 12:
					attIdx = context.getAttribute("", "undotted");
					if (attIdx >= 0) {
						final java.lang.String v = context.eatAttribute(attIdx);
						state = 15;
						eatText5(v);
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
			return mila.generated.impl.AdverbExceptionTypeImpl.this;
		}

	}

	protected java.lang.String _Value;
	protected java.lang.String _PossessiveSuffix;
	protected java.lang.String _Undotted;
	protected java.lang.String _Dotted;
	protected java.lang.String _Script;
	protected java.lang.String _Transliterated;
	public final static java.lang.Class version = (mila.generated.impl.JAXBVersion.class);

	private static com.sun.msv.grammar.Grammar schemaFragment;

	private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
		return (mila.generated.AdverbExceptionType.class);
	}

	@Override
	public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
		if (schemaFragment == null) {
			schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer
					.deserialize(("\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
							+ "n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
							+ "mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
							+ "on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
							+ "expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsr\u0000\u001bcom."
							+ "sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datat"
							+ "ype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/Strin"
							+ "gPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv.datatype.xsd.TokenType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
							+ "\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAl"
							+ "waysValidxr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000"
							+ "\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr"
							+ "\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnames"
							+ "paceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0013L\u0000\nwhiteSpacet\u0000."
							+ "Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://ww"
							+ "w.w3.org/2001/XMLSchemat\u0000\u0005tokensr\u00005com.sun.msv.datatype.xsd."
							+ "WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datat"
							+ "ype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.gra"
							+ "mmar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom."
							+ "sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0013L\u0000\fnamesp"
							+ "aceURIq\u0000~\u0000\u0013xpq\u0000~\u0000\u0017q\u0000~\u0000\u0016sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000"
							+ "\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
							+ "\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;x"
							+ "q\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000pq\u0000~\u0000\rsr\u0000#co"
							+ "m.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000"
							+ "\u0013L\u0000\fnamespaceURIq\u0000~\u0000\u0013xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000"
							+ "\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0006dottedt\u0000\u0000sr\u00000com.sun.msv.grammar.Expression$Epsilo"
							+ "nExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000$\u0001q\u0000~\u0000,sq\u0000~\u0000\u001fppsq\u0000~\u0000!q\u0000~\u0000%p"
							+ "sq\u0000~\u0000\nppsr\u0000)com.sun.msv.datatype.xsd.EnumerationFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
							+ "\u0001\u0002\u0000\u0001L\u0000\u0006valuest\u0000\u000fLjava/util/Set;xr\u00009com.sun.msv.datatype.xsd."
							+ "DataTypeWithValueConstraintFacet\"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com.sun.msv.d"
							+ "atatype.xsd.DataTypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012ne"
							+ "edValueCheckFlagL\u0000\bbaseTypet\u0000)Lcom/sun/msv/datatype/xsd/XSDa"
							+ "tatypeImpl;L\u0000\fconcreteTypet\u0000\'Lcom/sun/msv/datatype/xsd/Concr"
							+ "eteType;L\u0000\tfacetNameq\u0000~\u0000\u0013xq\u0000~\u0000\u0012q\u0000~\u0000*t\u0000\u0007PGNTypeq\u0000~\u0000\u001a\u0000\u0000q\u0000~\u0000\u0015q\u0000"
							+ "~\u0000\u0015t\u0000\u000benumerationsr\u0000\u0011java.util.HashSet\u00baD\u0085\u0095\u0096\u00b8\u00b74\u0003\u0000\u0000xpw\f\u0000\u0000\u0000 ?@\u0000"
							+ "\u0000\u0000\u0000\u0000\u0013t\u0000\t123p/M/Sgt\u0000\u00072p/M/Plt\u0000\u00073p/M/Sgt\u0000\b1p/MF/Sgt\u0000\t123p/M/Pl"
							+ "t\u0000\b2p/MF/Plt\u0000\u00072p/M/Sgt\u0000\u00071p/F/Sgt\u0000\b3p/MF/Plt\u0000\u00073p/F/Plt\u0000\u00071p/M/"
							+ "Sgt\u0000\u000bunspecifiedt\u0000\u00072p/F/Plt\u0000\t123p/F/Plt\u0000\u00073p/M/Plt\u0000\u00073p/F/Sgt\u0000"
							+ "\t123p/F/Sgt\u0000\b1p/MF/Plt\u0000\u00072p/F/Sgxq\u0000~\u0000\u001csq\u0000~\u0000\u001dq\u0000~\u00008q\u0000~\u0000*sq\u0000~\u0000&t"
							+ "\u0000\u0010possessiveSuffixq\u0000~\u0000*q\u0000~\u0000,sq\u0000~\u0000\u001fppsq\u0000~\u0000!q\u0000~\u0000%psq\u0000~\u0000\nppsq\u0000~"
							+ "\u00001q\u0000~\u0000*t\u0000\nScriptTypeq\u0000~\u0000\u001a\u0000\u0000q\u0000~\u0000\u0015q\u0000~\u0000\u0015q\u0000~\u00009sq\u0000~\u0000:w\f\u0000\u0000\u0000\u0010?@\u0000\u0000\u0000\u0000"
							+ "\u0000\u0004t\u0000\u0004typot\u0000\u0006formalt\u0000\u0005slangt\u0000\ncolloquialxq\u0000~\u0000\u001csq\u0000~\u0000\u001dq\u0000~\u0000Vq\u0000~\u0000"
							+ "*sq\u0000~\u0000&t\u0000\u0006scriptq\u0000~\u0000*q\u0000~\u0000,sq\u0000~\u0000!ppq\u0000~\u0000\rsq\u0000~\u0000&t\u0000\u000etransliterat"
							+ "edq\u0000~\u0000*sq\u0000~\u0000!ppq\u0000~\u0000\rsq\u0000~\u0000&t\u0000\bundottedq\u0000~\u0000*sr\u0000\"com.sun.msv.gr"
							+ "ammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/gr"
							+ "ammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.Ex"
							+ "pressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000"
							+ "\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\b\u0001pq\u0000~\u0000\tq"
							+ "\u0000~\u0000Rq\u0000~\u0000\u0006q\u0000~\u0000\bq\u0000~\u0000 q\u0000~\u0000\u0005q\u0000~\u0000\u0007q\u0000~\u0000.x"));
		}
		return new com.sun.msv.verifier.regexp.REDocumentDeclaration(
				schemaFragment);
	}

	@Override
	public mila.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(
			mila.generated.impl.runtime.UnmarshallingContext context) {
		return new mila.generated.impl.AdverbExceptionTypeImpl.Unmarshaller(context);
	}

	@Override
	public java.lang.String getDotted() {
		if (_Dotted == null) {
			return "";
		} else {
			return _Dotted;
		}
	}

	@Override
	public java.lang.String getPossessiveSuffix() {
		if (_PossessiveSuffix == null) {
			return "unspecified";
		} else {
			return _PossessiveSuffix;
		}
	}

	@Override
	public java.lang.Class getPrimaryInterface() {
		return (mila.generated.AdverbExceptionType.class);
	}

	@Override
	public java.lang.String getScript() {
		if (_Script == null) {
			return "formal";
		} else {
			return _Script;
		}
	}

	@Override
	public java.lang.String getTransliterated() {
		return _Transliterated;
	}

	@Override
	public java.lang.String getUndotted() {
		return _Undotted;
	}

	@Override
	public java.lang.String getValue() {
		return _Value;
	}

	@Override
	public void serializeAttributes(mila.generated.impl.runtime.XMLSerializer context)
			throws org.xml.sax.SAXException {
		if (_Dotted != null) {
			context.startAttribute("", "dotted");
			try {
				context.text(_Dotted, "Dotted");
			} catch (java.lang.Exception e) {
				mila.generated.impl.runtime.Util.handlePrintConversionException(
						this, e, context);
			}
			context.endAttribute();
		}
		if (_PossessiveSuffix != null) {
			context.startAttribute("", "possessiveSuffix");
			try {
				context.text(_PossessiveSuffix, "PossessiveSuffix");
			} catch (java.lang.Exception e) {
				mila.generated.impl.runtime.Util.handlePrintConversionException(
						this, e, context);
			}
			context.endAttribute();
		}
		if (_Script != null) {
			context.startAttribute("", "script");
			try {
				context.text(_Script, "Script");
			} catch (java.lang.Exception e) {
				mila.generated.impl.runtime.Util.handlePrintConversionException(
						this, e, context);
			}
			context.endAttribute();
		}
		context.startAttribute("", "transliterated");
		try {
			context.text(_Transliterated, "Transliterated");
		} catch (java.lang.Exception e) {
			mila.generated.impl.runtime.Util.handlePrintConversionException(this, e,
					context);
		}
		context.endAttribute();
		context.startAttribute("", "undotted");
		try {
			context.text(_Undotted, "Undotted");
		} catch (java.lang.Exception e) {
			mila.generated.impl.runtime.Util.handlePrintConversionException(this, e,
					context);
		}
		context.endAttribute();
	}

	@Override
	public void serializeBody(mila.generated.impl.runtime.XMLSerializer context)
			throws org.xml.sax.SAXException {
		try {
			context.text(_Value, "Value");
		} catch (java.lang.Exception e) {
			mila.generated.impl.runtime.Util.handlePrintConversionException(this, e,
					context);
		}
	}

	@Override
	public void serializeURIs(mila.generated.impl.runtime.XMLSerializer context)
			throws org.xml.sax.SAXException {
	}

	@Override
	public void setDotted(java.lang.String value) {
		_Dotted = value;
	}

	@Override
	public void setPossessiveSuffix(java.lang.String value) {
		_PossessiveSuffix = value;
	}

	@Override
	public void setScript(java.lang.String value) {
		_Script = value;
	}

	@Override
	public void setTransliterated(java.lang.String value) {
		_Transliterated = value;
	}

	@Override
	public void setUndotted(java.lang.String value) {
		_Undotted = value;
	}

	@Override
	public void setValue(java.lang.String value) {
		_Value = value;
	}

}
