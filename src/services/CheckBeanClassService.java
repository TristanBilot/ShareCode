package services;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import users.UsersDatabase;

public class CheckBeanClassService implements IService {
	
	private String nameBean;
	
	public CheckBeanClassService(String nameBean) {
		this.nameBean = nameBean;
	}
	
	public String getResultOfService() throws Exception {
		String notABean = "[-] This class is not a bean.";
		String isABean = "[+] This class is a bean.";
		Class<?> classe = getClassFromString(nameBean);
		if (Modifier.isFinal(classe.getModifiers())) 
			return notABean;
		
		if (!Modifier.isPublic(classe.getModifiers())) 
			return notABean;
		
		if (classe.getConstructor() == null)
			return notABean;
		
		Field[] fields = classe.getDeclaredFields();
		Method[] methods =  classe.getDeclaredMethods();
		
		Boolean setterOK, getterOK;
		
		for (Field field: fields) {
			setterOK = false;
			getterOK = false;
			for (Method method: methods) {
				if (method.getName().equalsIgnoreCase("get" + field.getName()))
					getterOK = true;
				if (method.getName().equalsIgnoreCase("set" + field.getName()))
					setterOK = true;				
			}
			if (!setterOK)
				return notABean;
			if (!getterOK)
				return notABean;
		}
		return isABean;
	}
	
	public Class<?> getClassFromString(String beanName) {
		URL[] classLoaderUrls;
		try {
			classLoaderUrls = new URL[]{new URL(UsersDatabase.DEFAULT_FTP_URL + "analyse/")}; 
			URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls); 
	        Class<?> serviceCLass = urlClassLoader.loadClass("services" + "." + beanName);
	        return serviceCLass;
		} catch (MalformedURLException | ClassNotFoundException e) {e.printStackTrace();} 				
		return null;
	}
	
	@Override
	public void run() {
		try {
			getResultOfService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
