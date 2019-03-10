package client;

import java.io.IOException;
import java.net.Socket;

import server.IOScanner;
import server.Server;
import services.Service;
import users.ServicesDatabase;

public class ClientAmateur {
	
	private static final int PORT = 2500;
	private static final String HOST = "127.0.0.1";

	public static void main(String[] args) throws IOException {

		Socket socket = new Socket(HOST, PORT);
		IOScanner scanner = new IOScanner(socket);	
		
		System.out.println("[+] You are now connected to the server: " + socket.getInetAddress() + ":"+ socket.getPort() + "\n");
		
		System.out.println(scanner.read()); // Choose a service
		for (int i = 0; i < ServicesDatabase.getNbStartedServices(); i++) {
			System.out.println(scanner.read()); // All the started services
			//Server.afficherDansLaConsole(ServicesDatabase.getStartedServices().get(i).getService().getSimpleName());
		}
		scanner.sendKeyboard(); // the service number
		System.out.println(scanner.read()); // enter the input code
		scanner.sendKeyboard(); // the input code
		System.out.println(scanner.read()); // your response is:
		System.out.println(scanner.read()); // the response content
	}

}
