package mila.lexicon.utils;

import java.util.StringTokenizer;

import static mila.lexicon.analyse.Constants.*;

public class StringUtils {
	public static boolean analyzeForeign(String token) {
		if (token.length() == 0) {
			return false;
		}
		boolean isForeign = false;
		// System.out.println("hebWord=" + token);
		char char0 = token.charAt(0);
		if (char0 >= 'a' && char0 <= 'z' || char0 >= 'A' && char0 <= 'Z') {
			isForeign = true;
		}
		return isForeign;
	}

	public static boolean analyzeHebrewSingleLetter(String token) {
		boolean hebrewSingleLetter = false;
		if (token.length() == 1) {
			if (token.matches(ALEFBAIT)) {
				hebrewSingleLetter = true;
			}
		}
		return hebrewSingleLetter;
	}

	public static ENUM_OUTPUT_PATTERN analyzeNumberExpression(String token) throws Exception {
		ENUM_OUTPUT_PATTERN outputPattern = null;
		if (token.matches("[0-9]+\\.")) {
			return ENUM_OUTPUT_PATTERN.NUMBERING;
		}
		if (!token.matches("\\d+[:\\/\\.]\\d+[:\\/\\.]*\\d*")) {
			return ENUM_OUTPUT_PATTERN.UNSPECIFIED;
		}

		char char0;
		char char1;
		char char2;
		char char3;
		char char4;
		char char5;
		char char6;
		char char7;
		char char8;
		char char9;

		int length = token.length();
		switch (length) {
		case 3:
			char0 = token.charAt(0);
			char1 = token.charAt(1);
			char2 = token.charAt(2);
			if (char1 == ':' && Character.isDigit(char0) && Character.isDigit(char2)) {
				outputPattern = ENUM_OUTPUT_PATTERN.GAMESCORE;
			}
			break;
		case 4:
			char0 = token.charAt(0);
			char1 = token.charAt(1);
			char2 = token.charAt(2);
			char3 = token.charAt(3);
			if (token.indexOf(":") != -1) {
				int index = token.indexOf(":");
				if (index == 1) {
					if (Character.isDigit(char0) && Character.isDigit(char2) && Character.isDigit(char3)) {
						int num1 = Integer.valueOf(token.substring(0, 1)).intValue();
						int num2 = Integer.valueOf(token.substring(2, 4)).intValue();
						if (num1 >= 0 && num1 <= 24 && num2 >= 0 && num2 <= 59) {
							if (char0 == '0' || char1 == '0' && char2 == '0' || char2 == '0') {
								outputPattern = ENUM_OUTPUT_PATTERN.TIME;
							} else {
								outputPattern = ENUM_OUTPUT_PATTERN.TIMEGAMESCORE;
							}
						} else {
							outputPattern = ENUM_OUTPUT_PATTERN.GAMESCORE;
						}
					}
				} else if (index == 2) {
					if (Character.isDigit(char0) && Character.isDigit(char1) && Character.isDigit(char3)) {
						Integer.parseInt(token.substring(0, index));
						Integer.parseInt(token.substring(index + 1));

						outputPattern = ENUM_OUTPUT_PATTERN.GAMESCORE;

					}
				}
			}
			break;
		case 5:
			char0 = token.charAt(0);
			char1 = token.charAt(1);
			char2 = token.charAt(2);
			char3 = token.charAt(3);
			char4 = token.charAt(4);
			if (char2 == ':' && Character.isDigit(char0) && Character.isDigit(char1) && Character.isDigit(char3)
					&& Character.isDigit(char4)) {
				int num1 = Integer.valueOf(token.substring(0, 1)).intValue();
				int num2 = Integer.valueOf(token.substring(3)).intValue();

				if (num1 >= 0 && num1 <= 24 && num2 >= 0 && num2 <= 59) {

					if (char0 == '0' && char1 == '0' || char3 == '0' && char4 == '0' || char0 == '0' && char1 != '0'
							|| char3 == '0' && char4 != '0') {
						outputPattern = ENUM_OUTPUT_PATTERN.TIME;
					} else {
						outputPattern = ENUM_OUTPUT_PATTERN.TIMEGAMESCORE;
					}
				} else {
					outputPattern = ENUM_OUTPUT_PATTERN.GAMESCORE;
				}
			} else if (char2 == '/' && Character.isDigit(char0) && Character.isDigit(char1) && Character.isDigit(char3)
					&& Character.isDigit(char4)) {
				int num1 = Integer.valueOf(token.substring(0, 1)).intValue();
				int num2 = Integer.valueOf(token.substring(3)).intValue();

				if (num1 <= 31 && num2 <= 12) {
					outputPattern = ENUM_OUTPUT_PATTERN.DATE;
				}

			}
			break;
		case 6:
			if (token.indexOf(":") != -1) {
				int index = token.indexOf(":");
				// ************************************** EDIT 21.11.10 (yossi)
				String subs = token.substring(index + 1);
				// point in second part like swiming time - 12:47.7 - for now i
				// return a "UNSPECIFIED"
				if (subs.indexOf(".") != -1) {
					return ENUM_OUTPUT_PATTERN.UNSPECIFIED;
				} else {
					// there is no point in second part - 75:787
					Integer.parseInt(subs);
				}
				outputPattern = ENUM_OUTPUT_PATTERN.GAMESCORE;
			}
			break;
		case 7:
			char0 = token.charAt(0);
			char1 = token.charAt(1);
			char2 = token.charAt(2);
			char3 = token.charAt(3);
			char4 = token.charAt(4);
			char5 = token.charAt(5);
			char6 = token.charAt(6);
			int num1, num2, num3;
			int index = token.indexOf(":");
			if (index != -1) {
				num1 = Integer.valueOf(token.substring(0, index)).intValue();
				String subs = token.substring(index + 1);
				if (subs.indexOf(".") != -1) { // point in second part like
					// swimming time - 12:47.74 -
					// for now i return a
					// "UNSPECIFIED"
					return ENUM_OUTPUT_PATTERN.UNSPECIFIED;
				} else if (subs.indexOf(":") != -1) {
					int index2 = subs.indexOf(":");
					num2 = Integer.valueOf(subs.substring(0, index2)).intValue();
					num3 = Integer.valueOf(subs.substring(index2 + 1)).intValue();
					outputPattern = ENUM_OUTPUT_PATTERN.TIME;
				} else {
					num2 = Integer.valueOf(token.substring(index + 1)).intValue();
					outputPattern = ENUM_OUTPUT_PATTERN.GAMESCORE;
				}
			} else {
				if (char2 == '.' && char4 == '.'
						|| char2 == '/' && char4 == '/' && Character.isDigit(char0) && Character.isDigit(char1)
								&& Character.isDigit(char3) && Character.isDigit(char5) && Character.isDigit(char6)) {
					num1 = Integer.valueOf(token.substring(0, 2)).intValue();
					num2 = Integer.valueOf(token.substring(3, 4)).intValue();
					if (num1 >= 1 && num1 <= 31 && num2 >= 1 && num2 <= 12) {

						outputPattern = ENUM_OUTPUT_PATTERN.DATE;
					}
				} else if (char1 == '.' && char4 == '.'
						|| char1 == '/' && char4 == '/' && Character.isDigit(char0) && Character.isDigit(char2)
								&& Character.isDigit(char3) && Character.isDigit(char5) && Character.isDigit(char6)) {
					num1 = Integer.valueOf(token.substring(0, 1)).intValue();
					num2 = Integer.valueOf(token.substring(2, 4)).intValue();

					if (num1 >= 1 && num1 <= 31 && num2 >= 1 && num2 <= 12) {

						outputPattern = ENUM_OUTPUT_PATTERN.DATE;
					}
				} else if (char1 == ':' && char4 == ':' && Character.isDigit(char0) && Character.isDigit(char2)
						&& Character.isDigit(char3) && Character.isDigit(char5) && Character.isDigit(char6)) {
					num1 = Integer.valueOf(token.substring(0, 1)).intValue();
					num2 = Integer.valueOf(token.substring(2, 4)).intValue();
					num3 = Integer.valueOf(token.substring(5, 7)).intValue();
					if (num1 >= 0 && num1 <= 24 && num2 >= 0 && num2 <= 59 && num3 >= 0 && num3 <= 59) {

						outputPattern = ENUM_OUTPUT_PATTERN.TIME;
					}

				}
			}
			break;
		case 8:
			char0 = token.charAt(0);
			char1 = token.charAt(1);
			char2 = token.charAt(2);
			char3 = token.charAt(3);
			char4 = token.charAt(4);
			char5 = token.charAt(5);
			char6 = token.charAt(6);
			char7 = token.charAt(7);
			if ((char2 == '.' && char5 == '.' || char2 == '/' && char5 == '/') && Character.isDigit(char0)
					&& Character.isDigit(char1) && Character.isDigit(char3) && Character.isDigit(char4)
					&& Character.isDigit(char6) && Character.isDigit(char7)) {

				outputPattern = ENUM_OUTPUT_PATTERN.DATE;
			} else if ((char1 == '.' && char3 == '.' || char1 == '/' && char3 == '/') && Character.isDigit(char0)
					&& Character.isDigit(char2) && Character.isDigit(char4) && Character.isDigit(char5)
					&& Character.isDigit(char6) && Character.isDigit(char7)) {
				num1 = Integer.valueOf(token.substring(0, 1)).intValue();
				num2 = Integer.valueOf(token.substring(2, 3)).intValue();
				num3 = Integer.valueOf(token.substring(4)).intValue();
				outputPattern = ENUM_OUTPUT_PATTERN.DATE;
			} else if (char2 == ':' && char5 == ':' && Character.isDigit(char0) && Character.isDigit(char1)
					&& Character.isDigit(char3) && Character.isDigit(char4) && Character.isDigit(char6)
					&& Character.isDigit(char7)) {
				num1 = Integer.valueOf(token.substring(0, 1)).intValue();
				num2 = Integer.valueOf(token.substring(3, 4)).intValue();
				num3 = Integer.valueOf(token.substring(6, 7)).intValue();

				if (num1 >= 0 && num1 <= 24 && num2 >= 0 && num2 <= 59 && num3 >= 0 && num3 <= 24) {

					outputPattern = ENUM_OUTPUT_PATTERN.TIME;
				}
			}
			break;
		case 9:
			char0 = token.charAt(0);
			char1 = token.charAt(1);
			char2 = token.charAt(2);
			char3 = token.charAt(3);
			char4 = token.charAt(4);
			char5 = token.charAt(5);
			char6 = token.charAt(6);
			char7 = token.charAt(7);
			char8 = token.charAt(8);
			if ((char1 == '.' && char4 == '.' || char1 == '/' && char4 == '/') && Character.isDigit(char0)
					&& Character.isDigit(char2) && Character.isDigit(char3) && Character.isDigit(char5)
					&& Character.isDigit(char6) && Character.isDigit(char7) && Character.isDigit(char8)) {

				outputPattern = ENUM_OUTPUT_PATTERN.DATE;
			}
			break;
		case 10:
			char0 = token.charAt(0);
			char1 = token.charAt(1);
			char2 = token.charAt(2);
			char3 = token.charAt(3);
			char4 = token.charAt(4);
			char5 = token.charAt(5);
			char6 = token.charAt(6);
			char7 = token.charAt(7);
			char8 = token.charAt(8);
			char9 = token.charAt(9);
			if ((char2 == '.' && char5 == '.' || char2 == '/' && char5 == '/') && Character.isDigit(char0)
					&& Character.isDigit(char1) && Character.isDigit(char3) && Character.isDigit(char4)
					&& Character.isDigit(char6) && Character.isDigit(char7) && Character.isDigit(char8)
					&& Character.isDigit(char9)) {

				outputPattern = ENUM_OUTPUT_PATTERN.DATE;
			}
			break;

		}
		if (outputPattern == null) {
			outputPattern = ENUM_OUTPUT_PATTERN.UNSPECIFIED;
		}
		return outputPattern;
	}

	public static boolean analyzeNumbers(String token) throws Exception {
		if (token.length() == 0) {
			return false;
		}
		boolean isNumber = false;
		if (!token.matches("[0-9]+\\.")) {
			if (Character.isDigit(token.charAt(0))) {
				if (token.length() >= 3 && token.indexOf("/") != -1 && token.lastIndexOf("/") == token.indexOf("/")) {
					isNumber = true;
				} else if (token.indexOf("/") == -1 && token.indexOf(":") == -1
						&& token.lastIndexOf(".") == token.indexOf(".")) {

					isNumber = true;
				}
			} else if (token.codePointAt(0) == 189 || token.codePointAt(0) == 188 || token.codePointAt(0) == 190) {
				isNumber = true;
			}
		}
		return isNumber;
	}

	public static boolean analyzePunctuations(String token) {
		final String punctuations = "=.!;|}{][*^&%#@~$/\\+:()�-,?_'����";
		boolean punctuation = false;
		int wordLength = token.length();

		// in case like ���'

		if (wordLength == 1) {
			if (token.charAt(0) == '"') {
				punctuation = true;
			}
			int punctuationsSize = punctuations.length();
			// analyze punctuations list for the relevant punctuation
			for (int i = 0; i < punctuationsSize; i++) {
				if (token.indexOf(punctuations.charAt(i)) != -1) {
					punctuation = true;
				}
			}
		} else if (token.startsWith(".") && token.endsWith(".")) {
			punctuation = true;
		}
		if (punctuation) {

		}
		return punctuation;
	}

	public static boolean analyzeURL(String token) {
		boolean isURL = false;
		if (token.indexOf("@") != -1 && token.indexOf(".") != -1 || token.startsWith("http://")
				|| token.startsWith("www.") || token.startsWith("ftp://")) {
			isURL = true;
		}
		return isURL;
	}

	public static String getGenderPGN(String PGN) {
		String suffixGender = "";
		StringTokenizer st = new StringTokenizer(PGN, "/");
		st.nextToken();
		suffixGender = st.nextToken();
		if (suffixGender.equals("M")) {
			suffixGender = "masculine";
		} else if (suffixGender.equals("F")) {
			suffixGender = "feminine";
		} else if (suffixGender.equals("MF")) {
			suffixGender = "masculine and feminine";
		}
		return suffixGender;
	}

	public static String getNumberPGN(String PGN) {
		String suffixNumber = "";
		StringTokenizer st = new StringTokenizer(PGN, "/");
		st.nextToken();
		st.nextToken();
		suffixNumber = st.nextToken();
		if (suffixNumber.startsWith("S")) {
			suffixNumber = "singular";
		} else if (suffixNumber.startsWith("P")) {
			suffixNumber = "plural";
		}
		return suffixNumber;
	}

	public static String getPersonPGN(String PGN) {

		StringTokenizer st = new StringTokenizer(PGN, "/");
		String suffixPerson = String.valueOf(st.nextToken().charAt(0));
		return suffixPerson;
	}

	public static boolean gimatriaPossibility(String token) {
		boolean rt = false;
		int tokenLen = token.length();
		if (tokenLen > 1 && token.indexOf("\"") == tokenLen - 2 || token.endsWith("'")) {
			rt = true;
		}
		return rt;
	}

	public static boolean moshevkaleb(String possiblePrefix) {
		boolean rt = false;
		int possiblePrefixLen = possiblePrefix.length();
		if (possiblePrefixLen > 0) {
			char endChar = possiblePrefix.charAt(possiblePrefixLen - 1);
			/*if (endChar == '�' || endChar == '�') {
				return rt;
			}*/

			possiblePrefix = Translate.Heb2Eng(possiblePrefix);

			for (int j = 0; j < PREFIX_ARRAY_SIZE; j++) {
				if (prefixArray[j].equals(possiblePrefix)) {
					rt = true;
					break;
				}
			}
		}
		return rt;
	}

	public static void strIntegerNumtoNum() {

	}

	public static void main(String args[]) {
		String token = "2:30:07";
		try {
			System.out.println("2:30:07 is:" + StringUtils.analyzeNumberExpression(token));
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
