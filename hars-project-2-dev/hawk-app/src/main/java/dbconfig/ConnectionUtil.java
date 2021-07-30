package dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to establish connections to the RDBMS
 * Throws SQLException if it can't establish a connection
 * Has a static method which uses environment variables to return a Connection object
 * */
public class ConnectionUtil {

	public static Connection getConnection() throws SQLException {

		return DriverManager.getConnection(
				System.getenv("db_url2"), 
				System.getenv("db_username"),
				System.getenv("db_password"));


	}
}
