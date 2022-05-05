package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.HotelDB;
import vo.HotelVO;

public class HotelDAO {

	static HotelDAO single = null;

	public static HotelDAO getInstance() {
		if (single == null) {
			single = new HotelDAO();
		}

		return single;
	}

	private HotelDAO() {
		// �̱��� ��ü�� �ѹ��� �����Ǿ� ����Ǳ� ������
		// �����̺� �����ڸ� ���� �ܺο��� �������� �ʵ��� �Ѵ�.
	}

	public List<HotelVO> selectAvailableRooms() { //���ڰ����� �游 ǥ��
		List<HotelVO> list = new ArrayList<HotelVO>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from available_rooms";

		try {

			// connection ������
			connection = HotelDB.getInstance().getConnection();

			// prepared statement ������
			pstmt = connection.prepareStatement(sql);

			// result set ���ϱ�
			rs = pstmt.executeQuery();

			// ���� �����ؼ� ArrayList�� ����ֱ�
			while (rs.next()) {
				int roomNumber = rs.getInt("roomNumber");
				String roomGrade = rs.getString("roomGrade");
				int roomPrice = rs.getInt("roomPrice");

				HotelVO vo = new HotelVO();
				vo.setRoomNumber(roomNumber);
				vo.setRoomGrade(roomGrade);
				vo.setRoomPrice(roomPrice);

				list.add(vo);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// ���������� �ݾ��ֱ�
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;

	}
	
	public List<HotelVO> selectAllRooms() { // ��� ���� ������Ȳ ��ȸ
		List<HotelVO> list = new ArrayList<HotelVO>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from current_rooms";

		try {

			// connection ������
			connection = HotelDB.getInstance().getConnection();

			// prepared statement ������
			pstmt = connection.prepareStatement(sql);

			// result set ���ϱ�
			rs = pstmt.executeQuery();

			// ���� �����ؼ� ArrayList�� ����ֱ�
			while (rs.next()) {
				int roomNumber = rs.getInt("roomNumber");
				String roomGrade = rs.getString("roomGrade");
				int roomPrice = rs.getInt("roomPrice");
				int cfmNumber = rs.getInt("cfmNumber");
				String name = rs.getString("name");

				HotelVO vo = new HotelVO();
				vo.setRoomNumber(roomNumber);
				vo.setRoomGrade(roomGrade);
				vo.setRoomPrice(roomPrice);
				vo.setCfmNumber(cfmNumber);
				vo.setName(name);

				list.add(vo);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// ���������� �ݾ��ֱ�
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;

	}

}
