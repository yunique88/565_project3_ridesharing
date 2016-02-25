package controller;

import java.awt.Point;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserTraces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

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
		
		int traceID = 0;
		if (request.getParameter("traceID") != null){
			traceID = new Integer(request.getParameter("traceID"));
		}
		String userName = request.getParameter("userName");
		String startTime = request.getParameter("startTime");
		int startTimeMargin = 0;
		if (request.getParameter("startTimeMargin") != null){
			startTimeMargin = new Integer(request.getParameter("startTimeMargin"));
		}
		 
		String endTime = request.getParameter("endTime");
		int endTimeMargin = 0;
		if (request.getParameter("endTimeMargin") != null){
			startTimeMargin = new Integer(request.getParameter("endTimeMargin"));
		}
		
		Point departure = null;
		if (request.getParameter("departureX") != null && request.getParameter("departureY") != null){
			departure = new Point(new Integer(request.getParameter("departureX")),new Integer(request.getParameter("departureY")));
			
		}
		
		int departureMargin = 0;
		if (request.getParameter("departureMargin") != null){
			startTimeMargin = new Integer(request.getParameter("departureMargin"));
		}
		
		Point destination = null;
		if (request.getParameter("destinationX") != null && request.getParameter("destinationY") != null){
			departure = new Point(new Integer(request.getParameter("destinationX")),new Integer(request.getParameter("destinationY")));
			
		}
		int destinationMargin = 0;
		if (request.getParameter("destinationMargin") != null){
			destinationMargin = new Integer(request.getParameter("destinationMargin"));
		}
		String departureDate = request.getParameter("departureDate");
		double matchRate = 0.0;
		if (request.getParameter("matchRate") != null){
			matchRate = new Integer(request.getParameter("matchRate"));
		}
		
		System.out.println("querying " + traceID);
		UserTracesDAO userTracesDAO = new UserTracesDAO();
		
		try {
			ArrayList<UserTraces> userTracesList = userTracesDAO.findMatch(traceID, userName,
					 startTime, startTimeMargin, endTime, endTimeMargin, departure,
					 departureMargin, destination, destinationMargin,
					 departureDate, matchRate);
			 
			if(userTracesList == null) {
				response.setStatus(404);
				out.println(String.format("User <%s> not found", traceID));
				System.out.println("userTraces for traceID " + traceID + " does not exist.");
			} else {
				out.println(OBJECT_MAPPER.writeValueAsString(Lists.newArrayList(userTracesList)));
				System.out.println("Found: " + userTracesList.toString());
			}
			
		} catch(Exception e) {
			response.setStatus(500);
			out.println("ERROR: " + e.getMessage());
			System.out.println("ERROR - " + e.getMessage());
		}
		
	}

}
