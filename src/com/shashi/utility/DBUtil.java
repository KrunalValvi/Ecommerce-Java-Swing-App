package com.shashi.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * DBUtil - Database utility class for connection management.
 * Provides singleton-like connection pooling and resource cleanup.
 * 
 * Configuration is loaded from application.properties file:
 * - db.driverName: JDBC driver class
 * - db.connectionString: Database URL
 * - db.username: Database user
 * - db.password: Database password
 * 
 * NOTE: This implementation uses a single static connection.
 * For production, consider using HikariCP or similar connection pooling library.
 * 
 * @author Shashi Kumar
 * @version 1.0
 */
public class DBUtil {
	private static Connection conn;

	public DBUtil() {
	}

	/**
	 * Provides a database connection.
	 * Reuses existing connection if available and open.
	 * Loads configuration from application.properties on first call.
	 * 
	 * Configuration properties:
	 * - db.driverName: MySQL JDBC driver (com.mysql.cj.jdbc.Driver)
	 * - db.connectionString: JDBC URL with host, port, database
	 * - db.username: Database username
	 * - db.password: Database password
	 * 
	 * @return Database Connection object, or null if connection fails
	 */
	public static Connection provideConnection() {

		try {
			// Check if connection is null or closed, then create new connection
			if (conn == null || conn.isClosed()) {
				// Load configuration from application.properties
				ResourceBundle rb = ResourceBundle.getBundle("application");
				String connectionString = rb.getString("db.connectionString");
				String driverName = rb.getString("db.driverName");
				String username = rb.getString("db.username");
				String password = rb.getString("db.password");
				
				try {
					// Load JDBC driver class
					Class.forName(driverName);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				// Establish new database connection
				conn = DriverManager.getConnection(connectionString, username, password);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * Close database connection.
	 * Currently disabled to maintain persistent connection.
	 * 
	 * TODO: Implement proper connection pooling and enable connection closing.
	 * 
	 * @param con Connection to close
	 */
	public static void closeConnection(Connection con) {
		/*
		 * TODO: Enable connection closing when connection pooling is implemented.
		 * try { if (con != null && !con.isClosed()) {
		 * 
		 * con.close(); } } catch (SQLException e) { 
		 * e.printStackTrace(); }
		 */
	}

	/**
	 * Close ResultSet resource.
	 * Safely closes ResultSet if not null and not already closed.
	 * 
	 * @param rs ResultSet to close
	 */
	public static void closeConnection(ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close PreparedStatement resource.
	 * Safely closes PreparedStatement if not null and not already closed.
	 * 
	 * @param ps PreparedStatement to close
	 */
	public static void closeConnection(PreparedStatement ps) {
		try {
			if (ps != null && !ps.isClosed()) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
