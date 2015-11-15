package app.utils;

import java.sql.SQLException;

import totalcross.sql.Connection;
import totalcross.sql.DriverManager;
import totalcross.sys.Convert;
import totalcross.sys.Settings;

public class ConnectionFactory {
	
	public ConnectionFactory() {}

	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:sqlite:" + Convert.appendPath(Settings.appPath, "test.db"));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
