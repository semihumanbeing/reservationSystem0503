package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.HotelDB;
import vo.ReserveVO;

public class ReserveDAO {

	// 싱글턴객체 생성
	static ReserveDAO single = null;

	public static ReserveDAO getInstance() {
		if (single == null) {
			single = new ReserveDAO();
		}
		return single;
	}

	private ReserveDAO() {
		// 외부에서 생성되지 않도록하기
	}

	public int addList(ReserveVO vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		String sql = "INSERT INTO RESERVE VALUES(NO_SEQ.NEXTVAL, ?, ?)";

		try {
			// 1. connection 얻어오기
			connection = HotelDB.getInstance().getConnection();
			
			// 2. pstmt 얻어오기
			pstmt = connection.prepareStatement(sql);
			
			// 2-1. 파라미터 세팅하기
			
			pstmt.setString(1, vo.getName());
			pstmt.setInt(2, vo.getRoomNumber());
			
			// 3. result set 얻어오기
			rs = pstmt.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs;

	}
	public int deleteList(ReserveVO vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		String sql = "DELETE FROM RESERVE WHERE CFMNUMBER = ?";

		try {
			// 1. connection 얻어오기
			connection = HotelDB.getInstance().getConnection();
			
			// 2. pstmt 얻어오기
			pstmt = connection.prepareStatement(sql);
			
			// 2-1. 파라미터 세팅하기
			pstmt.setInt(1, vo.getCfmNumber());
			
			// 3. result set 얻어오기
			rs = pstmt.executeUpdate();
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs;

	}


}
