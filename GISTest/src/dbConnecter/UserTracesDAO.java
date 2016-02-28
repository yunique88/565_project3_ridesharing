package dbConnecter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.Point;

import model.UserTraces;

public class UserTracesDAO {

	public ArrayList<UserTraces> findMatch(int traceID,String userName,
			String startTime,int startTimeMargin,String endTime,int endTimeMargin,Point departure,
			int departureMargin,Point destination,int destinationMargin,
			String departureDate,double matchRate) throws Exception {
		
		Connection conn = DBUtils.getConnection();
		Statement stmt = null;
		Statement callStmt = null;
		
		try {
			
			stmt = conn.createStatement();
		//	callStmt = conn.prepareCall("");
		//	Boolean rs1 = callStmt.execute("{call new_procedure("+traceID+")}");
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
