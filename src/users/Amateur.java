package users;

public class Amateur extends User {
	
	private String login;
	private String password;
	private boolean connected;

	/*
	 * The amateur can have a login and a password to use the mail service for example 
	 */
	public Amateur(String login, String password) {
		if (UsersDatabase.amateurExists(login))
			new UserException("An user with this login already exists !");
		this.login = login;
		this.password = password;
		this.connected = false;
		UsersDatabase.addAmateur(this);
	}
	
	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
