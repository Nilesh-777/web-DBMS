package Serv;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class fetch
 */
@WebServlet("/fetch")
public class fetch extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		HttpSession hs=request.getSession(false);
		//get context attribute password
				ServletContext sc=request.getServletContext();
		        String pm=(String)sc.getAttribute("passmysql");
		if(!hs.isNew())
		{
			String n1=(String) hs.getAttribute("mysess");
			
			try {
			Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/WebDBMS","root",pm);
		PreparedStatement st=con.prepareStatement("select name,password1,dob,email,id from WebApp where name='"+n1+"'");
		ResultSet rs=st.executeQuery();
		while(rs.next())
		{
			String str="<form action='update.html'>"
					+ "<table>"
					+ "<tr>"
					+ "<td>NAME : </td>"
					+ "<td>"+rs.getString("name")+"</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>DATE OF BIRTH : </td>"
					+ "<td>"+rs.getString("dob")+"</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>EMAIL  : </td>"
					+ "<td>"+rs.getString("email")+"</td>"
					+ "</tr>"
					+ "</table>"
					+ "<input type='submit' value='update'/>"
					+ "</form>";
			int r=rs.getInt("id");
			String s=String.valueOf(r);
				hs.setAttribute("mysess",s);
			out.println(str);
		}
		rs.close();
		st.close();
		con.close();
		}catch(Exception e)
		{
			out.println(e);
		}
		}else
		{
			out.println("looks like you have to login");
			request.getRequestDispatcher("login.html").include(request,response);
		}
	
	}
}
