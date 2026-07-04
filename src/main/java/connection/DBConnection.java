package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.ErrorUtil;

public class DBConnection {
	//define and initialize database driver
	private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	//define and initialize database url
	private static final String ORACLE_URL = "jdbc:oracle:thin:@//localhost:1521/freepdb1";
	
	//define and initialize database user
	private static final String ORACLE_USER = "CSC584";
	
	//define and initialize database password
	private static final String ORACLE_PASS = "0000";
	
	public static Connection getConnection() throws SQLException {

		//1. Load the database driver
		try {
			Class.forName(ORACLE_DRIVER);
		} catch (ClassNotFoundException e) {
			ErrorUtil.log("DBConnection.java", "getConnection", e);
		}
		
        return DriverManager.getConnection(
            ORACLE_URL,
            ORACLE_USER,
            ORACLE_PASS
        );
    }
}
