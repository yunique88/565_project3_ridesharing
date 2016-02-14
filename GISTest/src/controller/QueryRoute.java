package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Route;

import com.fasterxml.jackson.databind.ObjectMapper;

import dbConnecter.UserTracesDAO;

/**
 * Servlet implementation class QueryRoute
 */
@WebServlet("/QueryRoute")
public class QueryRoute extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryRoute() {
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
		UserTracesDAO dao = new UserTracesDAO();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		ObjectMapper OBJECT_MAPPER = new ObjectMapper();
		try {
			Route route = dao.queryRoute(request.getParameter("traceName"));
			if(route == null) {
				response.setStatus(404);
			} else {
				out.print(OBJECT_MAPPER.writeValueAsString(route));
			}
		} catch(Exception e) {
			response.setStatus(500);
			e.printStackTrace();
			out.println(e.getMessage());
		}
		
	}

}
