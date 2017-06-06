package Serv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
/**
 * Servlet implementation class FormToDatabase
 */
@WebServlet("/setData")
public class FormToDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String n=request.getParameter("name");        PrintWriter out=response.getWriter();
		String p1=request.getParameter("pass1");      response.setContentType("text/html");
		String p2=request.getParameter("pass2");
		//get context attribute password
		ServletContext sc=request.getServletContext();
        String pm=(String)sc.getAttribute("passmysql");
        
		if(p1.equals(p2)&&!n.equals("")&&!n.equals(null))
		{
		try{
			
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/WebDBMS","root",pm);
		PreparedStatement st=con.prepareStatement("insert into WebApp(name,password1,dob,email) values (?,?,?,?)");
		st.setString(1,request.getParameter("name"));
		st.setString(2,request.getParameter("pass1"));
		st.setString(3,request.getParameter("date1"));
		st.setString(4,request.getParameter("email"));
		st.executeUpdate();
		st.close();
		con.close();
		response.sendRedirect("Registration.html");
		}
		catch(Exception e)
		{
			out.println("exception : "+e);
		}
		}

		else
		{
			
			request.getRequestDispatcher("Registration.html").include(request,response);
			out.println("<b><font color='red'>passwords are not matching</font></b>");
			
		}
	}

}
