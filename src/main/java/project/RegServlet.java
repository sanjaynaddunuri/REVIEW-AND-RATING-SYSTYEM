package project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegServlet
 */
@WebServlet("/reg")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegServlet() {
		super();
// TODO Auto-generated constructor stub 
	}

	Connection con;

	public void init(ServletConfig config) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SYSTEM", "admin");
		} catch (ClassNotFoundException e) {
// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (SQLException e) {
// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
// TODO Auto-generated method stub 
		try {
			String s1 = request.getParameter("fname");
			String s2 = request.getParameter("lname");
			String s3 = request.getParameter("email");
			String s4 = request.getParameter("phone");
			String s5 = request.getParameter("uname");
			String s6 = request.getParameter("pword");
			PreparedStatement pstmt = con.prepareStatement("insert into rating values(?,?,?,?,?,?)");
			pstmt.setString(1, s1);
			pstmt.setString(2, s2);
			pstmt.setString(3, s3);
			pstmt.setString(4, s4);
			pstmt.setString(5, s5);
			pstmt.setString(6, s6);
			
			pstmt.executeUpdate();
			response.sendRedirect("login.html?registered=true");

		} catch (SQLException e) {
// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (IOException e) {
// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}
}