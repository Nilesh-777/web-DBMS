package Serv;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class update
 */
@WebServlet("/updateData")
public class update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String n=request.getParameter("name");        PrintWriter out=response.getWriter();
		String p1=request.getParameter("pass1");      response.setContentType("text/html");
		HttpSession hs=request.getSession(false);
		int r=Integer.parseInt((String) hs.getAttribute("mysess"));
		//get context attribute password
				ServletContext sc=request.getServletContext();
		        String pm=(String)sc.getAttribute("passmysql");
		if(!n.equals("")&&!n.equals(null))
		{
		try{
			
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/WebDBMS","root",pm);
		PreparedStatement st=con.prepareStatement("update WebApp set name=?,password1=?,dob=?,email=? where id=?");
		st.setString(1,request.getParameter("name"));
		st.setString(2,request.getParameter("pass1"));
		st.setString(3,request.getParameter("date1"));
		st.setString(4,request.getParameter("email"));
		st.setInt(5,r);
		st.executeUpdate();
		st.close();
		con.close();
		hs.invalidate();
		out.println("your last changes are updated successfully");
		request.getRequestDispatcher("login.html").include(request,response);
		}
		catch(Exception e)
		{
			out.println("exception : "+e);
		}
		}

		else
		{
			
			request.getRequestDispatcher("update.html").include(request,response);
			out.println("<b><font color='red'>name not entered</font></b>");
			
		}
	}

}
