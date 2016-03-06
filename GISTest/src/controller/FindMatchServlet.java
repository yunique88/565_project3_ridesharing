package controller;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.postgis.LineString;
import org.postgis.Point;

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
		if (request.getParameter("traceID") != null && !request.getParameter("traceID").equals("")){
			traceID = new Integer(request.getParameter("traceID"));
		}
		String userName = request.getParameter("userName");
		String startTime = request.getParameter("startTime");
		int startTimeMargin = 0;
		if (request.getParameter("startTimeMargin") != null  && !request.getParameter("startTimeMargin").equals("")){
			startTimeMargin = new Integer(request.getParameter("startTimeMargin"));
		}
		 
		String endTime = request.getParameter("endTime");
		int endTimeMargin = 0;
		if (request.getParameter("endTimeMargin") != null  && !request.getParameter("endTimeMargin").equals("")){
			startTimeMargin = new Integer(request.getParameter("endTimeMargin"));
		}
		
		String departurePoint = request.getParameter("departure");
		Point departure = null;
		if (departurePoint != null && !departurePoint.equals("")){
			String[] departureXY = departurePoint.split(",");
	        double latitude = Double.parseDouble(departureXY[0].substring(1));
	        double longitude = Double.parseDouble(departureXY[1].substring(1, departureXY[1].length()-1));
	        departure = new Point(longitude,latitude);
		}
		
		Double departureMargin = 0.0;
		if (request.getParameter("departureMargin") != null  && !request.getParameter("departureMargin").equals("")){
			departureMargin = Double.parseDouble(request.getParameter("departureMargin"));
		}
		
		String destinationPoint = request.getParameter("destination");
		Point destination = null;
		if (destinationPoint != null && !destinationPoint.equals("")){
			String[] destinationXY = destinationPoint.split(",");
	        double latitude = Double.parseDouble(destinationXY[0].substring(1));
	        double longitude = Double.parseDouble(destinationXY[1].substring(1, destinationXY[1].length()-1));
	        destination = new Point(longitude,latitude);
	        
		}
		
		Double destinationMargin = 0.0;
		if (request.getParameter("destinationMargin") != null  && !request.getParameter("destinationMargin").equals("")){
			destinationMargin = Double.parseDouble(request.getParameter("destinationMargin"));
		}
		
		
		String departureDate = request.getParameter("departureDate");
		System.out.println("----------departureDate:"+departureDate);
		/*
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.format(date)formatter.parse(departureDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("----------Date:"+date);
		*/
		double matchRate = 0.0;
		if (request.getParameter("matchRate") != null  && !request.getParameter("matchRate").equals("")){
			matchRate = new Integer(request.getParameter("matchRate"));
		}
		
		LineString coords = null;  
	    String sRoutePoints = request.getParameter("routePoints");
	    if(sRoutePoints != null && !sRoutePoints.equals("")){
	    	JSONParser parser = new JSONParser();
	    	System.out.println("sRoutePoints"+sRoutePoints);
		      try{ 
		         Object obj = parser.parse(sRoutePoints);
		         JSONArray array = (JSONArray)obj;
		         Point[] points = new Point[array.size()];
		         for (int i = 0; i < array.size(); i++){
		        	 JSONObject objVal = (JSONObject)array.get(i);
		        	 double pointLong = (double) objVal.get("longitude");
		        	 double pointLat = (double) objVal.get("latitude");
		        	 points[i] = new Point(pointLong,pointLat);
		         }
		        coords = new LineString(points);
		      } catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }  
		System.out.println("-----find matches for [departure: " + departure+ ", destination: " + destination+"]");
		System.out.println("-----coords"+coords);
		
		UserTracesDAO userTracesDAO = new UserTracesDAO();
		try {
			ArrayList<UserTraces> userTracesList = userTracesDAO.findMatch(traceID, userName,
					 startTime, startTimeMargin, endTime, endTimeMargin, departure,
					 departureMargin, destination, destinationMargin,
					 departureDate, matchRate, coords);
			 
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
