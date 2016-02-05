package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserParaHistory;
import dbConnecter.UserParaHistoryDAO;

/**
 * Servlet implementation class UserParahistoryServlet
 */
@WebServlet("/UserParaHistoryServlet")
public class UserParaHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String userName = request.getParameter("userName").toString();
		
		System.out.println("querying " + userName);
		UserParaHistoryDAO userParaHistoryDAO = new UserParaHistoryDAO();
		
		try {
			
			UserParaHistory userParaHistory = userParaHistoryDAO.query(userName);
			
			if(userParaHistory == null) {
				out.println("UserParahistory for username " + userName + " does not exist.");
				System.out.println("UserParahistory for username " + userName + " does not exist.");
			} else {
				request.setAttribute(userName, userParaHistory.getUserName());
				request.getRequestDispatcher("TestServlet.jsp").forward(request,response);
				out.println("Found: " + userParaHistory.toString());
				System.out.println("Found: " + userParaHistory.toString());
			}
			
		} catch(Exception e) {
			out.println("ERROR - " + e.getMessage());
			System.out.println("ERROR - " + e.getMessage());
		}
		
	}

}
