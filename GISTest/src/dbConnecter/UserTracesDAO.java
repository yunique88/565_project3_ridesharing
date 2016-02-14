package dbConnecter;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

import model.Route;
import model.UserTraces;

import org.junit.Test;

public class UserTracesDAO {
	
	@Test
	public void test() throws Exception {
		UserTracesDAO dao = new UserTracesDAO();
		Route route = dao.queryRoute("Jon");
		System.out.println(route.getTraceName());
		System.out.println(route.getUserName());
		System.out.println(route.getRoute());
	}
	
	public Route queryRoute(String routeName) throws Exception {
		Connection conn = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		
		if(routeName == null) {
			return null;
		}
		
		try {
			pstmt = conn.prepareStatement(""
					+ "select TraceName, UserName, ST_AsGeoJson(Coords) as Route from UserTraces1 "
					+ "where TraceName = ?");
			pstmt.setString(1, routeName);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return new Route(rs.getString("TraceName"), rs.getString("UserName"), rs.getString("Route"));
			} else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pstmt.close();
			conn.close();
		}
	}
	
	public UserTraces findMatch(int traceID,String userName,
			Time startTime,int startTimeMargin,Time endTime,int endTimeMargin,Blob departure,
			int departureMargin,Blob destination,int destinationMargin,
			Date departureDate,double matchRate) throws Exception {
		
		Connection conn = DBUtils.getConnection();
		String sStartTimeMatch = " ", sEndTimeMatch = " ", sDepartureMatch = " ", sDestinationMatch = " ", sDepartureDateMatch =" ";
		Statement stmt = null;
		/*
		if (startTime != null && !startTime.equals("")) {
			sStartTimeMatch= " and getStartTimeMatch(startTime,'"+startTime+"','"+startTimeMargin+"') = 1 ";
		}
		if (endTime != null && !endTime.equals("")) {
			sEndTimeMatch = " and getEndTimeTimeMatch(endTime,'"+endTime+"','"+endTimeMargin+"') = 1 ";
		}
		if (departure != null && !departure.equals("")) {
			sDepartureMatch = " and getDepartureMatch(departure,'"+departure+"','"+departureMargin+"') = 1";
		}
		
		if (destination != null && !destination.equals("")) {
			sDestinationMatch = " and getDestinationMatch(destination,'"+destination+"','"+destinationMargin+"') = 1";
		}
		
		if (departureDate != null && !departureDate.equals("")) {
			sDepartureDateMatch = " and getDepartureDateMatch(departureDate,'"+departureDate+"') = 1";
		}
		*/
		try {
			
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select traceID,userName,traceName,startTime,startTimeMargin,endTime,endTimeMargin,"
						+ " departure,"	//test blob
                        + " ST_X(departure) as departureX, ST_Y(departure) as departureY,departureMargin,"
                        + " ST_X(destination) as destinationX, ST_Y(destination) as destinationY,"
                        + " destinationMargin,departureDate"
                     //   + ",ST_AsGeoJson(coords) as coords,"
                        + " coords, updateTime "
                        + " from UserTraces ;"
                    //    + " where userName != '"+userName+"'"
                    //    + " and getroutesMatch(traceID,"+traceID+") >="+ matchRate +" and traceID != "+traceID
                     /*   + sStartTimeMatch
                        + sEndTimeMatch
                        + sDepartureMatch
                        + sDestinationMatch
                        + sDepartureDateMatch
                        */
                        + " ");
			
			if(rs.next()) {
				UserTraces traces = new UserTraces(rs.getInt("traceID"),rs.getString("userName"),rs.getString("traceName"), rs.getTime("startTime"), rs.getInt("startTimeMargin"), 
						rs.getTime("endTime"),rs.getInt("endTimeMargin"),
						rs.getBlob("departure"),
						rs.getDouble("departureX"),rs.getDouble("departureY"),rs.getInt("departureMargin"),
						rs.getDouble("destinationX"),rs.getDouble("destinationY"),rs.getInt("destinationMargin"),
						rs.getDate("departureDate"),rs.getBlob("coords"),rs.getTime("updateTime"));
				return traces;
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
	
	public Boolean insertUserTrace(int traceID,String userName,String traceName,Time startTime,int startTimeMargin,Time endTime,int endTimeMargin,Blob departure,
		int departureMargin,Blob destination,int destinationMargin,
		Date departureDate,Blob coords,Time updateTime) throws Exception {
		
		Connection conn = DBUtils.getConnection();
		
		Statement stmt = null;
		try {
			
			stmt = conn.createStatement();
			
			int rs = stmt.executeUpdate("INSERT INTO UserTraces VALUES ("
						+ traceID +",'" + userName +"','" + traceName +"','" + startTime +"'," + startTimeMargin +",'" + endTime +"'," + endTimeMargin +",'"
						+ departure +"',"+ departureMargin +",'" + destination +"'," + destinationMargin +",'" + departureDate +"','" 
						+ coords +"','"+updateTime +"')");
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
