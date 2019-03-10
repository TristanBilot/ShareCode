package server;

import java.net.Socket;

import users.User;

/*
 * The abstract class for the amateur & programmer services
 */

public abstract class ServiceServer implements Runnable {
	private Socket client;
	private IOScanner scanner;
	public User user;
	
	public ServiceServer(Socket client) {
		this.client = client;
		this.scanner = new IOScanner(client);
	}
	
	public Thread lancer() {
		Thread t = new Thread(this);
		t.start();
		return t;
	}

	public Socket getClient() {
		return client;
	}

	public IOScanner getScanner() {
		return scanner;
	}
	
	public void setUserForThisService(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}
