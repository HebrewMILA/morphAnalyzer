package mila.mw;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import mila.lexicon.analyse.Data;
import mila.lexicon.dbUtils.MWErecord;

public class MWEdata extends Data {
	// ------------------------------------------------------------------------------------
	public static ArrayList<MWErecord> getMWErecords(String key, int tableNum) {
		// System.out.println("(F)MWData:getMWEinflections("+key+","+tableNum+")");
		ArrayList<MWErecord> mweList = new ArrayList<MWErecord>();
		ArrayList<MWErecord> returnedMweList = new ArrayList<MWErecord>();
		int mweListSize = 0;
		MWErecord mwinfRec = null;
		// get from databse

		// System.out.println("(F) getMWEinflections() get from inflection files
		// !!!");
		// get from inflection files !!!
		try {
			switch (tableNum) {
			case 2: {
				// System.out.println("(F)MWData:getMWEinflections() - case 2:
				// size = "+
				// mwe2.GetSize());
				mweList = mwe2.get(key);
				break;
			}
			case 3: {
				mweList = mwe3.get(key);
				break;
			}
			case 4: {
				mweList = mwe4.get(key);
				break;
			}
			}
		} catch (Exception e1) {
			System.err.println("MWEdata.java Exception in key " + key + ", proceeding...");
			return returnedMweList;
			// e1.printStackTrace();
		}

		if (mweList != null) {
			mweListSize = mweList.size();

		}
		for (int MWEListIndex = 0; MWEListIndex < mweListSize; MWEListIndex++) {
			mwinfRec = new MWErecord();
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
