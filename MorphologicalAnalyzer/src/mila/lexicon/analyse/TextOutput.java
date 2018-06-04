/*
 * Created on 05/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.lexicon.analyse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import mila.corpus.CreateCorpusXML;
import mila.dataStructures.DBInflectionsRecord;
import mila.dataStructures.InflectedRecordNum;
import mila.lexicon.dbUtils.PrefixRecord;
import mila.lexicon.utils.Translate;
import static mila.lexicon.analyse.Constants.*;

/**
 * @author daliabo
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */

public class TextOutput {

	static public void buildSimpleXMLAnalysis(ENUM_OUTPUT_PATTERN outputPatterni, CreateCorpusXML createXML,
			String baseHebWord, int value) throws Exception {
		// System.out.println(baseHebWord);
		switch (outputPatterni) {
		case PUNCTUATION:
			createXML.createPunctuationAnalysis(baseHebWord);
			break;
		case HEBREWLETTER:
			createXML.createProperNameAnalysis("", "unspecified", "unspecified", "symbol", "", null, null, null,
					"unspecified", baseHebWord, "formal", "standard", null);
			break;
		case HEBREWDOTLETTER:
			createXML.createProperNameAnalysis("", "unspecified", "unspecified", "person", "", null, null, null,
					"unspecified", baseHebWord, "formal", "standard", null);
			break;
		case LITERAL_NUMBERS:
			createXML.createNumeralAnalysis("", null, null, null, "", "", "", baseHebWord, "", "", "", "",
					"literal number", "", "", "", "", "");
			break;
		case FOREIGN:
			createXML.createForeignAnalysis();
			break;
		case URL:
			createXML.createURLAnalysis();
			break;
		case TIME:
			createXML.createNumberExpressionAnalysis("time");
			break;
		case DATE:
			createXML.createNumberExpressionAnalysis("date");
			break;
		case GAMESCORE:
			createXML.createNumberExpressionAnalysis("gameScore");
			break;
		case TIMEGAMESCORE:
			createXML.createNumberExpressionAnalysis("timeGameScore");
			break;
		case NUMBERING:
			createXML.createNumberExpressionAnalysis("numbering");
			break;
		case UNSPECIFIED:
			createXML.createNumberExpressionAnalysis("unspecified");
			break;

		}

	}

	static public void buildXMLOutput(final DBInflectionsRecord input_inflectionRecDB,
			final InflectedRecordNum inflectionRecNum, final CreateCorpusXML createXML, final String hebWord)
			throws Exception {
		DBInflectionsRecord inflectionRecDB = input_inflectionRecDB;
		boolean webFlag = Data.webFlag;
		String transliteratedLexiconItem = "";
		String lexiconItem = inflectionRecDB.getBaseUndottedLItem();
		lexiconItem = URLDecoder.decode(lexiconItem, "UTF-8");

		if (!webFlag) {
			transliteratedLexiconItem = Translate.Heb2Eng(lexiconItem);
		} else
			transliteratedLexiconItem = inflectionRecDB.getBaseTransliteratedLItem();

		String lexiconPointer = inflectionRecDB.getBaseLexiconPointer();

		String register = inflectionRecDB.getRegister();
		String spelling = inflectionRecDB.getSpelling();
		String dottedLexiconItem = inflectionRecDB.getDottedLexiconItem();
		dottedLexiconItem = setDotted(dottedLexiconItem);
		ENUM_OUTPUT_PATTERN outputPatterni = inflectionRecNum.getOutputPattern();
		ENUM_SUFFIX_FUNCTION suffixFunctioni = inflectionRecNum.getSuffixFunction();

		String baseGender = "";
		String baseNumber = "";
		String basePerson = "";
		String PGN = "";
		ENUM_HATTRIBUTE hAttributei = null;
		ENUM_STATUS constructi = null;
		String construct = "";
		String polarity = "";
		String binyan = "";
		String root = "";
		String tense = "";
		String expansion = "";
		String type = "";
		int foreign = -1;
		String description = "";
		String hAttribute = "";
		String value = "";
		PrefixRecord pr = null;

		switch (outputPatterni) {
		case QUANTIFIER:
			PGN = inflectionRecDB.getPGN();
			constructi = inflectionRecNum.getStatus();
			construct = setStatus(constructi);
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			type = inflectionRecDB.getType();

			if (!webFlag) {
				type = Num2Str.strNum2StrType(type, "1", lexiconItem, hebWord);
				PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, hebWord);
			}

			createXML.createQuantifierAnalysis(description, suffixFunctioni, transliteratedLexiconItem, lexiconItem,
					lexiconPointer, hebWord, register, spelling, dottedLexiconItem, PGN, construct, hAttribute, type);

			break;
		case INTERJECTION:
			PGN = inflectionRecDB.getPGN();
			if (!webFlag) {
				PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, hebWord);
			}

			createXML.createInterjectionAnalysis(description, suffixFunctioni, transliteratedLexiconItem, lexiconItem,
					lexiconPointer, hebWord, register, spelling, dottedLexiconItem, PGN);
			break;
		case INTERROGATIVE:
			PGN = inflectionRecDB.getPGN();
			type = inflectionRecDB.getType();
			if (!webFlag) {
				type = Num2Str.strNum2StrType(type, "3", lexiconItem, hebWord);
				PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, hebWord);
			}

			createXML.createInterrogativeAnalysis(description, transliteratedLexiconItem, lexiconItem, lexiconPointer,
					hebWord, register, spelling, dottedLexiconItem, PGN, suffixFunctioni, type);
			break;
		case CONJUNCTION:
			type = inflectionRecDB.getType();
			expansion = inflectionRecDB.getValue();

			if (!webFlag) {
				type = Num2Str.strNum2StrType(type, "4", lexiconItem, hebWord);
				if (expansion.charAt(0) == '-')
					expansion = "";
			}
			expansion = URLDecoder.decode(expansion, "UTF-8");

			createXML.createConjunctionAnalysis(description, expansion, type, transliteratedLexiconItem, lexiconItem,
					lexiconPointer, hebWord, register, spelling, dottedLexiconItem);
			break;
		case ADVERB:
			PGN = inflectionRecDB.getPGN();
			expansion = inflectionRecDB.getValue();
			if (!webFlag) {
				PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, hebWord);
				if (expansion.charAt(0) == '-')
					expansion = "";
			}

			expansion = URLDecoder.decode(expansion, "UTF-8");

			createXML.createAdverbAnalysis(description, suffixFunctioni, transliteratedLexiconItem, lexiconItem,
					lexiconPointer, hebWord, register, spelling, dottedLexiconItem, expansion, PGN);
			break;
		case PREPOSITION:
			PGN = inflectionRecDB.getPGN();
			expansion = inflectionRecDB.getValue();
			if (!webFlag) {
				PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, hebWord);
				if (expansion.charAt(0) == '-')
					expansion = "";
			}

			expansion = URLDecoder.decode(expansion, "UTF-8");

			createXML.createPrepositionAnalysis(description, expansion, suffixFunctioni, transliteratedLexiconItem,
					lexiconItem, lexiconPointer, hebWord, register, spelling, dottedLexiconItem, PGN);
			break;

		case ADJECTIVE:
			foreign = inflectionRecDB.getForeign();
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			construct = setStatus(inflectionRecNum.getStatus());
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			expansion = inflectionRecDB.getValue();

			if (!webFlag) {
				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);
				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);

				if (expansion.charAt(0) == '-')
					expansion = "";
			}
			expansion = URLDecoder.decode(expansion, "UTF-8");

			createXML.createAdjectiveAnalysis(description, foreign, expansion, baseGender, baseNumber, construct,
					transliteratedLexiconItem, lexiconItem, lexiconPointer, hAttribute, hebWord, register, spelling,
					dottedLexiconItem);
			break;
		case NOUN:
			foreign = inflectionRecDB.getForeign();
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			constructi = inflectionRecNum.getStatus();
			construct = setStatus(constructi);
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			expansion = inflectionRecDB.getValue();
			PGN = inflectionRecDB.getPGN();

			if (!webFlag) {
				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);
				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);
				PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, hebWord);

				if (expansion.charAt(0) == '-')
					expansion = "";
			}

			expansion = URLDecoder.decode(expansion, "UTF-8");

			createXML.createNounAnalysis(description, suffixFunctioni, constructi, foreign, expansion, baseGender,
					baseNumber, construct, transliteratedLexiconItem, lexiconItem, lexiconPointer, hAttribute, hebWord,
					register, spelling, dottedLexiconItem, PGN);
			break;
		case PRONOUN:
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			basePerson = inflectionRecDB.getBasePerson();
			type = inflectionRecDB.getType();
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			PGN = inflectionRecDB.getPGN();

			if (!webFlag) {
				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);
				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);
				type = Num2Str.strNum2StrType(type, "9", lexiconItem, hebWord);
				PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, hebWord);
			}

			createXML.createPronounAnalysis(description, suffixFunctioni, hAttributei, baseGender, baseNumber, basePerson,
					type, hAttribute, transliteratedLexiconItem, lexiconItem, lexiconPointer, hebWord, register, spelling,
					dottedLexiconItem, PGN);
			break;
		case PROPERNAME:
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			type = inflectionRecDB.getType();
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			expansion = inflectionRecDB.getValue();

			if (!webFlag) {
				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);
				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);

				type = Num2Str.strNum2StrType(type, "10", lexiconItem, hebWord);

				if (expansion.charAt(0) == '-')
					expansion = "";
			}

			expansion = URLDecoder.decode(expansion, "UTF-8");

			createXML.createProperNameAnalysis(description, baseGender, baseNumber, type, expansion,
					transliteratedLexiconItem, lexiconItem, lexiconPointer, hAttribute, hebWord, register, spelling,
					dottedLexiconItem);
			break;
		case INDEPENDENTINFINITIVE:

		case VERB:
			// System.out.println("BAZINGA");
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			basePerson = inflectionRecDB.getBasePerson();
			binyan = inflectionRecDB.getBinyan();
			root = inflectionRecDB.getRoot();
			// System.out.println("ROOT = " + root);
			root = URLDecoder.decode(root, "UTF-8");
			// System.out.println("ROOT = " + root);
			tense = inflectionRecDB.getTense();
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			PGN = inflectionRecDB.getPGN();

			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);

			expansion = inflectionRecDB.getValue();

			if (!webFlag) {
				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);
				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);
				PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, hebWord);

				if (expansion.charAt(0) == '-')
					expansion = "";
			}

			expansion = URLDecoder.decode(expansion, "UTF-8");
			createXML.createVerbAnalysis(description, suffixFunctioni, binyan, baseGender, baseNumber, basePerson, root,
					tense, transliteratedLexiconItem, lexiconItem, lexiconPointer, hebWord, register, spelling,
					dottedLexiconItem, PGN, expansion);

			break;

		case NEGATION:

			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);

			createXML.createNegationAnalysis(description, transliteratedLexiconItem, lexiconItem, lexiconPointer, hebWord,
					register, spelling, dottedLexiconItem, hAttribute);
			break;
		case PARTICIPLE:
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			basePerson = inflectionRecDB.getBasePerson();
			binyan = inflectionRecDB.getBinyan();
			root = inflectionRecDB.getRoot();
			root = URLDecoder.decode(root, "UTF-8");
			tense = inflectionRecDB.getTense();
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			constructi = inflectionRecNum.getStatus();
			construct = setStatus(constructi);
			PGN = inflectionRecDB.getPGN();
			type = inflectionRecDB.getType();
			expansion = inflectionRecDB.getValue();

			if (!webFlag) {

				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);

				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);

				PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, hebWord);

				type = Num2Str.strNum2StrType(type, "13", lexiconItem, hebWord);

				if (expansion.charAt(0) == '-')
					expansion = "";

			}

			expansion = URLDecoder.decode(expansion, "UTF-8");
			createXML.createParticipleAnalysis(description, suffixFunctioni, constructi, hAttributei, baseGender,
					baseNumber, construct, basePerson, transliteratedLexiconItem, lexiconItem, lexiconPointer, root, binyan,
					hAttribute, hebWord, register, spelling, dottedLexiconItem, PGN, type, expansion);
			break;
		case PASSIVEPARTICIPLE:
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			basePerson = inflectionRecDB.getBasePerson();
			binyan = inflectionRecDB.getBinyan();
			root = inflectionRecDB.getRoot();
			root = URLDecoder.decode(root, "UTF-8");
			tense = inflectionRecDB.getTense();
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			constructi = inflectionRecNum.getStatus();
			construct = setStatus(constructi);
			type = inflectionRecDB.getType();

			if (!webFlag) {

				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);

				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);

				type = Num2Str.strNum2StrType(type, "20", lexiconItem, hebWord);

			}

			createXML.createPassiveParticipleAnalysis(description, constructi, hAttributei, baseGender, baseNumber,
					construct, basePerson, transliteratedLexiconItem, lexiconItem, lexiconPointer, root, binyan, hAttribute,
					hebWord, register, spelling, dottedLexiconItem, type);
			break;
		case WPREFIX:
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			polarity = Num2Str.char2StrPolarity(inflectionRecDB.getPolarity());
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			if (!webFlag) {

				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);

				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);

			}

			createXML.createWprefixAnalysis(baseGender, baseNumber, description, transliteratedLexiconItem, lexiconItem,
					lexiconPointer, hebWord, register, spelling, dottedLexiconItem, hAttribute, polarity);
			break;
		case NUMERAL:
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			constructi = inflectionRecNum.getStatus();
			construct = setStatus(constructi);
			PGN = inflectionRecDB.getPGN();
			value = inflectionRecDB.getValue();
			value = URLDecoder.decode(value, "UTF-8");
			type = inflectionRecDB.getType();
			if (!webFlag) {

				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);

				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);

				PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, hebWord);

				type = Num2Str.strNum2StrType(type, "14", lexiconItem, hebWord);

			}

			createXML.createNumeralAnalysis(description, suffixFunctioni, hAttributei, constructi, baseGender, baseNumber,
					construct, value, transliteratedLexiconItem, lexiconItem, lexiconPointer, hAttribute, type, hebWord,
					register, spelling, dottedLexiconItem, PGN);
			break;
		case EXISTENTIAL:
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			basePerson = inflectionRecDB.getBasePerson();
			construct = setStatus(inflectionRecNum.getStatus());
			root = inflectionRecDB.getRoot();
			root = URLDecoder.decode(root, "UTF-8");
			tense = inflectionRecDB.getTense();
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			polarity = Num2Str.char2StrPolarity(inflectionRecDB.getPolarity());
			PGN = inflectionRecDB.getPGN();
			if (!webFlag) {

				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);

				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);

				PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, hebWord);

			}

			createXML.createExistentialAnalysis(description, suffixFunctioni, baseGender, baseNumber,
					transliteratedLexiconItem, lexiconItem, lexiconPointer, root, polarity, tense, hAttribute, basePerson,
					hebWord, register, spelling, dottedLexiconItem, PGN);
			break;
		// case IMPERSONAL:
		//
		// createXML.createImpersonalAnalysis(description,
		// transliteratedLexiconItem, lexiconItem, lexiconPointer,
		// hebWord,
		// dottedLexiconItem);
		//
		// break;
		case MODAL:
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			basePerson = inflectionRecDB.getBasePerson();
			tense = inflectionRecDB.getTense();
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);

			if (!webFlag) {

				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);

				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);

			}

			createXML.createModalAnalysis(description, baseGender, baseNumber, basePerson, tense,
					transliteratedLexiconItem, lexiconItem, lexiconPointer, hebWord, register, spelling, dottedLexiconItem,
					hAttributei);
			break;
		case COPULA:
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			basePerson = inflectionRecDB.getBasePerson();
			tense = inflectionRecDB.getTense();
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			polarity = Num2Str.char2StrPolarity(inflectionRecDB.getPolarity());

			PGN = inflectionRecDB.getPGN();
			if (!webFlag) {

				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);

				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);

				PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, hebWord);

			}

			createXML.createCopulaAnalysis(description, suffixFunctioni, baseGender, baseNumber, basePerson, polarity,
					tense, transliteratedLexiconItem, lexiconItem, register, spelling, lexiconPointer, hebWord,
					dottedLexiconItem, PGN);
			break;
		case TITLE:
			baseGender = inflectionRecDB.getBaseGender();
			baseNumber = inflectionRecDB.getBaseNumber();
			hAttributei = inflectionRecNum.getHAttribute();
			hAttribute = setHAttribute(pr, hAttributei);
			expansion = inflectionRecDB.getValue();
			expansion = URLDecoder.decode(expansion, "UTF-8");
			if (!webFlag) {

				baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, hebWord);

				baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, hebWord);

				if (expansion.charAt(0) == '-')
					expansion = "";

			}

			createXML.createTitleAnalysis(description, baseGender, baseNumber, hAttribute, expansion,
					transliteratedLexiconItem, lexiconItem, lexiconPointer, hebWord, register, spelling, dottedLexiconItem);
			break;

		}
	}

	static public void buildXMLPrefixOutput(final PrefixRecord pr, DBInflectionsRecord inflectionRecDB,
			InflectedRecordNum inflectionRecNum, final CreateCorpusXML createXML, final String baseHebWord)
			throws Exception {
		ENUM_OUTPUT_PATTERN outputPatterni = inflectionRecNum.getOutputPattern();
		ENUM_HATTRIBUTE hAttributei = inflectionRecNum.getHAttribute();

		// System.out.println("(F) buildXMLPrefixOutput
		// (baseHebWord,outputPatterni) ("
		// + baseHebWord +","+ outputPatterni +")" );

		String description = "";
		String hAttribute = "";
		String value = "";
		boolean definiteArticleTag = false;

		// ///////////////////////////////////////
		// Gimatria Analysis Handling
		// /////////////////////////////////////
		switch (outputPatterni) {
		case GEMATRIA:
			value = inflectionRecDB.getValue();
			// ���� �� ������ �� � ���� - ��"�
			if (pr == null) {
				description = "definiteArticle";
				// hAttribute = "true";
				// �� ������ �������� �� ����
				// ���
			} else if (hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_TRUE) {
				description = pr.getDescription();
				// hAttribute = "true";
			}
			// ���� �� - ���� �� ������ �����
			else {
				description = pr.getDescription();
				definiteArticleTag = pr.isDefiniteArticleTag();
				if (definiteArticleTag)
					return; // ����� �� ������� ��"� - ��� ��
								// ����� ���� 2
				// ������� - ����� ��� �����
				else
					hAttribute = "false";
			}
			createXML.createNumeralAnalysis(description, null, hAttributei, null, "", "", "", value, "", "", "",
					hAttribute, "gematria", "", "", "", "", "");
			break;
		case PREFIXES:
			if (pr == null) {
				// System.out.println("(F) buildXMLPrefixOutput pr == null" );
				createXML.createPrefixesAnalysis("definiteArticle", ENUM_HATTRIBUTE.PREFIX_STANDALONE_H);
			} else {
				description = pr.getDescription();
				hAttributei = inflectionRecNum.getHAttribute();
				// System.out.println("(F) buildXMLPrefixOutput pr != null
				// (description,hAttributei) ("
				// + description + "," + hAttributei + ")" );
				createXML.createPrefixesAnalysis(description, hAttributei);
			}
			break;
		default:
			String transliteratedLexiconItem = "";
			String lexiconItem = inflectionRecDB.getBaseUndottedLItem();
			lexiconItem = URLDecoder.decode(lexiconItem, "UTF-8");
			boolean webFlag = Data.webFlag;
			if (!webFlag) {
				transliteratedLexiconItem = Translate.Heb2Eng(lexiconItem);
			} else
				transliteratedLexiconItem = inflectionRecDB.getBaseTransliteratedLItem();

			checkTokensStartedWithww(pr.getPrefix(), inflectionRecDB, inflectionRecNum);
			description = pr.getDescription();
			String lexiconPointer = inflectionRecDB.getBaseLexiconPointer();
			lexiconItem = inflectionRecDB.getBaseUndottedLItem();
			lexiconItem = URLDecoder.decode(lexiconItem, "UTF-8");
			String register = inflectionRecDB.getRegister();
			String spelling = inflectionRecDB.getSpelling();
			String dottedLexiconItem = inflectionRecDB.getDottedLexiconItem();
			dottedLexiconItem = setDotted(dottedLexiconItem);
			ENUM_SUFFIX_FUNCTION suffixFunctioni = inflectionRecNum.getSuffixFunction();

			String baseGender = "";
			String baseNumber = "";
			String basePerson = "";
			String PGN = "";
			ENUM_STATUS constructi = null;
			String construct = "";
			String polarity = "";
			String binyan = "";
			String root = "";
			String tense = "";
			String expansion = "";
			String type = "";
			int foreign = -1;

			switch (outputPatterni) {
			case QUANTIFIER:
				PGN = inflectionRecDB.getPGN();
				constructi = inflectionRecNum.getStatus();
				construct = setStatus(constructi);
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				type = inflectionRecDB.getType();

				if (!webFlag) {
					type = Num2Str.strNum2StrType(type, "1", lexiconItem, baseHebWord);
					PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, baseHebWord);
				}

				createXML.createQuantifierAnalysis(description, suffixFunctioni, transliteratedLexiconItem, lexiconItem,
						lexiconPointer, baseHebWord, register, spelling, dottedLexiconItem, PGN, construct, hAttribute, type);
				break;
			case INTERJECTION:
				PGN = inflectionRecDB.getPGN();
				if (!webFlag) {
					PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, baseHebWord);
				}

				createXML.createInterjectionAnalysis(description, suffixFunctioni, transliteratedLexiconItem, lexiconItem,
						lexiconPointer, baseHebWord, register, spelling, dottedLexiconItem, PGN);
				break;
			case INTERROGATIVE:
				PGN = inflectionRecDB.getPGN();
				type = inflectionRecDB.getType();
				if (!webFlag) {
					type = Num2Str.strNum2StrType(type, "3", lexiconItem, baseHebWord);

					PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, baseHebWord);
				}

				createXML.createInterrogativeAnalysis(description, transliteratedLexiconItem, lexiconItem, lexiconPointer,
						baseHebWord, register, spelling, dottedLexiconItem, PGN, suffixFunctioni, type);
				break;
			case CONJUNCTION:
				type = inflectionRecDB.getType();
				expansion = inflectionRecDB.getValue();
				if (!webFlag) {
					type = Num2Str.strNum2StrType(type, "4", lexiconItem, baseHebWord);

					if (expansion.charAt(0) == '-')
						expansion = "";
				}
				expansion = URLDecoder.decode(expansion, "UTF-8");

				createXML.createConjunctionAnalysis(description, expansion, type, transliteratedLexiconItem, lexiconItem,
						lexiconPointer, baseHebWord, register, spelling, dottedLexiconItem);
				break;
			case ADVERB:
				PGN = inflectionRecDB.getPGN();
				expansion = inflectionRecDB.getValue();
				if (!webFlag) {
					PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, baseHebWord);

					if (expansion.charAt(0) == '-')
						expansion = "";
				}

				expansion = URLDecoder.decode(expansion, "UTF-8");

				createXML.createAdverbAnalysis(description, suffixFunctioni, transliteratedLexiconItem, lexiconItem,
						lexiconPointer, baseHebWord, register, spelling, dottedLexiconItem, expansion, PGN);
				break;
			case PREPOSITION:
				PGN = inflectionRecDB.getPGN();
				expansion = inflectionRecDB.getValue();
				if (!webFlag) {
					PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, baseHebWord);

					if (expansion.charAt(0) == '-')
						expansion = "";
				}

				expansion = URLDecoder.decode(expansion, "UTF-8");

				createXML.createPrepositionAnalysis(description, expansion, suffixFunctioni, transliteratedLexiconItem,
						lexiconItem, lexiconPointer, baseHebWord, register, spelling, dottedLexiconItem, PGN);
				break;

			case ADJECTIVE:
				foreign = inflectionRecDB.getForeign();
				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				construct = setStatus(inflectionRecNum.getStatus());
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				expansion = inflectionRecDB.getValue();

				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);

					if (expansion.charAt(0) == '-')
						expansion = "";
				}
				expansion = URLDecoder.decode(expansion, "UTF-8");

				createXML.createAdjectiveAnalysis(description, foreign, expansion, baseGender, baseNumber, construct,
						transliteratedLexiconItem, lexiconItem, lexiconPointer, hAttribute, baseHebWord, register, spelling,
						dottedLexiconItem);
				break;
			case NOUN:
				foreign = inflectionRecDB.getForeign();
				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				constructi = inflectionRecNum.getStatus();
				construct = setStatus(constructi);
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);

				expansion = inflectionRecDB.getValue();
				PGN = inflectionRecDB.getPGN();

				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);

					PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, baseHebWord);

					if (expansion.charAt(0) == '-')
						expansion = "";
				}

				expansion = URLDecoder.decode(expansion, "UTF-8");

				createXML.createNounAnalysis(description, suffixFunctioni, constructi, foreign, expansion, baseGender,
						baseNumber, construct, transliteratedLexiconItem, lexiconItem, lexiconPointer, hAttribute,
						baseHebWord, register, spelling, dottedLexiconItem, PGN);
				break;
			case PRONOUN:
				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				basePerson = inflectionRecDB.getBasePerson();
				type = inflectionRecDB.getType();
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				PGN = inflectionRecDB.getPGN();

				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);

					type = Num2Str.strNum2StrType(type, "9", lexiconItem, baseHebWord);

					PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, baseHebWord);

				}

				createXML.createPronounAnalysis(description, suffixFunctioni, hAttributei, baseGender, baseNumber,
						basePerson, type, hAttribute, transliteratedLexiconItem, lexiconItem, lexiconPointer, baseHebWord,
						register, spelling, dottedLexiconItem, PGN);
				break;
			case PROPERNAME:
				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				type = inflectionRecDB.getType();
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				expansion = inflectionRecDB.getValue();

				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);

					type = Num2Str.strNum2StrType(type, "10", lexiconItem, baseHebWord);

					if (expansion.charAt(0) == '-')
						expansion = "";
				}

				expansion = URLDecoder.decode(expansion, "UTF-8");

				createXML.createProperNameAnalysis(description, baseGender, baseNumber, type, expansion,
						transliteratedLexiconItem, lexiconItem, lexiconPointer, hAttribute, baseHebWord, register, spelling,
						dottedLexiconItem);
				break;
			case INDEPENDENTINFINITIVE:
			case VERB:
				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				basePerson = inflectionRecDB.getBasePerson();
				binyan = inflectionRecDB.getBinyan();
				root = inflectionRecDB.getRoot();
				root = URLDecoder.decode(root, "UTF-8");
				tense = inflectionRecDB.getTense();
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				PGN = inflectionRecDB.getPGN();
				expansion = inflectionRecDB.getValue();

				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);

					PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, baseHebWord);

					if (expansion.charAt(0) == '-')
						expansion = "";
				}

				expansion = URLDecoder.decode(expansion, "UTF-8");
				createXML.createVerbAnalysis(description, suffixFunctioni, binyan, baseGender, baseNumber, basePerson, root,
						tense, transliteratedLexiconItem, lexiconItem, lexiconPointer, baseHebWord, register, spelling,
						dottedLexiconItem, PGN, expansion);

				break;

			case NEGATION:

				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);

				createXML.createNegationAnalysis(description, transliteratedLexiconItem, lexiconItem, lexiconPointer,
						baseHebWord, register, spelling, dottedLexiconItem, hAttribute);
				break;
			case PARTICIPLE:
				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				basePerson = inflectionRecDB.getBasePerson();
				binyan = inflectionRecDB.getBinyan();
				root = inflectionRecDB.getRoot();
				root = URLDecoder.decode(root, "UTF-8");
				tense = inflectionRecDB.getTense();
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				constructi = inflectionRecNum.getStatus();
				construct = setStatus(constructi);
				PGN = inflectionRecDB.getPGN();
				type = inflectionRecDB.getType();
				expansion = inflectionRecDB.getValue();
				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);

					PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, baseHebWord);

					type = Num2Str.strNum2StrType(type, "13", lexiconItem, baseHebWord);
					if (expansion.charAt(0) == '-')
						expansion = "";
				}

				expansion = URLDecoder.decode(expansion, "UTF-8");
				createXML.createParticipleAnalysis(description, suffixFunctioni, constructi, hAttributei, baseGender,
						baseNumber, construct, basePerson, transliteratedLexiconItem, lexiconItem, lexiconPointer, root,
						binyan, hAttribute, baseHebWord, register, spelling, dottedLexiconItem, PGN, type, expansion);
				break;
			case PASSIVEPARTICIPLE:
				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				basePerson = inflectionRecDB.getBasePerson();
				binyan = inflectionRecDB.getBinyan();
				root = inflectionRecDB.getRoot();
				root = URLDecoder.decode(root, "UTF-8");
				tense = inflectionRecDB.getTense();
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				type = inflectionRecDB.getType();
				constructi = inflectionRecNum.getStatus();
				construct = setStatus(constructi);

				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);

					type = Num2Str.strNum2StrType(type, "13", lexiconItem, baseHebWord);
				}

				createXML.createPassiveParticipleAnalysis(description, constructi, hAttributei, baseGender, baseNumber,
						construct, basePerson, transliteratedLexiconItem, lexiconItem, lexiconPointer, root, binyan,
						hAttribute, baseHebWord, register, spelling, dottedLexiconItem, type);
				break;
			case WPREFIX:
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				polarity = Num2Str.char2StrPolarity(inflectionRecDB.getPolarity());

				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);
				}

				createXML.createWprefixAnalysis(baseGender, baseNumber, description, transliteratedLexiconItem, lexiconItem,
						lexiconPointer, baseHebWord, register, spelling, dottedLexiconItem, hAttribute, polarity);
				break;
			case NUMERAL:
				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				constructi = inflectionRecNum.getStatus();
				construct = setStatus(constructi);
				PGN = inflectionRecDB.getPGN();
				value = inflectionRecDB.getValue();
				value = URLDecoder.decode(value, "UTF-8");
				type = inflectionRecDB.getType();
				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);

					PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, baseHebWord);

					type = Num2Str.strNum2StrType(type, "14", lexiconItem, baseHebWord);
				}

				createXML.createNumeralAnalysis(description, suffixFunctioni, hAttributei, constructi, baseGender,
						baseNumber, construct, value, transliteratedLexiconItem, lexiconItem, lexiconPointer, hAttribute,
						type, baseHebWord, register, spelling, dottedLexiconItem, PGN);
				break;
			case EXISTENTIAL:
				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				basePerson = inflectionRecDB.getBasePerson();
				construct = setStatus(inflectionRecNum.getStatus());
				root = inflectionRecDB.getRoot();
				root = URLDecoder.decode(root, "UTF-8");
				tense = inflectionRecDB.getTense();
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				polarity = Num2Str.char2StrPolarity(inflectionRecDB.getPolarity());

				PGN = inflectionRecDB.getPGN();
				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);

					PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, baseHebWord);
				}

				createXML.createExistentialAnalysis(description, suffixFunctioni, baseGender, baseNumber,
						transliteratedLexiconItem, lexiconItem, lexiconPointer, root, polarity, tense, hAttribute, basePerson,
						baseHebWord, register, spelling, dottedLexiconItem, PGN);
				break;
			// case IMPERSONAL:
			//
			// createXML.createImpersonalAnalysis(description,
			// transliteratedLexiconItem, lexiconItem, lexiconPointer,
			// baseHebWord,
			// dottedLexiconItem);
			//
			// break;
			case MODAL:
				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				basePerson = inflectionRecDB.getBasePerson();
				tense = inflectionRecDB.getTense();
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);
				}

				createXML.createModalAnalysis(description, baseGender, baseNumber, basePerson, tense,
						transliteratedLexiconItem, lexiconItem, lexiconPointer, baseHebWord, register, spelling,
						dottedLexiconItem, hAttributei);
				break;
			case COPULA:
				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				basePerson = inflectionRecDB.getBasePerson();
				tense = inflectionRecDB.getTense();
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				polarity = Num2Str.char2StrPolarity(inflectionRecDB.getPolarity());

				PGN = inflectionRecDB.getPGN();
				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);

					PGN = Num2Str.strNum2StrPGN(PGN, lexiconItem, baseHebWord);
				}

				createXML.createCopulaAnalysis(description, suffixFunctioni, baseGender, baseNumber, basePerson, polarity,
						tense, transliteratedLexiconItem, lexiconItem, register, spelling, lexiconPointer, baseHebWord,
						dottedLexiconItem, PGN);
				break;
			case TITLE:
				baseGender = inflectionRecDB.getBaseGender();
				baseNumber = inflectionRecDB.getBaseNumber();
				hAttributei = inflectionRecNum.getHAttribute();
				hAttribute = setHAttribute(pr, hAttributei);
				expansion = inflectionRecDB.getValue();
				expansion = URLDecoder.decode(expansion, "UTF-8");
				if (!webFlag) {
					baseGender = Num2Str.strNum2StrGender(baseGender, lexiconItem, baseHebWord);

					baseNumber = Num2Str.strNum2StrNumber(baseNumber, lexiconItem, baseHebWord);

					if (expansion.charAt(0) == '-')
						expansion = "";
				}

				createXML.createTitleAnalysis(description, baseGender, baseNumber, hAttribute, expansion,
						transliteratedLexiconItem, lexiconItem, lexiconPointer, baseHebWord, register, spelling,
						dottedLexiconItem);
				break;

			}
		}
	}

	private static void checkTokensStartedWithww(final String prefix, DBInflectionsRecord inflectionsRecDB,
			InflectedRecordNum inflectionsRecNum) {
		// ///////////////////////////////////////////////////////////
		// tokens starting with w - special handling
		// //////////////////////////////////////////////////////////
		// handle words which starts with w - when
		// accompanied with prefix the
		// w must be doubled else it is colloquial
		String base = inflectionsRecDB.getTransliterated();
		if (base.charAt(0) == 'w' && base.charAt(1) == 'w') {
			inflectionsRecDB.setSpelling("standard");
			inflectionsRecNum.setSpelling(1);
			// ����� ��"� ������ ��� ��
			// ������ �
		} else if (base.charAt(0) == 'w' && !prefix.endsWith("w")) {
			inflectionsRecDB.setSpelling("irregular");
			inflectionsRecNum.setSpelling(2);
		}
	}

	public static String setDotted(String dottedLexiconItem) throws UnsupportedEncodingException {
		if (dottedLexiconItem == null)
			dottedLexiconItem = "";
		else if (dottedLexiconItem.length() > 0)
			dottedLexiconItem = URLDecoder.decode(dottedLexiconItem, "UTF-8");
		return dottedLexiconItem;
	}

	public static String setHAttribute(final PrefixRecord pr, final ENUM_HATTRIBUTE hattributei) {
		String hAttribute = "";
		if (pr == null) { // no Prefix

			switch (hattributei) {
			case BASE_DEFINITNESS_TRUE_TRUE:
			case BASE_DEFINITNESS_REQUIRED_TRUE:
				hAttribute = "true";
				break;
			case SUBCOORDINATING:
				hAttribute = "subcoordinating";
				break;
			default:
				hAttribute = "false";
				break;
			}
		} else
			hAttribute = setHAttributeWithPrefix(pr, hattributei);

		return hAttribute;
	}

	private static String setHAttributeWithPrefix(final PrefixRecord pr, final ENUM_HATTRIBUTE hAttributei) {
		String returnedHAttribute = "";
		boolean definiteArticleTag = pr.isDefiniteArticleTag();
		if ((definiteArticleTag && hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_REQUIRED_TRUE)
				|| (definiteArticleTag && hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_FALSE)
				|| (!definiteArticleTag && hAttributei == ENUM_HATTRIBUTE.BASE_DEFINITNESS_TRUE_TRUE))
			returnedHAttribute = "true";

		else if (hAttributei != ENUM_HATTRIBUTE.SUBCOORDINATING)
			returnedHAttribute = "false";

		return returnedHAttribute;
	}

	public static String setStatus(ENUM_STATUS statusi) {
		String status = "";
		switch (statusi) {
		case CONSTRUCT_TRUE:
			status = "construct";
			break;
		case CONSTRUCT_FALSE:
			status = "absolute";
			break;
		default:
			status = "unspecified";
		}
		return status;
	}

}
