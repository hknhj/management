package management;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
	private String id;
	private String password;
	private String name;
	private int age;
	private String email;
	
}
