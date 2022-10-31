package management;

public class Banner {
	public static void banner() {
		System.out.println("--------------------");
		System.out.println("1.Student | 2.Staff");
		System.out.println("--------------------");
		
	}
	
	public static void banner_person(String option) {
		if(option.equals("1")) {
			System.out.println("----------------------------");
			System.out.println("[학생 전용]");
			System.out.println();
			System.out.println("1.Login | 2.Join | 3.Exit");
			System.out.println("----------------------------");
		}
		else if(option.equals("2")) {
			System.out.println("----------------------------");
			System.out.println("[교직원 전용]");
			System.out.println();
			System.out.println("1.Login | 2.Join | 3.Exit");
			System.out.println("----------------------------");
		}
		
	}
	
	public static void banner_sub() {
		System.out.println("-----------------");
		System.out.println("1.Back | 2.Exit");
		System.out.println("-----------------");
	}
}
