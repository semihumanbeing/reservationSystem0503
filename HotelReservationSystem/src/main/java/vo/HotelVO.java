package vo;

public class HotelVO {

	int roomNumber;
	String roomGrade;
	int roomPrice;
	int cfmNumber;
	String name;
	
	

	public int getCfmNumber() {
		
		return cfmNumber;
	}

	public void setCfmNumber(int cfmNumber) {
		this.cfmNumber = cfmNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomGrade() {
		return roomGrade;
	}

	public void setRoomGrade(String roomGrade) {
		this.roomGrade = roomGrade;
	}

	public int getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(int roomPrice) {
		this.roomPrice = roomPrice;
	}

}
