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
 * Servlet implementation class Review
 */
@WebServlet("/rev")
public class Review extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Review() {
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
			String s1 = request.getParameter("product");
			String s2 = request.getParameter("rating");
			String s3 = request.getParameter("review");
			PreparedStatement pstmt = con.prepareStatement("insert into rev values(?,?,?)");
			pstmt.setString(1, s1);
			pstmt.setString(2, s2);
			pstmt.setString(3, s3);
			pstmt.executeUpdate();
			request.getSession().setAttribute("message", "Your review was submitted successfully!");
			response.sendRedirect("dashboard.html");
		} catch (SQLException e) {
// TODO Auto-generated catch block 
			request.getSession().setAttribute("error", "Failed to submit review. Please try again.");
			e.printStackTrace();
			response.sendRedirect("dashboard.html");
		} catch (IOException e) {
// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}
}