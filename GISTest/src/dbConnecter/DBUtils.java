package dbConnecter;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {
	public static Connection getConnection() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/GISDatabase?user=root&password=root");
			return conn;
		} catch (Exception e) {
			throw e;
		}
	}
}
