package dbConnecter;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import model.UserParaHistory;

public class UserParaHistoryDAO {
	
	public ArrayList<UserParaHistory> query(String userName) throws Exception {
		
		Connection conn = DBUtils.getConnection();
		ArrayList<UserParaHistory> uPHistoryList = new ArrayList<UserParaHistory>();
		Statement stmt = null;
		try {
			
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select traceId,userName,startTime,startTimeMargin,endTime,endTimeMargin,"
						+" ST_AsText(departure) as departure,"	
                        +" departureMargin,"
                        +" ST_AsText(destination) as destination,"
                        +" destinationMargin,departureDate,updateTime "
                        +" from UserParaHistory "
                        +" where userName = '" + userName+"'"
                        +" ORDER BY traceId DESC");
			
			while(rs.next()) {
				UserParaHistory uPHistory = new UserParaHistory(
						rs.getInt("traceId"),
						rs.getString("userName"), rs.getTime("startTime"), rs.getInt("startTimeMargin"), 
						rs.getTime("endTime"),rs.getInt("endTimeMargin"),
						rs.getObject("departure"),
						rs.getInt("departureMargin"),
						rs.getObject("destination"),rs.getInt("destinationMargin"),
						rs.getDate("departureDate"),rs.getTime("updateTime"));
				uPHistoryList.add(uPHistory);
				
			}
			
			return uPHistoryList;
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch(Exception e) {
			}
		}
	}
	
	public Boolean insert(String userName,Time startTime,int startTimeMargin,Time endTime,int endTimeMargin,Blob departure,
		int departureMargin,Blob destination,int destinationMargin,
		Date departureDate,Time updateTime) throws Exception {
		
		Connection conn = DBUtils.getConnection();
		
		Statement stmt = null;
		try {
			
			stmt = conn.createStatement();
			
			int rs = stmt.executeUpdate("INSERT INTO UserParaHistory VALUES ('"
						+ userName +"','" + startTime +"'," + startTimeMargin +",'" + endTime +"'," + endTimeMargin +",'"
						+ departure +"',"+ departureMargin +",'" + destination +"'," + destinationMargin +",'" + departureDate +"','" 
						+ updateTime +"')");
			if(rs == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				stmt.close();
				conn.close();
				return false;
			} catch(Exception e) {
			}
		}
	}
}
