package mila.mw;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import mila.lexicon.analyse.Data;
import mila.lexicon.dbUtils.MWE1record;

public class MWE1data extends Data {
	public static ArrayList<MWE1record> getMWE1records(String key) {
		// System.out.println("(F)MWData:getMWEinflections("+key+")");
		ArrayList<MWE1record> mweList = new ArrayList<MWE1record>();
		ArrayList<MWE1record> returnedMweList = new ArrayList<MWE1record>();
		int mweListSize = 0;
		MWE1record mwinfRec = null;
		// get from databse

		// System.out.println("(F) getMWEinflections() get from inflection files
		// !!!");
		// get from inflection files !!!
		try {
			mweList = mwe1.get(key);
		} catch (Exception e1) {
			System.err.println("MWE1data.java Exception in key " + key + ", proceeding...");
			return returnedMweList;
			// e1.printStackTrace();
		}

		if (mweList != null) {
			mweListSize = mweList.size();

		}
		for (int MWEListIndex = 0; MWEListIndex < mweListSize; MWEListIndex++) {
			mwinfRec = new MWE1record();
			mwinfRec = mweList.get(MWEListIndex);
			String surface = null;
			try {
				surface = URLDecoder.decode(mwinfRec.getSurface(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("(F) getMWEinflections: surface = " +
			// surface);
			mwinfRec.setSurface(surface);
			returnedMweList.add(mwinfRec);
		}

		return returnedMweList;
	}
}
