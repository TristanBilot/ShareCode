package server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Tristan Bilot : https://tristan-bilot.fr
 * @version 1.0 
 */

public class Server implements Runnable {
	
	private ServerSocket listen_socket;
	private boolean running;
	private int port;
	
	Server (int port) throws IOException {
		listen_socket = new ServerSocket(port);
		running = true;
		this.port = port;
	}

	@Override
	public void run() {
		while (running) {
			try {
				if (port == ServerLaunch.PORT_AMA)
					new ServiceAmateur(listen_socket.accept()).lancer();
				else if (port == ServerLaunch.PORT_PROG)
					new ServiceProgrammer(listen_socket.accept()).lancer();							
			} catch (IOException e) {e.printStackTrace();}
		}		
	}
	
	public static void afficherDansLaConsole(String msg) {
		System.out.println(msg);
	}
	
	protected void finalize() throws Throwable {
		try {
			this.listen_socket.close();
		} 
		catch (IOException e) { e.printStackTrace(); }
	}
	

}
