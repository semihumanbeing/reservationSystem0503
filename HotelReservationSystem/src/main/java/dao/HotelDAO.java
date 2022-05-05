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
		// 싱글턴 객체는 한번만 생성되어 실행되기 때문에
		// 프라이빗 생성자를 만들어서 외부에서 생성되지 않도록 한다.
	}

	public List<HotelVO> selectAvailableRooms() { //숙박가능한 방만 표시
		List<HotelVO> list = new ArrayList<HotelVO>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from available_rooms";

		try {

			// connection 얻어오기
			connection = HotelDB.getInstance().getConnection();

			// prepared statement 얻어오기
			pstmt = connection.prepareStatement(sql);

			// result set 구하기
			rs = pstmt.executeQuery();

			// 값을 포장해서 ArrayList에 집어넣기
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
			// 열려있으면 닫아주기
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
	
	public List<HotelVO> selectAllRooms() { // 모든 방의 숙박현황 조회
		List<HotelVO> list = new ArrayList<HotelVO>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from current_rooms";

		try {

			// connection 얻어오기
			connection = HotelDB.getInstance().getConnection();

			// prepared statement 얻어오기
			pstmt = connection.prepareStatement(sql);

			// result set 구하기
			rs = pstmt.executeQuery();

			// 값을 포장해서 ArrayList에 집어넣기
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
			// 열려있으면 닫아주기
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
