package model;

public class UserTraces {
	private int traceID;
	private String userName;
	private String coords;
	private String time;
	
	
	public UserTraces(int traceID,String userName,String coords,String time) {
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

	public String getCoords() {
		return coords;
	}

	public void setCoords(String coords) {
		this.coords = coords;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	
}
