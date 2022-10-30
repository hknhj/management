package management;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class Staff extends Person{
	private int staffNo;
	
	public Staff(String id, String password,
			String name, int age,  String email,
			 int staffNo) {
		super(id, password, name,  age,  email);
		this.staffNo=staffNo;
	}
	

}
