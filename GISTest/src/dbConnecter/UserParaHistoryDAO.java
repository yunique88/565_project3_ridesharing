package dbConnecter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import model.UserParaHistory;

public class UserParaHistoryDAO {
	public UserParaHistory query(String userName) throws Exception {
		
		Connection conn = DBUtils.getConnection();
		
		Statement stmt = null;
		try {
			
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select userName,startTime,startTimeMargin,endTime,endTimeMargin,"
                        +" ST_X(departure) as departureX, ST_Y(departure) as departureY,departureMargin,"
                        +" ST_X(destination) as destinationX, ST_Y(destination) as destinationY,"
                        +" destinationMargin,departureDate,updateTime "
                        +" from UserParaHistory "
                        +" where userName = " + userName);
			
			if(rs.next()) {
				UserParaHistory uPHistory = new UserParaHistory(rs.getString("userName"), rs.getTime("startTime"), rs.getInt("startTimeMargin"), 
						rs.getTime("endTime"),rs.getInt("endTimeMargin"),rs.getDouble("departureX"),rs.getDouble("departureY"),rs.getInt("departureMargin"),
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
}
