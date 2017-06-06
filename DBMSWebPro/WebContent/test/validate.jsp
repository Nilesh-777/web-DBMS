<%@page import="java.sql.PreparedStatement"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body backgroung="textured.jpg">
<%
String uname=request.getParameter("n1");            int i=0;
String password=request.getParameter("p1");      String pfd;
try{
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/WebDBMS","root","nilesh");
PreparedStatement pr=con.prepareStatement("select name,password1 from WebApp");
ResultSet rs=(ResultSet) pr.executeQuery();
   
while(rs.next())
{
	if(rs.getString("name").equals(uname))
	{
		out.println("entered in if uname");
		i=1;
		pfd=rs.getString("password1"); //out.println("password "+rs.getString("password1"));
		if(pfd.equals(password))
		{
			response.sendRedirect("Info.jsp"); break;
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



%>
</body>
</html>