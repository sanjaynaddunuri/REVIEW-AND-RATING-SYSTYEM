package project;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/reviews")
public class ReviewServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String JDBC_USER = "SYSTEM";
    private static final String JDBC_PASSWORD = "admin";
    private final Gson gson = new Gson();

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oracle JDBC Driver not found", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            List<Review> reviews = fetchReviews(conn);
            String jsonResponse = gson.toJson(reviews);
            response.getWriter().write(jsonResponse);
            
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }

    private List<Review> fetchReviews(Connection conn) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT P_NAME, RATING, REVIEW FROM rev ORDER BY P_NAME";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                reviews.add(new Review(
                    rs.getString("P_NAME"),
                    rs.getInt("RATING"),
                    rs.getString("REVIEW")
                ));
            }
        }
        return reviews;
    }

    public static class Review {
        private final String pName;
        private final int rating;
        private final String review;

        public Review(String pName, int rating, String review) {
            this.pName = pName;
            this.rating = rating;
            this.review = review;
        }

        public String getPName() { return pName; }
        public int getRating() { return rating; }
        public String getReview() { return review; }
    }
}