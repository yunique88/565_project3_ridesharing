package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import model.Student;

public class StudentDAO {
	public Student queryStudent(int studentID) throws Exception {
		
		Connection conn = DBUtils.getConnection();
		
		Statement stmt = null;
		try {
			
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from student where studentID = " + studentID);
			
			if(rs.next()) {
				Student student = new Student(rs.getInt("studentID"), rs.getString("studentName"), rs.getInt("age"), rs.getString("Studentcol"));
				return student;
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
