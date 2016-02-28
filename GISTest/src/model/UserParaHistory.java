package model;

import java.sql.Date;
import java.sql.Time;


public class UserParaHistory {
	private int traceId;
	private String userName;
	private Time startTime;
	private int startTimeMargin;
	private Time endTime;
	private int endTimeMargin;
	private Object departure;
	private int departureMargin;
	private Object destination;
	private int destinationMargin;
	private Date departureDate;
	private Time updateTime;
	
	
	public UserParaHistory(int traceId, String userName,Time startTime,int startTimeMargin,Time endTime,int endTimeMargin,
			Object departure,int departureMargin,Object destination,int destinationMargin,
			Date departureDate,Time updateTime) {
		this.traceId = traceId;
		this.userName  = userName;
		this.startTime  = startTime;
		this.startTimeMargin  = startTimeMargin;
		this.endTime  = endTime;
		this.endTimeMargin  = endTimeMargin;
		this.departure = departure;
		this.departureMargin  = departureMargin;
		this.destination  = destination;
		this.destinationMargin = destinationMargin;
		this.departureDate  = departureDate;
		this.updateTime  = updateTime;
		
	}

	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public Time getStartTime() {
		return startTime;
	}



	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}



	public int getStartTimeMargin() {
		return startTimeMargin;
	}



	public void setStartTimeMargin(int startTimeMargin) {
		this.startTimeMargin = startTimeMargin;
	}



	public Time getEndTime() {
		return endTime;
	}



	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}



	public int getEndTimeMargin() {
		return endTimeMargin;
	}



	public void setEndTimeMargin(int endTimeMargin) {
		this.endTimeMargin = endTimeMargin;
	}


	public int getDepartureMargin() {
		return departureMargin;
	}



	public void setDepartureMargin(int departureMargin) {
		this.departureMargin = departureMargin;
	}



	public Object getDestination() {
		return destination;
	}



	public void setDestination(double destination) {
		this.destination = destination;
	}

	public int getDestinationMargin() {
		return destinationMargin;
	}



	public void setDestinationMargin(int destinationMargin) {
		this.destinationMargin = destinationMargin;
	}



	public Date getDepartureDate() {
		return departureDate;
	}



	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}



	public Time getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(Time updateTime) {
		this.updateTime = updateTime;
	}

	
	@Override
	public String toString() {
		return traceId +"@"+ userName +"@"+ startTime +"@"+ startTimeMargin +"@"+ endTime +"@"+ endTimeMargin +"@"
				+ departure + "@"
				+"@"+ departureMargin +"@"+ destination +"@"
				+ destinationMargin +"@"+ departureDate +"@"+ updateTime;
	}

	public Object getDeparture() {
		return departure;
	}

	public void setDeparture(Object departure) {
		this.departure = departure;
	}

	public int getTraceId() {
		return traceId;
	}

	public void setTraceId(int traceId) {
		this.traceId = traceId;
	}
}
