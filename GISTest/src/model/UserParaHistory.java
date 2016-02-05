package model;

import java.sql.Date;
import java.sql.Time;

public class UserParaHistory {
	
	private String userName;
	private Time startTime;
	private int startTimeMargin;
	private Time endTime;
	private int endTimeMargin;
	private double departureX;
	private double departureY;
	private int departureMargin;
	private double destinationX;
	private double destinationY;
	private int destinationMargin;
	private Date departureDate;
	private Time updateTime;
	
	
	public UserParaHistory(String userName,Time startTime,int startTimeMargin,Time endTime,int endTimeMargin,
			double departureX,double departureY,int departureMargin,double destinationX,double destinationY,int destinationMargin,
			Date departureDate,Time updateTime) {
		this.userName  = userName;
		this.startTime  = startTime;
		this.startTimeMargin  = startTimeMargin;
		this.endTime  = endTime;
		this.endTimeMargin  = endTimeMargin;
		this.departureX  = departureX;
		this.departureY  = departureY;
		this.departureMargin  = departureMargin;
		this.destinationX  = destinationX;
		this.destinationY  = destinationY;
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



	public double getDepartureX() {
		return departureX;
	}



	public void setDepartureX(double departureX) {
		this.departureX = departureX;
	}



	public double getDepartureY() {
		return departureY;
	}



	public void setDepartureY(double departureY) {
		this.departureY = departureY;
	}



	public int getDepartureMargin() {
		return departureMargin;
	}



	public void setDepartureMargin(int departureMargin) {
		this.departureMargin = departureMargin;
	}



	public double getDestinationX() {
		return destinationX;
	}



	public void setDestinationX(double destinationX) {
		this.destinationX = destinationX;
	}



	public double getDestinationY() {
		return destinationY;
	}



	public void setDestinationY(double destinationY) {
		this.destinationY = destinationY;
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
		return userName +"@"+ startTime +"@"+ startTimeMargin +"@"+ endTime +"@"+ endTimeMargin +"@"
				+ departureX +"@"+ departureY +"@"+ departureMargin +"@"+ destinationX +"@"+ destinationY +"@"
				+ destinationMargin +"@"+ departureDate +"@"+ updateTime;
	}
}
