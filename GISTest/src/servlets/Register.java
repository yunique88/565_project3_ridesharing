package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/register")
public class Register extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//    	response.setContentType("application/json");
    	response.setContentType("text/plain");
    	// set it to the json 
    	
        
    	
        //get request parameters for userID and password
        String email = request.getParameter("email");
        String pswd = request.getParameter("pswd");
         
        //get servlet config init params
        String emailDB = "asdf@asdf.com";
        String pswdDB = "asdf";
        boolean loggedIn = true;
        String msg = "";
        
//        JSONObject obj = new JSONObject();
        
//        obj.put("loginStatus", true);
//        obj.put("loginMsg", "Hello");
        
        if (!email.equals(emailDB)) {
        	loggedIn = true;
        	msg = "t ";
//        	out.print("<script>document.getElementById(\"loginMsg\").innerHTML = \"New text!\";</script>");
//            RequestDispatcher rd=request.getRequestDispatcher("index.html");  
//            rd.include(request,response);  
        } else {
        	loggedIn = false;
        	msg = "f ";
//        	out.print("loginFail");  
//            RequestDispatcher rd=request.getRequestDispatcher("index.html");  
//            rd.include(request,response);  
        }
//        out.close(); 
        

        PrintWriter out = response.getWriter();
//        out.println("{");
//		out.println("\"status\": \"" + loggedIn + "\",");
//		out.println("\"msg\": \"" + msg + "\"");
//		out.println("}");
        out.print(msg);
        response.getWriter().write(out.toString());
        out.close();
        
//        
//        Gson gson = new GsonBuilder().setPrettyPrinting()
//                .create();
//        String json = gson.toJson(personMap);
//        response.getWriter().write(json);
//        
        
        
//        //logging example
//        log("User="+user+"::password="+pwd);
//         
//        if(userID.equals(user) && password.equals(pwd)){
//            response.sendRedirect("LoginSuccess.jsp");
//        }else{
//            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
//            PrintWriter out= response.getWriter();
//            out.println("<font color=red>Either user name or password is wrong.</font>");
//            rd.include(request, response);
//             
//        }
         
    }
}