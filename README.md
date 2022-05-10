
# 미니프로젝트[호텔예약] 
<details>
<summary> 자바와 JDBC api를 사용해서 콘솔 프로그램을 만드는 미니프로젝트 </summary>
<div markdown="1">
  

  
![image](https://user-images.githubusercontent.com/99929191/167563645-21ee16b5-93d8-4a0f-8b99-f49a40681600.png)


## 목표

1. JDBC 사용하여 DB와 프로그램 연동
2. JAVA→ DB 로 CRUD 구현하기

## 주제

호텔 예약 관리 시스템 만들기

### DB설계
![image](https://user-images.githubusercontent.com/99929191/167563552-56f34614-de69-4c9b-b197-dd8fcee58ded.png)



### 프로그램구성

객실예약: 

객실 호수와 등급, 가격을 조회 → 객실테이블 조회

어떤 호수를 선택할지, 이름입력

예약취소:

취소하면 예약 테이블 요소 delete

객실상태조회:

호수,등급,예약자명 조회

+각자 추가하고 싶은 요소(원하면)

### 사용기술

Java 1.8

Oracle 10

JDBC14

입력: Scanner

출력: 콘솔 출력창

### 제작기간: 일주일

1일차 - 다함께 구체적인 db설계하기(ERD 사용)

2일차~6일차 - 각자 어느 부분을 만들었는지 매일 간단하게 보고

7일차 - 각자 프로그램 시연 혹은 실패후 막힌 부분 공유

</div>
</details>

<details>
<summary> 미니프로젝트[호텔예약] day 2 </summary>
<div markdown="1">

전체적인 과정에 대해 정리해보기:

1. 테이블만들기
2. DBConnection 클래스만들기
3. VO만들어서 변수명 지정하기
4. DAO에서 데이터 포장하기
5. Main에서 콘솔 구현하기

 ERD를 바탕으로 객실 테이블과 예약 테이블을 만들고,

PRIMARY KEY 와 FOREIGN KEY 를 지정해주었다.

## 1. 테이블만들기

객실 테이블

```sql
CREATE TABLE HOTEL
(
	ROOMNUMBER NUMBER(4),
	ROOMGRADE VARCHAR2(100),
	ROOMPRICE INT
)
/

ALTER TABLE HOTEL
	ADD CONSTRAINT HOTEL_ROOMNUMBER_PK PRIMARY KEY(ROOMNUMBER)
	

INSERT INTO HOTEL VALUES (101, 'DELUXE CITY VIEW', 166000);
INSERT INTO HOTEL VALUES (102, 'DELUXE CITY VIEW', 166000);
INSERT INTO HOTEL VALUES (103, 'DELUXE OCEAN VIEW', 188000);
INSERT INTO HOTEL VALUES (201, 'LUXURY CITY VIEW', 188000);
INSERT INTO HOTEL VALUES (202, 'LUXURY OCEAN VIEW', 220000);
INSERT INTO HOTEL VALUES (203, 'LUXURY OCEAN VIEW', 220000);
INSERT INTO HOTEL VALUES (301, 'SUITE OCEAN VIEW', 340000);
INSERT INTO HOTEL VALUES (302, 'SUITE SPECIAL VIEW', 370000);
INSERT INTO HOTEL VALUES (303, 'SUITE SPECIAL VIEW', 370000);
INSERT INTO HOTEL VALUES (401, 'PENTHOUSE', 550000);
```

객실 테이블에는 방번호, 등급, 금액을 넣고 방번호는 Primary Key 로 지정해주었다.

예약 테이블

```sql
CREATE TABLE RESERVE
(
	CFMNUMBER INT,
	NAME VARCHAR2(100),
	ROOMNUMBER NUMBER(4)
)

ALTER TABLE RESERVE
	ADD CONSTRAINT RESERVE_CFMNUMBER_PK PRIMARY KEY(CFMNUMBER)
	

ALTER TABLE RESERVE
	ADD CONSTRAINT RESERVE_ROOMNUMBER_FK FOREIGN KEY(ROOMNUMBER)
	REFERENCES HOTEL(ROOMNUMBER)
```

예약 테이블에는 예약번호, 예약자명, 방번호를 넣고 

예약번호는 PRIMARY KEY 를 지정해주고

ROOMNUMBER는 FOREIGN KEY 로 지정해주었다.

## 2. DBConnection 클래스만들기

```jsx
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
```

</div>
</details>

<details>
<summary> 미니프로젝트[호텔예약] day 4 </summary>
<div markdown="1">

## VO만들어서 변수명 지정하기

예약 테이블과 호텔의 VO를 다른 클래스에 저장해두었다.

- 예약 VO
    
    ```java
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
    ```
    
- 호텔 VO
    
    ```java
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
    ```
    

DAO를 설계하면서 SELECT 를 하거나 INSERT를 할 때 서로 다른 데이터가 출력되게 하고 싶어서 전체 숙박현황을 조회할 수 있는 CURRENT_ROOMS 와 AVAILABLE_ROOMS 라는 view를 만들었다.

view를 만들기 위해서는 primary key와 foreign key 로 테이블간의 관계를 형성해주어야하고 

system에서 view를 만들수있는 권한을 부여해 주어야 한다.

left outer join 을 하면 왼쪽 테이블은 전부 출력되고 오른쪽에 null 값이 있는건 null 로 출력이 된다.

null이 아닌 값을 넣고 싶어서 NVL 함수를 사용했다. NVL(개체,NULL일때 원하는 출력방식) 으로 입력하면 된다. 

숙박 가능한 방을 조회할 때에는 예약번호가 Null인 곳만 호출하도록 만들었다. null을 조건으로 넣기 위해서는 개체명 = NULL 이 아닌 개체명 IS NULL 로 조건을 걸어야 한다.

- 추가된 SQL VIEW
    
    ```sql
    -- 전체 숙박현황조회
    CREATE OR REPLACE VIEW CURRENT_ROOMS
    AS
    SELECT H.ROOMNUMBER, H.ROOMGRADE, H.ROOMPRICE,NVL(R.CFMNUMBER,0) 
    AS CFMNUMBER, NVL(R.NAME,' ')AS NAME
    FROM HOTEL H LEFT OUTER JOIN RESERVE R
    ON H.ROOMNUMBER = R.ROOMNUMBER
    ORDER BY H.ROOMNUMBER
    
    -- 숙박 가능한 방 조회
    CREATE OR REPLACE VIEW AVAILABLE_ROOMS
    AS
    SELECT H.ROOMNUMBER, H.ROOMGRADE, H.ROOMPRICE
    FROM HOTEL H LEFT OUTER JOIN RESERVE R
    ON H.ROOMNUMBER = R.ROOMNUMBER
    WHERE R.CFMNUMBER IS NULL
    ORDER BY H.ROOMNUMBER
    ```
    

## DAO에서 데이터 포장하기

DAO파일도 호텔의 DAO와 예약현황의 DAO를 따로 만들었다.

호텔 DAO

- SELECT ALL ROOMS
    
    ```sql
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
    ```
    
- SELECT AVAILABLE ROOMS
    
    ```sql
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
    ```
    

예약현황 DAO

- INSERT
    
    ```java
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
    ```
    
- DELETE
    
    ```java
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
    ```
    

## Main에서 콘솔 구현하기

![image](https://user-images.githubusercontent.com/99929191/167565193-f751d112-1fb9-4d4c-ae35-e138ee1cca80.png)

위와 같은 구조로 콘솔 프로그램을 구현했다.

값을 선택해야 할 일이 많아 매번 int를 만들지 않고 static 객체 select를 만들어서 모든 메서드가 사용할 수 있도록 만들었다.

select, insert, delete와 같은 큰 기능은 각각 메서드를 만들었다.

main에서 실행되면 initDisplay메서드가 실행되어 select값을 변경하고, 그 내용을 HotelReservasionSystem()으로 전달한다. 

선택된 Select값에 따라 각각의 메서드를 실행하는 원리이다.

- 메인 프로그램
    
    ```java
    
    public class HotelReservationSystem {
    	static int select;
    	Scanner scanner = new Scanner(System.in);
    
    	public HotelReservationSystem() {
    		do {
    			initDisplay();
    			if (select == 1) {
    				addReserveInfo(); // 객실예약
    			}
    			if (select == 2) {
    				deleteReserveInfo(); // 예약취소
    			}
    			if (select == 3) {
    				selectHotelVO(); // 전체객실조회
    			}
    		} while (select != 4);
    
    		System.out.println("-----이용해주셔서 감사합니다-----");
    		
    	}
    
    	private void deleteReserveInfo() {
    		System.out.println("-----예약취소를 선택하셨습니다.-----");
    		System.out.println("예약번호를 입력하세요");
    		int cfmNumber = scanner.nextInt();
    		
    		System.out.println("예약을 취소하시겠습니까?\n예약취소:1\n돌아가기:0");
    		select = scanner.nextInt();
    		
    		if(select ==1) {
    			ReserveVO vo = new ReserveVO(cfmNumber);
    			ReserveDAO.getInstance().deleteList(vo);
    			System.out.println("예약이 취소되었습니다.\n돌아가기:0");
    			select = scanner.nextInt();
    			if(select ==0) {
    				return;
    			}
    			
    		} else {
    			return;
    		}
    
    	}
    
    	private void addReserveInfo() {
    		List<HotelVO> list = HotelDAO.getInstance().selectAvailableRooms();
    		
    		System.out.println("-----객실예약을 선택하셨습니다.-----");
    		System.out.println("-----예약 가능한 객실을 조회합니다-----");
    		for (HotelVO vo : list) {
    			System.out.printf("%d | %s | %d\n", vo.getRoomNumber(), 
    vo.getRoomGrade(), vo.getRoomPrice());
    		}
    		
    		System.out.println("입실하실 방 번호를 입력하세요");
    		int roomNumber = scanner.nextInt();
    		scanner.nextLine();
    		
    		System.out.println("이름을 입력하세요");
    		String name = scanner.nextLine();
    
    		System.out.printf("%s님. %d호에 예약하시겠습니까?\n결제하기:1\n취소:0\n", 
    name, roomNumber);
    		select = scanner.nextInt();
    		if (select == 1) {
    		
    			ReserveVO vo = new ReserveVO(name, roomNumber);
    			ReserveDAO.getInstance().addList(vo);
    			System.out.println("예약되었습니다.");
    			
    			System.out.println("1: 다시 예약하기\n0: 처음으로 돌아가기");
    			select = scanner.nextInt();
    			if(select == 1) {
    				addReserveInfo();
    			} else {
    				return;
    			}
    		} else {
    			return;
    		}
    	}
    
    	private void selectHotelVO() { // 객실 조회하기
    		List<HotelVO> list = HotelDAO.getInstance().selectAllRooms();
    		System.out.println("-----전체 객실을 조회합니다-----");
    		System.out.println("방번호 |     객실등급     | 객실가격 | 예약번호 | 예약자명");
    		for (HotelVO vo : list) {
    			System.out.printf("%d | %s | %d | %d | %s\n", vo.getRoomNumber(), 
    					vo.getRoomGrade(), vo.getRoomPrice(),
    					vo.getCfmNumber(), vo.getName());
    		}
    		System.out.println("0: 돌아가기");
    
    		select = scanner.nextInt();
    		if (select == 0) {
    			return;
    		} else {
    			selectHotelVO();
    		}
    
    	}
    
    	public int initDisplay() { // 인트로 및 선택창
    
    		System.out.println("-----호텔에 오신것을 환영합니다-----");
    		System.out.println("1. 객실예약");
    		System.out.println("2. 예약취소");
    		System.out.println("3. 전체객실조회");
    		System.out.println("4. 종료하기");
    		select = scanner.nextInt();
    		scanner.nextLine();
    		return select;
    
    	}
    
    	public static void main(String[] args) {
    		new HotelReservationSystem();
    	}
    
    }
    ```

</div>
</details>
  
<details>
<summary> 미니프로젝트[호텔예약] day 6 </summary>
<div markdown="1">

콘솔로 웬만한 기능들은 구현을 했는데, 이제 예외처리 부분이 남았다.

### 원하는 수정사항들

1. 프로그램이 실행되고 원하는 숫자 이외에 다른 숫자나 문자가 들어왔을때 mismatch exception이 나와버리는 문제.
2. 방을 예약할때 같은 이름의 방을 예약하면 SQL exception이 나오는데, try catch 문 안에 있어도 exception이 출력되는 문제.

## 프로그램이 실행되고 원하는 숫자 이외에 다른 숫자나 문자가 들어왔을때 mismatch exception이 나와버리는 문제

### 변경 전

```java
public static void main(String[] args) {
		new HotelReservationSystem();
```

그냥 이 프로그램 전반을 try catch 로 묶어버리기로 했다.

### 변경후

```java
public static void main(String[] args) {
		try {
			new HotelReservationSystem();
		} catch (Exception e) {
			System.out.println("잘못된 입력입니다.");
			new HotelReservationSystem();
		}
```

catch 에서는 잘못된 입력이라고 알려준 뒤 프로그램이 재실행된다.

## 방을 예약할때 같은 이름의 방을 예약하면 SQL exception이 나오는데, try catch 문 안에 있어도 exception이 출력되는 문제.

## 변경전

```java
private void addReserveInfo() {
		List<HotelVO> list = HotelDAO.getInstance().selectAvailableRooms();

		System.out.println("-----객실예약을 선택하셨습니다.-----");
		System.out.println("-----예약 가능한 객실을 조회합니다-----");
		for (HotelVO vo : list) {
			System.out.printf("%d | %s | %d\n", vo.getRoomNumber(), vo.getRoomGrade(), 
			vo.getRoomPrice());
		}

		System.out.println("입실하실 방 번호를 입력하세요");
		int roomNumber = scanner.nextInt();
		scanner.nextLine();

		System.out.println("이름을 입력하세요");
		String name = scanner.nextLine();
```

## 변경후

```java
private void addReserveInfo() {
		List<HotelVO> list = HotelDAO.getInstance().selectAvailableRooms();
		List<Integer> availableRooms = new ArrayList<Integer>();

		System.out.println("-----객실예약을 선택하셨습니다.-----");
		System.out.println("-----예약 가능한 객실을 조회합니다-----");
		for (HotelVO vo : list) {
			System.out.printf("%d | %s | %d\n", vo.getRoomNumber(), vo.getRoomGrade(),
			 vo.getRoomPrice());
			availableRooms.add(vo.getRoomNumber());
		}

		System.out.println("입실하실 방 번호를 입력하세요");
		int roomNumber = scanner.nextInt();
		scanner.nextLine();

		if(!availableRooms.contains(roomNumber)) {
			System.out.println("이미 예약된 방입니다.");
			return;
		}

		System.out.println("이름을 입력하세요");
		String name = scanner.nextLine();
```

try catch를 dao에 잡아야 하는건가 고민하고 있었는데 알아보니 Exception은 마지막 수단같은것이고 프로그램을 짤 때에는 예외구간에 도달하지 않기 위해 미리 체크하는 것이 중요하다고 한다.

예약 가능한 객실을 조회하는 부분에서 가능한 방의 목록을 ArrayList에 저장해두고 입실할 방을 선택할 때 가능한 방 목록에 내가 선택한 방 번호가 포함되어있지 않으면 이미 예약된 방이라고 하면서 첫 화면으로 return 하도록 했다.

</div>
</details>
