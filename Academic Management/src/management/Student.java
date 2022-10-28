package management;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class Student extends Person {
	private int studentNo;
	
	public Student() {
		super();
	}
	
	public Student(String id, String password,String name, 
			int age, String email, int studentNo) {
		super(id, password, name, age, email);
		this.studentNo=studentNo;
	}
}
