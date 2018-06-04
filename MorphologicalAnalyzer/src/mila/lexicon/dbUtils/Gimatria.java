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
public class Gimatria extends Connected {

	public static int get(String input) {
		int val = -1;
		if (input.indexOf("\"") != -1) {
			input = input.replaceAll("\"", "%22");
		} else if (input.indexOf("\'") != -1) {
			input = input.replaceAll("\'", "\\\\'");
		}
		String sql = "SELECT * FROM gimatria where transliterated =?";
		try {
			ResultSet rs = staticGetData(sql, input);
			if (rs != null) {
				while (rs.next()) {
					val = rs.getInt("val");
				}
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			releaseConnection();
		}
		return val;
	}

	public ArrayList<String> getAll() {
		String sql = "SELECT * FROM gimatria";
		try {
			ArrayList<String> array = new ArrayList<String>();
			ResultSet rs = getData(sql);
			if (rs != null) {
				while (rs.next()) {
					array.add(rs.getString("transliterated"));
				}
				rs.close();
			}
			return array;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			releaseConnection();
		}
		return null;
	}

}
