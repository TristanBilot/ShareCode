package client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import server.IOScanner;
import server.ServiceConnexion;
import server.ServiceProgrammer;
import users.ServicesDatabase;

public class ClientProgrammer {
	
	private static final int PORT = 2600;
	private static final String HOST = "127.0.0.1";

	public static void main(String[] args) throws IOException, InterruptedException {
		
		Socket socket = new Socket(HOST, PORT);
		IOScanner scanner = new IOScanner(socket);	
		
		System.out.println("[+] Your are now connected to the server " + socket.getInetAddress() + ":"+ socket.getPort());
		
		/* --- Connection --- */
		do {
			System.out.println(scanner.read()); // login
			scanner.sendKeyboard();
			System.out.println(scanner.read()); // password
			scanner.sendKeyboard();
			System.out.println(scanner.read()); // response
			System.out.println();
		} while (ServiceConnexion.repeat);
		 
		
		/* --- Services --- */
		int y = 0;
		do {
			System.out.println(++y);
			for (int i = 0; i < 6; i++)
				System.out.println(scanner.read());
			scanner.sendKeyboard();
		 
		} while(ServiceProgrammer.repeat);
		
		String response = scanner.read(); // choice (1, 2, 3..)
		
		switch (response) {
		case "1": // ADD SERVICE
			System.out.println(scanner.read());
			System.out.println(scanner.read());
			scanner.sendKeyboard(); // name of the service to add
			System.out.println(scanner.read()); // response if success
			//System.out.println(scanner.read()); // test reverse string
			break;
			
		case "2": // UPDATE SERVICE
			System.out.println(scanner.read()); // ftp connection success
			System.out.println(scanner.read()); // please, tap the name of file
			scanner.sendKeyboard(); // name of the service to update
			System.out.println(scanner.read()); // class (not) found
			System.out.println(scanner.read()); // name of the 2nd class
			scanner.sendKeyboard(); // name of the 2nd class
			System.out.println(scanner.read()); // result, success
			break;
			
		case "3": // CHANGE FTP URL
			System.out.println(scanner.read()); // enter the new url
			scanner.sendKeyboard(); // new url sent to server
			System.out.println(scanner.read()); // response, success
			break;
			
		case "4": // START/STOP A SERVICE
			System.out.println(scanner.read()); // choose an action
			System.out.println(scanner.read()); // 1:start service
			System.out.println(scanner.read()); // 2:stop service
			scanner.sendKeyboard(); // start or stop
			System.out.println(scanner.read()); // enter the name of the service
			scanner.sendKeyboard(); // the name of the service
			System.out.println(scanner.read()); // class found or not
			System.out.println(scanner.read()); // response, success or fail
			break;

		case "5": // DELETE A SERVICE
			System.out.println(scanner.read()); // name of service to delete
			scanner.sendKeyboard(); // name of service
			System.out.println(scanner.read()); // class found or not
			System.out.println(scanner.read()); // response, success or fail
			break;
		
			
		default:
			break;
		}
	}
	
	public void addService() {
		
	}

}
