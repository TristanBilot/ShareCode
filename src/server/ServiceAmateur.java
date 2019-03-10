package server;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import services.Service;
import users.ServicesDatabase;

/*
 * This service is invoked when a connection is open on the amateur port of the server
 */

public class ServiceAmateur extends ServiceServer {
	
	public ServiceAmateur(Socket client) {
		super(client);
	}

	@Override
	public void run() {
		Server.afficherDansLaConsole("===> New connection : AMATEUR Service\n");
		menu();
	}
	
	public String useService(int numService, String inputCode) {
		URL[] classLoaderUrls;
		String serviceName = ServicesDatabase.getStartedServices().get(numService - 1).getService().getSimpleName(); // num - 1 because we add 1 before (1 instead of 0)
		
		try {
			classLoaderUrls = new URL[]{new URL("ftp://localhost:4446/services/")};
			URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
	        Class<?> serviceCLass = urlClassLoader.loadClass("services." + serviceName); // invokation of the service
	    	Constructor<?> constructor = serviceCLass.getConstructor(String.class); // each constructor eat a String in param
			Object instance = constructor.newInstance(inputCode); // new Instance of constructor with the String input code
			Method method = serviceCLass.getMethod("getResultOfService"); // the method used by every services
	        Object returnValue = method.invoke(instance); // launch the method
	        String result = (String) returnValue; // cast to String
	        return result;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void menu() {
		int serviceNum;
		int y;
		try {
			super.getScanner().write("==> Choose a service to start: ");			
			for (int i = 0; i < ServicesDatabase.getNbStartedServices(); i++) {
				y = i + 1;
				super.getScanner().write("("+ y +") " + ServicesDatabase.getStartedServices().get(i).getService().getSimpleName());
			}
			serviceNum = Integer.parseInt(super.getScanner().read());
			if (serviceNum > 0 && serviceNum <= ServicesDatabase.getNbStartedServices()) {
				serviceImplementation(serviceNum);
			}
			else {
				
			}
				
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void serviceImplementation(int numService) {
		String inputCode = "";
		try {
			super.getScanner().write("Please, enter the input code you want to analyse: ");
			inputCode = super.getScanner().read();
			String responseOfTheService = useService(numService, inputCode);
			super.getScanner().write("==> Here is the response of the service: ");
			super.getScanner().write(responseOfTheService);
		} catch (IOException e1) {e1.printStackTrace();}
		
	}

}
