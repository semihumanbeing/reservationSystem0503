package main;

import java.util.List;
import java.util.Scanner;

import dao.HotelDAO;
import dao.ReserveDAO;
import vo.HotelVO;
import vo.ReserveVO;

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
			System.out.printf("%d | %s | %d\n", vo.getRoomNumber(), vo.getRoomGrade(), vo.getRoomPrice());
		}
		
		System.out.println("입실하실 방 번호를 입력하세요");
		int roomNumber = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("이름을 입력하세요");
		String name = scanner.nextLine();

		System.out.printf("%s님. %d호에 예약하시겠습니까?\n결제하기:1\n취소:0\n", name, roomNumber);
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
			System.out.printf("%d | %s | %d | %d | %s\n", vo.getRoomNumber(), vo.getRoomGrade(), vo.getRoomPrice(),
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
