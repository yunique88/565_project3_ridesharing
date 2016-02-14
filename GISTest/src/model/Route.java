package model;

public class Route {

	private String traceName;
	private String userName;
	private String route;
	
	public Route(String traceName, String userName, String route) {
		this.traceName = traceName;
		this.userName = userName;
		this.route = route;
	}
	public String getTraceName() {
		return traceName;
	}
	public void setTraceName(String traceName) {
		this.traceName = traceName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}

}
