package dbConnecter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import model.UserTraces;

import org.postgis.LineString;
import org.postgis.Point;

public class UserTracesDAO {
	/*
	public static void main(String[] args) throws Exception {
		System.out.println(new java.util.Date());
		UserTracesDAO dao = new UserTracesDAO();
		dao.findMatch(1, "Bei", new java.util.Date().toString(), 1, null, 1, null, 1.1, null, 1.1, null, 1.1, null);
	}
*/
	public ArrayList<UserTraces> findMatch(int traceID, String userName,
			String startTime,int startTimeMargin,String endTime,int endTimeMargin,Point departure,
			double departureMargin,Point destination,double destinationMargin,
			String departureDate,double matchRate,LineString coords) throws Exception {
		
		Connection conn = DBUtils.getConnection();
		Statement stmt = null;
//		CallableStatement callStmt = null;
		
		try {
			
			stmt = conn.createStatement();
		//	callStmt = conn.prepareCall("{call getMatchOnLineString("+coords+","+coords+", null, 1, null, 1, null, 1.1, null, 1.1, null)}");
		//	ResultSet rs1 = callStmt.executeQuery();
			
		//	System.out.println(rs1);
			
//			Boolean rs1 = callStmt.execute("{call new_procedure("+traceID+")}");
			ResultSet rs = stmt.executeQuery("select traceID,userName,"
						+ " ST_AsGeoJson(coords) as coords,"
						+ " ST_AsGeoJson(time) as time "
                        + " from finalmatch "
                        + " where cluster > 0;"
                        + " ");
			ArrayList<UserTraces> list = new ArrayList<UserTraces>();
			while(rs.next()) {
				UserTraces traces = new UserTraces(rs.getInt("traceID"),rs.getString("userName"),
													rs.getObject("coords"),rs.getObject("time"));
				list.add(traces);
			}
			
			return list;
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
