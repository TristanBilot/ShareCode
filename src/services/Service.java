package services;

public class Service {
	private Class<?> classService;
	private boolean activated;
	
	public Service(Class<?> service) {
		this.classService = service;			
		this.activated = true; // by default, the service is activated
	}

	public Class<?> getService() {
		return classService;
	}

	public void setService(Class<?> service) {
		this.classService = service;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

}
