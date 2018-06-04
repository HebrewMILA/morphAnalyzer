/*
 * Created on 09/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.mw;

import java.net.URLDecoder;
import java.util.ArrayList;

import mila.lexicon.analyse.Data;
import mila.lexicon.dbUtils.MWEinflections;
import mila.lexicon.dbUtils.MWEinflectionsRecord;

/**
 * @author daliabo
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MWData extends Data {

	public static ArrayList<MWEinflectionsRecord> getMWEinflections(String key) throws Exception {
		ArrayList<MWEinflectionsRecord> mweList = new ArrayList<MWEinflectionsRecord>();
		ArrayList<MWEinflectionsRecord> returnedMweList = new ArrayList<MWEinflectionsRecord>();
		int mweListSize = 0;
		MWEinflectionsRecord mwinfRec = null;
		// get from databse
		if (webFlag) {
			// get from lists
			mweList = MWEinflections.get(key);
		} else {
			// get from inflection files !!!
			mweList = mwinflections.get(key);
		}

		if (mweList != null) {
			mweListSize = mweList.size();

		}
		for (int MWEListIndex = 0; MWEListIndex < mweListSize; MWEListIndex++) {
			mwinfRec = new MWEinflectionsRecord();
			mwinfRec = mweList.get(MWEListIndex);
			String surface = URLDecoder.decode(mwinfRec.getSurface(), "UTF-8");
			mwinfRec.setSurface(surface);
			returnedMweList.add(mwinfRec);
		}

		return returnedMweList;
	}

}
