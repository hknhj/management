package management;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class Staff extends Person{
	private int staffNo;
	
	public Staff(String name, int age,  String email,
			String id, String password, int staffNo) {
		super(name,  age,  email,id, password);
		this.staffNo=staffNo;
	}
	

}
