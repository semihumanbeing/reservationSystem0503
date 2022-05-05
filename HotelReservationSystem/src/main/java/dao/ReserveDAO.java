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

	// �̱��ϰ�ü ����
	static ReserveDAO single = null;

	public static ReserveDAO getInstance() {
		if (single == null) {
			single = new ReserveDAO();
		}
		return single;
	}

	private ReserveDAO() {
		// �ܺο��� �������� �ʵ����ϱ�
	}

	public int addList(ReserveVO vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		String sql = "INSERT INTO RESERVE VALUES(NO_SEQ.NEXTVAL, ?, ?)";

		try {
			// 1. connection ������
			connection = HotelDB.getInstance().getConnection();
			
			// 2. pstmt ������
			pstmt = connection.prepareStatement(sql);
			
			// 2-1. �Ķ���� �����ϱ�
			
			pstmt.setString(1, vo.getName());
			pstmt.setInt(2, vo.getRoomNumber());
			
			// 3. result set ������
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
			// 1. connection ������
			connection = HotelDB.getInstance().getConnection();
			
			// 2. pstmt ������
			pstmt = connection.prepareStatement(sql);
			
			// 2-1. �Ķ���� �����ϱ�
			pstmt.setInt(1, vo.getCfmNumber());
			
			// 3. result set ������
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
