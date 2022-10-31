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
	Object object;
	
	
	public int login(String option) {
		int logined=0;
		System.out.println("[로그인]");
		
		try {
			System.out.print("아이디: ");
			String id = scanner.nextLine();
			System.out.print("비밀번호: ");
			String password = scanner.nextLine();

			int result = check(id ,password, option);
			
			if(result == 0) {
				if(option.equals("1")) {
					//학생에 대한 정보 얻기
					Student student = student_getInfo(id);
					if(student!=null) {
						System.out.println(student.getName()+"님이 로그인 했습니다.");
					} else {
						System.out.println("로그인 정보가 없습니다.");
					}
					
				} else if(option.equals("2")) {
					//직원에 대한 정보 얻기
					Staff staff = staff_getInfo(id);
					if(staff!=null) {
						System.out.println(staff.getName()+"님이 로그인 했습니다.");
					} else {
						System.out.println("로그인 정보가 없습니다.");
					}
				}
				logined=1;
			} else {
				if(result==1) {
					System.out.println("비밀번호가 일치하지 않습니다.");
					System.out.println();
					sub();
				} else if(result==2) {
					System.out.println("아이디가 존재하지 않습니다.");
					System.out.println();
				}
				logined=0;
			}
			

			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return logined;
	}
	
	public void join(String option) {
		connect();
		System.out.println("[회원 가입]");
		String id;
		try {
			while(true) {
				String sql;
				System.out.print("아이디: ");
				id = scanner.nextLine();
				if(option.equals("1")) {
					sql = ""+
							"SELECT * "+"FROM students "+"WHERE studentid = ?";
				} else {
					sql = ""+
							"SELECT * "+"FROM staffs "+"WHERE staffid = ?";
				}
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
			System.out.print("고유번호: ");
			int no = scanner.nextInt();
			scanner.nextLine();
			
			
			String sql_insert;
			if(option.equals("1")) {
				sql_insert = ""+
					"INSERT INTO students(studentid, studentpassword, studentname, studentage, studentemail, studentno)"+
					"VALUES(?,?,?,?,?,?)";
			} else {
				sql_insert = ""+
					"INSERT INTO staffs(staffid, staffpassword, staffname, staffage, staffemail, staffno)"+
					"VALUES(?,?,?,?,?,?)";
			}
				
			PreparedStatement pstmt = conn.prepareStatement(sql_insert);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setString(3, name);
			pstmt.setInt(4, age);
			pstmt.setString(5, email);
			pstmt.setInt(6, no);
			
			pstmt.executeUpdate();
			
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			unconnect();
		}
	}
	
	public int check(String id, String password, String option) {
		int result=2;
		connect();
		try {
			String sql=null;
			if(option.equals("1")) {
				sql = "{? = call student_login(?, ?)}";
			} else if(option.equals("2")) {
				sql = "{? = call staff_login(?, ?)}";
			}
			
			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, id);
			cstmt.setString(3, password);
			
			cstmt.execute();
			result=cstmt.getInt(1);

			cstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		unconnect();
		
		return result;
	
	}
	
	public String sub() {
		Banner.banner_sub();
		System.out.print("입력: ");
		String option = scanner.nextLine();
		return option;

	}
	
	public Student student_getInfo(String id) {
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
	
	public Staff staff_getInfo(String id) {
		connect();
		
		Staff staff=null;
		
		try {
			String sql = ""+"SELECT * "+
				"FROM staffs "+"WHERE staffid = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				staff = new Staff();
				staff.setId(id);
				staff.setPassword(rs.getString("staffpassword"));
				staff.setName(rs.getString("staffname"));
				staff.setAge(rs.getInt("staffage"));
				staff.setEmail(rs.getString("staffemail"));
				staff.setStaffNo(rs.getInt("staffno"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		unconnect();
		
		return staff;
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

