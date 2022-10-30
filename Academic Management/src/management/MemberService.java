package management;

public class MemberService {
	private String id;
	private String password;
	private Person person;
	
	public MemberService() {
		
	}
	
	public MemberService(String id, String password, Person person) {
		this.id=id;
		this.password=password;
		this.person=person;
	}
}
