package management;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.ResultSet;


public class Login {
	
	Scanner scanner = new Scanner(System.in);
	Connection conn;
	
	
	public void login() {
		System.out.println("[로그인]");
		
		try {
			while(true) {
				System.out.print("아이디: ");
				String id = scanner.nextLine();
				System.out.print("비밀번호: ");
				String password = scanner.nextLine();
				
				int result = check(id ,password);
				System.out.println(result);
				
				if(result==0) {
					Student student = getInfo(id);
					if(student!=null) {
						System.out.println(student.getName()+"님이 로그인했습니다.");
					} else {
						System.out.println("로그인 정보가 없습니다.");
					}
					break;
				} else if(result==1) {
					System.out.println("비밀번호가 일치하지 않습니다.");
					System.out.println();
				} else {
					System.out.println("아이디가 존재하지 않습니다.");
					System.out.println();
				}
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public void join() {
		connect();
		System.out.println("[회원 가입]");
		String id;
		try {
			while(true) {

				System.out.print("아이디: ");
				id = scanner.nextLine();
				String sql = ""+
					"SELECT * "+"FROM students "+"WHERE studentid = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					System.out.println("이미 존재하는 아이디입니다. 다시 입력하세요.");
					System.out.println();
				} else {

					rs.close();
					pstmt.close();
					break;
				}
			}
			System.out.print("비밀번호: ");
			String password = scanner.nextLine();
			System.out.print("이름: ");
			String name = scanner.nextLine();
			System.out.print("나이: ");
			int age = scanner.nextInt();
			scanner.nextLine();
			System.out.print("이메일: ");
			String email = scanner.nextLine();
			System.out.print("학번: ");
			int studentNo = scanner.nextInt();
			scanner.nextLine();
			
			String sql_insert = ""+
				"INSERT INTO students(studentid, studentpassword, studentname, studentage, studentemail, studentno)"+
				"VALUES(?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql_insert);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setString(3, name);
			pstmt.setInt(4, age);
			pstmt.setString(5, email);
			pstmt.setInt(6, studentNo);
			
			pstmt.executeUpdate();
			
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			unconnect();
		}
	}
	
	public int check(String id, String password) {
		int result=2;
		connect();
		try {
			String sql = "{? = call student_login(?, ?)}";
			
			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, id);
			cstmt.setString(3, password);
			
			cstmt.execute();
			result=cstmt.getInt(1);
			//System.out.println("result: "+result);
			
			cstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		unconnect();
		
		return result;
	
	}
	
	public Student getInfo(String id) {
		connect();
		
		Student student=null;
		
		try {
			String sql = ""+"SELECT * "+
					"FROM students "+"WHERE studentid = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				student = new Student();
				student.setId(id);
				student.setPassword(rs.getString("studentpassword"));
				student.setName(rs.getString("studentname"));
				student.setAge(rs.getInt("studentage"));
				student.setEmail(rs.getString("studentemail"));
				student.setStudentNo(rs.getInt("studentno"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		unconnect();
		
		return student;
		
	}
		
	public void connect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/orcl",
					"java",
					"oracle"
					);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void unconnect() {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {}
		}
	}
}

