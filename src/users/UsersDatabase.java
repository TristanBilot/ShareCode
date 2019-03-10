package users;

import java.util.ArrayList;
import java.util.List;

public class UsersDatabase {
	
	public static final String DEFAULT_FTP_URL = "ftp://localhost:4446/";
	public static List<Amateur> amateurs = new ArrayList<Amateur>();
	public static List<Programmer> programmers = new ArrayList<Programmer>();
	public static int CPT_AMATEURS = 0;
	public static int CPT_PROGRAMMERS = 0;
	
	public static void addAmateur(Amateur a) {
		amateurs.add(a);
		CPT_AMATEURS++;
	}
	
	public static void addProgrammer(Programmer p) {
		programmers.add(p);
		CPT_PROGRAMMERS++;
	}
	
	public static Amateur getAmateur(int index) {
		return amateurs.get(index);
	}
	
	public static Programmer getProgrammer(int index) {
		return programmers.get(index);
	}
	
	public static void initSomeUsers() {
		for (int i = 0; i < 10; i++) {
			addAmateur(new Amateur("amateur" + i, "password" + i));
			addProgrammer(new Programmer("login" + i, "password" + i, "ftp://localhost:4446/"));
		}
		addProgrammer(new Programmer("a", "a", "ftp://localhost:4446/"));
	}
	
	public static boolean amateurExists(String login) {
		for (Amateur a:amateurs) {
			if (a.getLogin().equals(login))
					return true;
		}
		return false;
	}
	
	public static boolean programmerExists(String login) {
		for (Programmer a:programmers) {
			if (a.getLogin().equals(login))
					return true;
		}
		return false;
	}
	
	public static Amateur getAmateur(String login) {
		for (Amateur a:amateurs) {
			if (a.getLogin().contentEquals(login))
				return a;			
		}
		return null;
	}
	
	public static Programmer getProgrammer(String login) {
		for (Programmer a:programmers) {
			if (a.getLogin().contentEquals(login))
				return a;			
		}
		return null;
	}

}
