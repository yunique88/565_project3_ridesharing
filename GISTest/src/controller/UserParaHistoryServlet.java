package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserParaHistory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import dbConnecter.UserParaHistoryDAO;

/**
 * Servlet implementation class UserParaHistoryServlet
 */
@WebServlet("/UserParaHistoryServlet")
public class UserParaHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserParaHistoryServlet() {
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
		
		String userName = request.getParameter("userName").toString();
		
		System.out.println("querying " + userName);
		UserParaHistoryDAO userParaHistoryDAO = new UserParaHistoryDAO();
		
		try {
			
			ArrayList<UserParaHistory> userParaHistoryList = userParaHistoryDAO.query(userName);
			
			if(userParaHistoryList == null) {
				response.setStatus(404);
				out.println(String.format("User <%s> not found", userName));
				System.out.println("UserParahistory for username " + userName + " does not exist.");
			} else {
				out.println(OBJECT_MAPPER.writeValueAsString(Lists.newArrayList(userParaHistoryList)));
				System.out.println("Found: " + userParaHistoryList.toString());
			}
			
		} catch(Exception e) {
			response.setStatus(500);
			out.println("ERROR: " + e.getMessage());
			System.out.println("ERROR - " + e.getMessage());
		}
		
	}

}
