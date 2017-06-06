package Serv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class allSetting implements ServletContextListener {

  
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	
    public void contextInitialized(ServletContextEvent sce)  { 
    	String dbName="WebDBMS";
    	String dbPass="nilesh";
    	
        ServletContext sc=sce.getServletContext();
        sc.setAttribute("passmysql",dbPass);
        String pm=(String)sc.getAttribute("passmysql");
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root",pm);
		PreparedStatement str=con.prepareStatement("create database webDBMS");
		str.executeUpdate();
		con.close();
        }catch(Exception e)
        {
        	System.out.println(e);
        }
        try
        {
		Connection cont=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,"root",pm);
		String crTable="create table WebApp"
				+ "("
				+ "id int primary key auto_increment,"
				+ "name varchar(30),"
				+ "password1 varchar(30),"
				+ "dob date,"
				+ "email varchar(30)"
				+ ")";
		PreparedStatement st=cont.prepareStatement(crTable);
		st.execute();
		
		st.close();
		cont.close();
		}catch(Exception e)
		{
			System.out.println(e);
		}
        
    }
	
}
