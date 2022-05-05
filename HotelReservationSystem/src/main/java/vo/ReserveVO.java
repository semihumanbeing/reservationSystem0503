package vo;

public class ReserveVO {

	int cfmNumber;
	String name;
	int roomNumber;
	
	
	
	public ReserveVO() {
		super();
	}


	public int getCfmNumber() {
		return cfmNumber;
	}


	public void setCfmNumber(int cfmNumber) {
		this.cfmNumber = cfmNumber;
	}
	
	public ReserveVO(String name, int roomNumber){
		
		this.name = name;
		this.roomNumber = roomNumber;
	}


	public ReserveVO(int cfmNumber) {
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

}
