package dbConnecter;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

import model.UserParaHistory;

import org.junit.Test;

public class UserParaHistoryDAO {
	
	@Test
	public void test() throws Exception {
		UserParaHistory u = query("Bei");
		System.out.println(u.getDeparture());
	}
	
	public UserParaHistory query(String userName) throws Exception {
		
		Connection conn = DBUtils.getConnection();
		
		Statement stmt = null;
		try {
			
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select userName,startTime,startTimeMargin,endTime,endTimeMargin,"
						+" departure,"	//test blob
                        +" ST_X(departure) as departureX, ST_Y(departure) as departureY,departureMargin,"
                        +" ST_X(destination) as destinationX, ST_Y(destination) as destinationY,"
                        +" destinationMargin,departureDate,updateTime "
                        +" from UserParaHistory "
                        +" where userName = '" + userName+"'");
			
			if(rs.next()) {
				UserParaHistory uPHistory = new UserParaHistory(
						rs.getString("userName"), rs.getTime("startTime"), rs.getInt("startTimeMargin"), 
						rs.getTime("endTime"),rs.getInt("endTimeMargin"),
						rs.getBlob("departure"), 
						rs.getDouble("departureX"),rs.getDouble("departureY"),rs.getInt("departureMargin"),
						rs.getDouble("destinationX"),rs.getDouble("destinationY"),rs.getInt("destinationMargin"),
						rs.getDate("departureDate"),rs.getTime("updateTime"));
				return uPHistory;
			}
			
			return null;
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
