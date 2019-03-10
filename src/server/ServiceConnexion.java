package server;

import java.io.IOException;
import java.net.Socket;

import users.Amateur;
import users.Programmer;
import users.UserException;
import users.UsersDatabase;

public class ServiceConnexion extends ServiceServer {
	
	private String typeOfSevice; // connection for an amateur or a programmer
	public static boolean repeat; 
	private ServiceServer typeOfService;

	public ServiceConnexion(Socket client, ServiceServer connectionFor) throws UserException {
		super(client);
		repeat = true;
		if (connectionFor instanceof ServiceProgrammer || connectionFor instanceof ServiceAmateur)
			this.typeOfService = connectionFor;
		else
			throw new UserException("The type of user is invalid (amateur/programmer expected)");
	}

	@Override
	public void run() {
		String login, password, 
				success = "[+] Connection OK !", 
				errorLogin = "[+] Connection refused --> (bad password or login)\n Press enter to retry...",
				errorUser = "[+] Connection refused --> (user doesn't exists yet)\n Press enter to retry...";
		
		do {
			try {
				
				super.getScanner().write("Your login: ");
				login = super.getScanner().read();
				
				super.getScanner().write("Your password: ");
				password = super.getScanner().read();
				
				if (typeOfService instanceof ServiceProgrammer) {
					if (UsersDatabase.amateurExists(login)) {
						if (UsersDatabase.getAmateur(login).getPassword().equals(password)) {
							repeat = false;
							UsersDatabase.getAmateur(login).setConnected(true);
							super.getScanner().write(success);	
							String ftpURL = UsersDatabase.getProgrammer(login).getFtpUrl();
							typeOfService.setUserForThisService( new Programmer(login ,password, ftpURL));
						}
						else
							super.getScanner().write(errorLogin);
					}
					else
						super.getScanner().write(errorUser);
				}
				
				else if (typeOfService instanceof ServiceAmateur) {
					if (UsersDatabase.programmerExists(login)) {
						if (UsersDatabase.getProgrammer(login).getPassword().equals(password)) {
							repeat = false;
							UsersDatabase.getProgrammer(login).setConnected(true);
							super.getScanner().write(success);
							typeOfService.setUserForThisService( new Amateur(login ,password));
						}
						else
							super.getScanner().write(errorLogin);
					}
					else
						super.getScanner().write(errorUser);
				}
				else
					super.getScanner().write(errorLogin);
					// ajouter while
					
				
			} catch (IOException e) {e.printStackTrace();}
			
		} while(repeat);
	}
	
	

}
