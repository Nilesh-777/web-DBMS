package Serv;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class servo
 */
@WebServlet("/servo")
public class servo extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		String uname=request.getParameter("n1");            int i=0;
		String password=request.getParameter("p1");      String pfd;
		//get context attribute password
				ServletContext sc=request.getServletContext();
		        String pm=(String)sc.getAttribute("passmysql");       
		try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/WebDBMS","root",pm);
		PreparedStatement pr=con.prepareStatement("select name,password1 from WebApp");
		ResultSet rs=(ResultSet) pr.executeQuery();
           
		while(rs.next())
		{
			if(rs.getString("name").equals(uname))
			{
				
				i=1;
				pfd=rs.getString("password1"); //out.println("password "+rs.getString("password1"));
				if(pfd.equals(password))
				{
					HttpSession hs=request.getSession(true);
					hs.setAttribute("mysess",uname);
					out.println("<body background='textured.jpg'></body>");
					out.println("<a href='logout.jsp' target='content'>LOG OUT</a>  | &nbsp;&nbsp; <a href='fetch' target='content'>fetch data of client</a><br>");
					out.println("welcome "+uname);
					
					break;
				}
				else
				{
					out.println("password incorrect");
					
					request.getRequestDispatcher("login.html").include(request,response);
					break;
				}
			}
			
		}
		rs.close();
		pr.close();
		con.close();
		}catch(Exception e)
		{
			out.println("exception "+e);
		}

		if(i!=1)
		{
		out.println("incorrect username");
		request.getRequestDispatcher("login.html").include(request,response);
		}


	}

}
