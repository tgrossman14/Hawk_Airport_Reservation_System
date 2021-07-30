package dbconfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ResourceClosers class to define helper static methods for easily closing resources
 * */
public class ResourceClosers {

	/**
	 * Static method to close the Connection object, catches SQLExceptions
	 * */
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Static method to close the Statement object, catches SQLExceptions
	 * */
	public static void closeStatement(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Static method to close the ResultSet object, catches SQLExceptions
	 * */
	public static void closeResultSet(ResultSet set) {
		try {
			set.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}