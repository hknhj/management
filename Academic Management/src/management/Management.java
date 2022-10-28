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
	
	private Scanner scanner = new Scanner(System.in);
	private Connection conn; 
	private Map<Integer, Student> studentMap = new HashMap<>();
	private Map<Integer, Staff> staffMap = new HashMap<>();
	
	public void login() {
		System.out.println("[로그인]");
		System.out.println("아이디: ");
		String id = scanner.nextLine();
		System.out.println("비밀번호: ");
		String password = scanner.nextLine();
		
		int result = check(id, password);

		if(result==0) {
			//login(id, password);
			//로그인
		} else if(result==1) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			System.out.println();
			login();
		} else {
			System.out.println("아이디가 존재하지 않습니다.");
			System.out.println();
			login();
		}
			
	}
	
	public int check(String id, String password) {
		int result=2;
		try {
			String sql = "{? = call user_login(?, ?)}";
			
			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, id);
			cstmt.setString(3, password);
			
			cstmt.execute();
			result=cstmt.getInt(1);
			System.out.println("result: "+result);
			
			cstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("result: "+result);
		return result;
	
	}
}
