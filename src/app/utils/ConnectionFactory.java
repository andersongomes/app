package app.utils;

import java.sql.SQLException;

import totalcross.sql.Connection;
import totalcross.sql.DriverManager;
import totalcross.sys.Convert;
import totalcross.sys.Settings;

public class ConnectionFactory {
	
	private static Connection dbcon;
	
	public ConnectionFactory() {}

	public static Connection getConnection() {
		try {
			dbcon = DriverManager.getConnection("jdbc:sqlite:" + Convert.appendPath(Settings.appPath, "test.db"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dbcon;
	}
}
