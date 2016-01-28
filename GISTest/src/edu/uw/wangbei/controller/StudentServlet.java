package edu.uw.wangbei.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uw.wangbei.dao.StudentDAO;
import edu.uw.wangbei.model.Student;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		int studentID = new Integer(request.getParameter("studentID")).intValue();
		
		System.out.println("querying " + studentID);
		StudentDAO studentDAO = new StudentDAO();
		
		try {
			
			Student student = studentDAO.queryStudent(studentID);
			
			if(student == null) {
				out.println("Student for ID " + studentID + " does not exist.");
				System.out.println("Student for ID " + studentID + " does not exist.");
			} else {
				out.println("Found: " + student.toString());
				System.out.println("Found: " + student.toString());
			}
			
		} catch(Exception e) {
			out.println("ERROR - " + e.getMessage());
			System.out.println("ERROR - " + e.getMessage());
		}
		
	}

}
