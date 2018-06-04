package mila.lexicon.dbUtils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.resource.spi.ConnectionManager;
import javax.sql.DataSource;

import snaq.db.ConnectionPool;

/**
 * The Connected class represent an object which has Database connectivity
 * abilities, through the JDBC standard and using a Connection Pool. Extending
 * the class will result in immidiate Database connectivity, based on a common
 * (static) configuration which is loaded during the creation of the first
 * instance of the object. Further instanciations would not result in reloading
 * of the configuration file. In order to take into account updates in the
 * configuration file, the system needs to be rebooted, in order to create
 * (again) the first <code>Connected</code> instance.
 * <p>
 * In order to learn more about the connection pool configuration, please seek
 * <b>bitmechanic</b> and find out.
 * <p>
 *
 * @author Danny Shacham
 * @version 1.0
 */
public class Connected {
	static Properties properties = new Properties();
	static String mysqlUser = "";
	static String mysqlPassword = "";
	static String myurl = "";
	private static ConnectionPool pool = null;

	static {
		try {
			Driver driver = (Driver) Class.forName("org.mariadb.jdbc.Driver").newInstance();
			DriverManager.registerDriver(driver);
			// pool = new ConnectionPool("mysqlLexiocn", 10, 20, 18000, myurl,
			// mysqlUser, mysqlPassword);

			// pool = new ConnectionPool("mysqlLexiocn", 10, 20, 18000,
			// "jdbc:mysql://yeda.cs.technion.ac.il:3306/generator",
			// "dummy1", "health&happiness");

			// pool = new ConnectionPool("mysqlLexiocn", 10, 20, 18000,
			// "jdbc:mysql://localhost/lexicon_06_08_09",
			// "root", "dalia");

			// pool = new ConnectionPool("mysqlLexiocn", 10, 20, 18000,
			// "jdbc:mysql://yeda.cs.technion.ac.il:3306/generatorTest",
			// "tommy", "tammy2010!)");
			pool = new ConnectionPool("mysqlLexiocn", 10, 20, 18000,
					"jdbc:mariadb://yeda.cs.technion.ac.il:3306/generatorTest", "morphuser", "qetu");
			pool.setCaching(false);
			// pool = new ConnectionPool("mysqlLexiocn", 10, 20, 18000,
			// "jdbc:mysql://yeda.cs.technion.ac.il:3306/mwGenerator",
			// "gili", "dr7R2c8edru");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static DataSource cpds;
	/**
	 * A Connection object used by the object and it's extentions in order to
	 * connect to the DB. The connection is created and released by the
	 * <code>ConnectionManager</code> object.
	 *
	 * @see ConnectionManager
	 */
	protected static Connection conn = null;

	/**
	 * A Statement object used to commit a DB command or to retrieve data from the
	 * DB. The object must be closed after the execution of the DB transaction,
	 * using <code>stmt.close()</code>. The object is created by the
	 * <code>Connection</code> object.
	 *
	 * @see java.sql.Connection
	 * @see #conn
	 */
	protected static Statement stmt = null;

	/**
	 * Serves as a super method for DB actions that require an "execute" mode, such
	 * as INSERT ,UPDATE or DELETE.
	 *
	 * @param sql
	 *           The SQL statement to be executed.
	 * @return Number of rows affected (0, if nothing happened, 1 if one row added,
	 *         ..., -1 if the statement is a SELECT statement).
	 */
	protected static int execute(String sql) {
		if (sql == null) {
			return 0;
		}
		int feedback = 0;
		try {
			System.out.println("Preparing connection for query: " + sql);
			conn = prepareConnection();
			stmt = conn.createStatement();
			feedback = stmt.executeUpdate(sql);
		} catch (SQLException E) {
			System.out.println(sql);
			E.printStackTrace();
		} finally {
			System.out.println("Releasing connection for query: " + sql);
			releaseConnection();
		}
		return feedback;
	}

	public static synchronized DataSource getCPDS() {
		return cpds;
	}

	/**
	 * Opens the <code>conn</code> Connection object. The method default opening
	 * method is using the <code>DriverManager.getConnection</code> method, using a
	 * connection pool to retrieve an open conenction from. If the variable
	 * <code>directConnection</code> is used, then the method would open a
	 * conenction directly, using the <code>prepareDirectConnection</code> method.
	 *
	 * @see DriverManager#getConnection
	 * @see #conn
	 * @see #prepareDirectConnection
	 */
	protected static Connection prepareConnection() throws SQLException {
		/*
		 * if (cpds == null) { Context ctx = null; try { System.out.println(
		 * "*****   Starting the DB connection!   ****"); ctx = new InitialContext();
		 * if(ctx == null ) System.out.println( "Boom - no cotext"); Context envCtx =
		 * (Context) ctx.lookup("java:comp/env"); DataSource ds = (DataSource)
		 * envCtx.lookup("jdbc/mysqlDBlexicon"); Connected.setCPDS(ds);
		 *
		 * } catch (NamingException ne) { System.out.println(
		 * "Exception in creating the DataSource!"); ne.printStackTrace(); } } conn =
		 * getCPDS().getConnection();
		 */
		long timeout = 2000; // 2 second timeout
		Connection conn = pool.getConnection(timeout);
		if (conn == null) {
			throw new SQLException();
		}
		return conn;
	}

	/**
	 * The method release a connection, used by the current Content object. The
	 * releasing is done by calling <code>conn.close()</code>. Please note that the
	 * use of some conenction pooling devices may cause the connection not to be
	 * actually closed, even if this action is performed.
	 *
	 * @see Connection#close
	 * @see #prepareConenction
	 */
	protected static void releaseConnection() {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException E) {
			E.printStackTrace();
		}
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				conn = null;
			}
		} catch (SQLException E) {
			E.printStackTrace();
		}
	}

	public static void setCPDS(DataSource cpds) {
		Connected.cpds = cpds;
	}

	protected static ResultSet staticGetData(String sql) {
		ResultSet rs = null;
		try {
			conn = prepareConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
			rs = stmt.getResultSet();
		} catch (SQLException E) {
			System.out.println("Lexicon Message: Content.getData Error" + sql);
			E.printStackTrace();
		} finally {
			releaseConnection();
		}
		return rs;
	}

	protected static ResultSet staticGetData(String query, String... params) {
		ResultSet rs = null;
		try {
			conn = prepareConnection();
			PreparedStatement stmt = conn.prepareStatement(query);
			for (int i = 1; i <= params.length; i++) {
				stmt.setString(i, params[i - 1]);
			}
			stmt.execute();
			rs = stmt.getResultSet();
		} catch (SQLException E) {
			String paramsOut = "";
			for (String param : params) {
				paramsOut += " " + param;

			}
			System.err.println("Lexicon Message: Content.getData Error trying to excute:\n" + query + paramsOut);
			E.printStackTrace();
		} finally {
			releaseConnection();
		}
		return rs;

	}

	/**
	 * Commit a SELECT statement and returns a <code>ResultSet</code> containing the
	 * query output. Calls <code>connect()</code> in order to connect with the DB
	 * and commit the statement using <code>Statement.executeQuery</code> . There
	 * might be a performence problem becuase the method does not close the
	 * <code>ResultSet</code>, <code>Statement</code> and <code>Connection</code>
	 * objects.
	 *
	 * @param sql
	 *           The SQL statement to be executed
	 * @return The ResultSet recieved from the DB
	 */
	protected ResultSet getData(String sql) {
		return Connected.staticGetData(sql);
	}

	protected ResultSet getData(String query, String... params) {
		return Connected.staticGetData(query, params);
	}

}
