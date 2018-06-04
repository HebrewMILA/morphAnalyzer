/*
 * Created on 10/10/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.lexicon.dbUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author daliabo
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MWEinflections extends Connected {

	public static ArrayList<MWEinflectionsRecord> get(String input) {
		ArrayList<MWEinflectionsRecord> result = new ArrayList<MWEinflectionsRecord>();
		input = input.replaceAll("'", "\\\\'");
		/*****
		 * UPDATE checking input does not contain "\" character so it will not cause an
		 * SQL error - yossi 16.11.10
		 */
		if (input.endsWith("\\")) {
			// System.out.println("BEFORE \\ to \\\\ in " + input );
			input = input.replace("\\", "\\\\");
			// System.out.println("REPLACED \\ to \\\\ in " + input );
		}
		/***** UPDATE END */
		String sql = "SELECT * FROM mweinflections where transliterated ='" + input + "'";
		// System.out.println("sql="+ sql);
		ResultSet rs = null;
		try {
			rs = staticGetData(sql);
			if (rs != null) {
				while (rs.next()) {
					MWEinflectionsRecord mwInfRec = new MWEinflectionsRecord();
					mwInfRec.setTransliterated(rs.getString("transliterated"));
					mwInfRec.setSurface(rs.getString("surface"));
					mwInfRec.setPos(rs.getString("pos"));
					mwInfRec.setMweId(rs.getString("mweId"));
					mwInfRec.setType(rs.getString("type"));
					mwInfRec.setPrefix(rs.getBoolean("prefix"));
					result.add(mwInfRec);
				}
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			releaseConnection();
		}
		return result;
	}

}
