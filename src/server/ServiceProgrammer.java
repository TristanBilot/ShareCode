package server;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import users.Programmer;
import users.ServicesDatabase;
import users.UserException;
import users.UsersDatabase;

/*
 * This service is invoked when a connection is open on the programmer port of the server
 */

public class ServiceProgrammer extends ServiceServer {
	
	public static boolean repeat;
	
	public ServiceProgrammer(Socket client) {
		super(client);
		repeat = true;
	}

	@Override
	public void run() {
		Server.afficherDansLaConsole("===> New connection : PROGRAMMER Service\n");
		try {
			/* Connection */
			Thread serviceThread = new ServiceConnexion(getClient(), this).lancer();
			serviceThread.join(); // wait until the connection thread is not dead
			//Server.afficherDansLaConsole("New connection !");

			/* Services */
			do {
				menu();
			} while(repeat);
			
			
		} catch (UserException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void menu() throws IOException {
		String response;
			super.getScanner().write("Which service do you want to use ?\n"
					+ "--> (1) Put a new service\n"
					+ "--> (2) Update a service\n"
					+ "--> (3) Change your FTP server IP\n"
					+ "--> (4) Start/stop a service\n"
					+ "--> (5) Delete a service");
			
			response = super.getScanner().read();
			if (response.equals("1") || response.equals("2") || response.equals("3") || response.equals("4") || response.equals("5"))
				repeat = false;

			super.getScanner().write(response); // send the choice of the user
			switch (response) {
			case "1":
				addNewService();				
				break;
			case "2":
				updateService();
				break;
			case "3":
				changeFtpURL();
				break;
			case "4":
				startStopService();
				break;
			case "5":
				deleteService();
				break;
	
			default:
				break;
			}
		//} while(!response.equals("1") || !response.equals("2") || !response.equals("3") || !response.equals("4") || !response.equals("5"));		
	}

	public void addNewService() throws IOException {
		String serviceName;
		super.getScanner().write("[+] Connection to your FTP server: " + UsersDatabase.getProgrammer(getUser().getLogin()).getFtpUrl());		
		super.getScanner().write("Please, tap the name of the class file --> ");
		serviceName = super.getScanner().read(); // the name of the file is enter by the user
		loadService(serviceName, true);
		Server.afficherDansLaConsole(ServicesDatabase.getStartedServices().toString());
		if (ServicesDatabase.exists(serviceName))
			super.getScanner().write("[+] Class " + serviceName + ".class loaded with success !");
		else
			super.getScanner().write("[-] Class '" + serviceName + ".class' not found.");
	}
	
	public void updateService() throws IOException {
		String serviceName, newServiceName;
		super.getScanner().write("[+] Connection to your FTP server: " + UsersDatabase.getProgrammer(getUser().getLogin()).getFtpUrl());		
		super.getScanner().write("==> Please, tap the name of the class file: ");
		serviceName = super.getScanner().read(); // the name of the original class
		if (!ServicesDatabase.exists(serviceName))
			super.getScanner().write("[-] This service name doesn't exists !");
		else {
			super.getScanner().write("[+] Class found !");
			super.getScanner().write("--> Enter the name of the new service: ");
			newServiceName = super.getScanner().read(); // name of new service
			Class<?> newClass = loadService(newServiceName, false); // load the new service, false because we don't want to add to bdd
			ServicesDatabase.replaceService(serviceName, newClass); // replace the old by the new
			super.getScanner().write("[+] Service updated correctly !");
		}
	}
	
	public void changeFtpURL() {
		String newURL;
		try {
			super.getScanner().write("==> Enter your new url: ");
			newURL = super.getScanner().read();
			UsersDatabase.getProgrammer(getUser().getLogin()).setFtpUrl(newURL);
			super.getScanner().write("[+] Great, your FTP server is now: " + newURL);
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void startStopService() {
		String response, nameService;
		try {
			super.getScanner().write("<==> Choose an action (1/2) <==>");
			super.getScanner().write("(1) Start a service");
			super.getScanner().write("(2) Stop a service");
			response = super.getScanner().read();

			super.getScanner().write("==> Enter the name of the service: ");
			nameService = super.getScanner().read();
			if (ServicesDatabase.exists(nameService)) {
				super.getScanner().write("[+] Class found.");
				if (response.equals("1")) {
					ServicesDatabase.getService(nameService).setActivated(true);
					super.getScanner().write("The service has been started correctly.");
				}
				else if (response.equals("2")) {
					ServicesDatabase.getService(nameService).setActivated(false);
					super.getScanner().write("The service has been stopped correctly.");
				}
			}
			else {
				super.getScanner().write("[-] Class not found.");
				return; //a changer, boucle
			}
				
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void deleteService() {
		String nameService;		
		try {
			super.getScanner().write("==> Enter the name of the service to delete: ");
			nameService = super.getScanner().read();
			if (ServicesDatabase.exists(nameService)) {
				super.getScanner().write("[+] Class found.");
				ServicesDatabase.removeService(nameService); // removing
				super.getScanner().write("The service has been removed correctly.");
			}
			else {
				super.getScanner().write("[-] Class not found.");
				return; //a changer, boucle
			}
		} catch (IOException e) {e.printStackTrace();}
	}
	
	/*
	 * The user can load a service from his ftp url and his login folder
	 */
	public Class<?> loadService(String serviceName, boolean insertIntoDatabase) {
		URL[] classLoaderUrls;
		try {
			classLoaderUrls = new URL[]{new URL(UsersDatabase.getProgrammer(getUser().getLogin()).getFtpUrl() + getUser().getLogin() + "/")}; // url + login folder
			URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls); 
	        Class<?> serviceCLass = urlClassLoader.loadClass("services" + "." + serviceName); // services à changer par le login du user et dans la classe (le package)
	        if (insertIntoDatabase) // if true, insert
	        	ServicesDatabase.addService(serviceCLass); // add to the list of services	        	       
	        return serviceCLass;
		} catch (MalformedURLException | ClassNotFoundException e) {e.printStackTrace();} 				
		return null;
	}
	
	public static Class<?> initLoadService(String serviceName, boolean insertIntoDatabase) {
		URL[] classLoaderUrls;
		try {
			classLoaderUrls = new URL[]{new URL("ftp://localhost:4446/a/")}; 
			URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls); 
	        Class<?> serviceCLass = urlClassLoader.loadClass("services" + "." + serviceName); // services à changer par le login du user et dans la classe (le package)
	        if (insertIntoDatabase) // if true, insert
	        	ServicesDatabase.addService(serviceCLass); // add to the list of services	        	       
	        return serviceCLass;
		} catch (MalformedURLException | ClassNotFoundException e) {e.printStackTrace();} 				
		return null;
	}

}
