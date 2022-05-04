package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HotelDB {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	static HotelDB single = null;

	public static HotelDB getInstance() {
		if (single == null)
			single = new HotelDB();

		return single;
	}

	private HotelDB() {

	}

	// 커넥션 연결관리하는 메서드
	public Connection getConnection() throws SQLException {
		// connection 얻어오기
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "hotel";
		String pwd = "hotel";

		Connection connection = DriverManager.getConnection(url, user, pwd);
		return connection;
	}

}
