package users;

import java.util.ArrayList;
import java.util.List;

import server.ServiceProgrammer;
import services.Service;

public class ServicesDatabase {

	private static List<Service> services;
	
	static {
		initDatabase();
	}
	
	public static void initDatabase() {
		services = new ArrayList<Service>();
		ServiceProgrammer.initLoadService("ReverseStringService", true);
		ServiceProgrammer.initLoadService("CheckJsonFileService", true);
		ServiceProgrammer.initLoadService("CheckXmlFileService", true);
		ServiceProgrammer.initLoadService("CheckBeanClassService", true);		
	}
	
	public static void addService(Class<?> service) {
		services.add(new Service(service));
	}
	
	public static Service getService(int index) {
		return services.get(index);
	}
	
	public static Class<?> getServiceClass(String name) {
		for (Service c: services) {
			if (c.getService().getName().equals(name))
				return c.getService();
		}
		return null;
	}
	
	public static void removeService(String name) {
		synchronized(services) {
			for (Service c: services) {
				if (c.getService().getName().equals(name))
					services.remove(c);
			}			
		}
	}
	
	/*
	 * return true if the services exists, else false
	 */
	public static boolean exists(String name) {
		for (Service c: services) {
			if (c.getService().getSimpleName().equals(name))
				return true;
		}
		return false;
	}
	
	public static void replaceService(String name, Class<?> newService) {
		for (Service c: services) {
			if (c.getService().getName().equals(name))
				c.setService(newService);
		}
	}
	
	public static String getAllServices() {
		String tmp = "";
		for (Service c: services) {
			tmp += c.getService().getSimpleName() + "\n";
		}
		return tmp;
	}
	
	public static List<Service> getStartedServices() {
		ArrayList<Service> tmp = new ArrayList<Service>();
		for (Service c: services) {
			if (c.isActivated())
				tmp.add(c);
		}
		return tmp;
	}
	
	public static int getNbStartedServices() {
		return getStartedServices().size();
	}
	
	public static Service getService(String name) {
		for (Service c: services) {
			if (c.getService().getSimpleName().equals(name))
				return c;
		}
		return null;
	}
}
