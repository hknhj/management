package management;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Management {
	//우선 로그인 기능 구현하기
	private Scanner scanner = new Scanner(System.in);
//	private Connection conn; 
//	private Map<Integer, Student> studentMap = new HashMap<>();
//	private Map<Integer, Staff> staffMap = new HashMap<>();
	
	public void login() {
		Login login = new Login();
		while(true) {
			//배너 출력하기
			Banner.banner();
			System.out.print("입력: ");
			String option = scanner.nextLine();
			if(option.equals("1")) {
				login.login();
			} else if(option.equals("2")) {
				login.join();
			} else {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		Management management = new Management();
		management.login();
	}
	
}
