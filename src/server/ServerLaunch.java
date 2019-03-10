package server;

import java.io.IOException;
import users.ServicesDatabase;
import users.UsersDatabase;

public class ServerLaunch {
	 
	public static final int PORT_AMA = 2500, PORT_PROG = 2600;
	
	public static void main(String[] args) throws IOException {
		
		new Thread(new Server(PORT_AMA)).start();
		System.out.println("[+] Serveur lancé sur le port " + PORT_AMA);
		
		new Thread(new Server(PORT_PROG)).start();
		System.out.println("[+] Serveur lancé sur le port " + PORT_PROG);

		UsersDatabase.initSomeUsers();
		ServicesDatabase.initDatabase();
		
	}

}
