import java.sql.*;

public class UserDAO {
    private static final String URL = "jdbc:mysql://172.20.54.101:3306/medicationsDB";
    private static final String USER = "nourchene";
    private static final String PASSWORD = "abcd12";
    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM Users WHERE username = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}