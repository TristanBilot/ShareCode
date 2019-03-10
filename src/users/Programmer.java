package users;

public class Programmer extends User {
	
	private String login;
	private String password;
	private String ftpUrl;
	private boolean connected;
	
	/*
	 * The programmer must have a login, password and a url for his ftp Server
	 */
	public Programmer(String login, String password, String ftpUrl) {
		// a Programmer is also an amateur 	
		if (UsersDatabase.programmerExists(login) || UsersDatabase.amateurExists(login)) 
			new UserException("An user with this login already exists !");
		this.login = login;
		this.password = password;
		this.ftpUrl = ftpUrl;
		this.connected = false;
		// new amateur because the programmer can also use the services
		UsersDatabase.addAmateur(new Amateur(login, password)); 
		UsersDatabase.addProgrammer(this);
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

	public String getFtpUrl() {
		return ftpUrl;
	}

	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}

	
}
