package project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
// TODO Auto-generated constructor stub 
	}

	Connection con;

	public void init(ServletConfig config) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","admin"); 
		} catch (ClassNotFoundException e) {
// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (SQLException e) {
// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        String s1 = request.getParameter("uname");
	        String s2 = request.getParameter("pword");

	        PreparedStatement pstmt = con.prepareStatement("select * from rating where uname=? and pword=?");
	        pstmt.setString(1, s1);
	        pstmt.setString(2, s2);
	        ResultSet rs = pstmt.executeQuery();

	        response.sendRedirect("dashboard.html?registered=true");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}