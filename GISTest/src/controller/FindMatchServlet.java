package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserParaHistory;
import model.UserTraces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import dbConnecter.UserParaHistoryDAO;
import dbConnecter.UserTracesDAO;

/**
 * Servlet implementation class FindMatchServlet
 */
@WebServlet("/FindMatchServlet")
public class FindMatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindMatchServlet() {
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
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		int traceID = new Integer(request.getParameter("traceID"));
		String userName = request.getParameter("userName");
		String startTime = request.getParameter("startTime");
		int startTimeMargin = new Integer(request.getParameter("startTimeMargin"));
		String endTime = request.getParameter("endTime");
		int endTimeMargin = new Integer(request.getParameter("endTimeMargin"));
		String departure = request.getParameter("departure");
		String departureMargin = request.getParameter("departureMargin");
		String destination = request.getParameter("destination");
		String destinationMargin = request.getParameter("destinationMargin");
		String departureDate = request.getParameter("departureDate");
		String matchRate = request.getParameter("matchRate");
		System.out.println("querying " + traceID);
		UserTracesDAO userTracesDAO = new UserTracesDAO();
		
		try {
			UserTraces userTraces = null; 
//					userTracesDAO.findMatch(traceID, userName,
//					 startTime, startTimeMargin, endTime, endTimeMargin, departure,
//					 departureMargin, destination, destinationMargin,
//					 departureDate, matchRate);
			
			if(userTraces == null) {
				response.setStatus(404);
				out.println(String.format("User <%s> not found", traceID));
				System.out.println("userTraces for traceID " + traceID + " does not exist.");
			} else {
				out.println(OBJECT_MAPPER.writeValueAsString(Lists.newArrayList(userTraces)));
				System.out.println("Found: " + userTraces.toString());
			}
			
		} catch(Exception e) {
			response.setStatus(500);
			out.println("ERROR: " + e.getMessage());
			System.out.println("ERROR - " + e.getMessage());
		}
		
	}

}
