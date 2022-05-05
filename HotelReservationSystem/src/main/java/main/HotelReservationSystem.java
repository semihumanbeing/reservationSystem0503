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
				addReserveInfo(); // ���ǿ���
			}
			if (select == 2) {
				deleteReserveInfo(); // �������
			}
			if (select == 3) {
				selectHotelVO(); // ��ü������ȸ
			}
		} while (select != 4);

		System.out.println("-----�̿����ּż� �����մϴ�-----");
		
	}

	private void deleteReserveInfo() {
		System.out.println("-----������Ҹ� �����ϼ̽��ϴ�.-----");
		System.out.println("�����ȣ�� �Է��ϼ���");
		int cfmNumber = scanner.nextInt();
		
		System.out.println("������ ����Ͻðڽ��ϱ�?\n�������:1\n���ư���:0");
		select = scanner.nextInt();
		
		if(select ==1) {
			ReserveVO vo = new ReserveVO(cfmNumber);
			ReserveDAO.getInstance().deleteList(vo);
			System.out.println("������ ��ҵǾ����ϴ�.\n���ư���:0");
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
		
		System.out.println("-----���ǿ����� �����ϼ̽��ϴ�.-----");
		System.out.println("-----���� ������ ������ ��ȸ�մϴ�-----");
		for (HotelVO vo : list) {
			System.out.printf("%d | %s | %d\n", vo.getRoomNumber(), vo.getRoomGrade(), vo.getRoomPrice());
		}
		
		System.out.println("�Խ��Ͻ� �� ��ȣ�� �Է��ϼ���");
		int roomNumber = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("�̸��� �Է��ϼ���");
		String name = scanner.nextLine();

		System.out.printf("%s��. %dȣ�� �����Ͻðڽ��ϱ�?\n�����ϱ�:1\n���:0\n", name, roomNumber);
		select = scanner.nextInt();
		if (select == 1) {
		
			ReserveVO vo = new ReserveVO(name, roomNumber);
			ReserveDAO.getInstance().addList(vo);
			System.out.println("����Ǿ����ϴ�.");
			
			System.out.println("1: �ٽ� �����ϱ�\n0: ó������ ���ư���");
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

	private void selectHotelVO() { // ���� ��ȸ�ϱ�
		List<HotelVO> list = HotelDAO.getInstance().selectAllRooms();
		System.out.println("-----��ü ������ ��ȸ�մϴ�-----");
		System.out.println("���ȣ |     ���ǵ��     | ���ǰ��� | �����ȣ | �����ڸ�");
		for (HotelVO vo : list) {
			System.out.printf("%d | %s | %d | %d | %s\n", vo.getRoomNumber(), vo.getRoomGrade(), vo.getRoomPrice(),
					vo.getCfmNumber(), vo.getName());
		}
		System.out.println("0: ���ư���");

		select = scanner.nextInt();
		if (select == 0) {
			return;
		} else {
			selectHotelVO();
		}

	}

	public int initDisplay() { // ��Ʈ�� �� ����â

		System.out.println("-----ȣ�ڿ� ���Ű��� ȯ���մϴ�-----");
		System.out.println("1. ���ǿ���");
		System.out.println("2. �������");
		System.out.println("3. ��ü������ȸ");
		System.out.println("4. �����ϱ�");
		select = scanner.nextInt();
		scanner.nextLine();
		return select;

	}

	public static void main(String[] args) {
		new HotelReservationSystem();
	}

}
