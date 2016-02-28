package model;

public class UserTraces {
	private int traceID;
	private String userName;
	private Object coords;
	private Object time;
	
	
	public UserTraces(int traceID,String userName,Object coords,Object time) {
		this.traceID = traceID;
		this.userName  = userName;
		this.coords = coords;
		this.time  = time;
	}
	
	public int getTraceID() {
		return traceID;
	}

	public void setTraceID(int traceID) {
		this.traceID = traceID;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Object getCoords() {
		return coords;
	}

	public void setCoords(Object coords) {
		this.coords = coords;
	}

	public Object getTime() {
		return time;
	}

	public void setTime(Object time) {
		this.time = time;
	}

	
}
