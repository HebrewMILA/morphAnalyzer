/*
 * Created on 10/10/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.lexicon.dbUtils;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author daliabo
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Prefixes extends Connected {
	PrefixRecord pr = null;

	public ArrayList<PrefixRecord> get(String input) throws UnsupportedEncodingException {
		ArrayList<PrefixRecord> result = new ArrayList<PrefixRecord>();
		String sql = "SELECT * FROM prefixes where prefix =?";
		// System.out.println("sql="+ sql);
		ResultSet rs = null;
		try {
			rs = getData(sql, input);
			if (rs != null) {

				while (rs.next()) {
					pr = new PrefixRecord();
					pr.setAdverbKAF(rs.getBoolean("adverbKAF"));
					pr.setDefArtHE(rs.getBoolean("defArtHE"));
					pr.setDefiniteArticleTag(rs.getBoolean("definiteArticleTag"));
					pr.setDescription(rs.getString("description"));
					pr.setPrefix(rs.getString("prefix"));
					pr.setPrefPartUnit(rs.getBoolean("prefPartUnit"));
					pr.setRelativizerTag(rs.getBoolean("relativizerTag"));
					pr.setRelHE(rs.getBoolean("relHE"));
					pr.setSubConOrRelSHIN(rs.getBoolean("subConOrRelSHIN"));
					pr.setSubordinatingConjunctionTag(rs.getBoolean("subordinatingConjunctionTag"));
					pr.setTemporalSubConjTag(rs.getBoolean("temporalSubConjTag"));
					pr.setTempSubConKAFSHIN(rs.getBoolean("tempSubConKAFSHIN"));
					pr.setTempSubConBETSHIN(rs.getBoolean("tempSubConBETSHIN"));
					pr.setTempSubConMEMSHIN(rs.getBoolean("tempSubConMEMSHIN"));
					pr.setTempSubConLAMEDKAFSHIN(rs.getBoolean("tempSubConLAMEDKAFSHIN"));
					pr.setPrepositionTag(rs.getBoolean("prepositionTag"));
					pr.setPrepBET(rs.getBoolean("prepBet"));
					pr.setPrepKAF(rs.getBoolean("prepKAF"));
					pr.setPrepLAMED(rs.getBoolean("prepLAMED"));
					pr.setPrepMEM(rs.getBoolean("prepMEM"));
					pr.setConjunctionTag(rs.getBoolean("conjunctionTag"));
					result.add(pr);
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

	// public boolean isExist(String input) throws SQLException {
	// boolean rt = false;
	//
	// if (input.equals("h"))
	// return true;
	// try{
	// String sql = "SELECT * FROM prefixes where prefix ='" + input + "'";
	// ResultSet rs = null;
	// rs = getData(sql);
	// if (rs != null){
	// //prefixes table doesn't contains definitness in the prefix - the
	// definiteness
	// //is marked by definiteArticleTag - so we need to check it in case the
	// possible
	// //prefix ends with - h
	// if (input.endsWith("h")) {
	// while (rs.next()) {
	// if (rs.getBoolean("definiteArticleTag")) {
	// return true;
	// }
	// }
	// } else{
	// while (rs.next()) {
	// return true;
	// }
	// }
	// //not related to �������
	// rt = false;
	// }
	// }finally {
	// releaseConnection();
	// }
	// return rt;
	// }
}
