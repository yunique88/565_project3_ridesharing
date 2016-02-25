package dbConnecter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.Point;
import model.UserTraces;
import org.junit.Test;

public class UserTracesDAO {

	@Test
	public void test() throws Exception {
		UserTracesDAO dao = new UserTracesDAO();
		UserTraces route = dao.findMatch(11);
		System.out.println(route.getTraceID());
		System.out.println(route.getUserName());
		System.out.println(route.getCoords());
		System.out.println(route.getTime());
		/*UserTraces route = dao.findMatch(11,"Jon",
				"8:30:00",30,"9:30:00",20,"(2,5)",
				30,"(6,7)",20,
				null,70);
		*/
	}
	
	/*public UserTraces findMatch(int traceID,String userName,
			Date startTime,int startTimeMargin,Date endTime,int endTimeMargin,Point departure,
			int departureMargin,Point destination,int destinationMargin,
			Date departureDate,double matchRate) throws Exception {
		*/
	public UserTraces findMatch(int traceID) throws Exception {
		Connection conn = DBUtils.getConnection();
		Statement stmt = null;
		Statement callStmt = null;
		
		try {
			
			stmt = conn.createStatement();
		//	callStmt = conn.prepareCall("");
		//	Boolean rs1 = callStmt.execute("{call new_procedure("+traceID+")}");
			ResultSet rs = stmt.executeQuery("select traceID,userName,"
						+ " ST_AsGeoJson(coords) as coords,"
						+ " ST_AsGeoJson(time) as time"
                        + " from UserTraces ;"
                        + " ");
			
			if(rs.next()) {
				UserTraces traces = new UserTraces(rs.getInt("traceID"),rs.getString("userName"),
													rs.getString("coords"),rs.getString("time"));
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
	
}
