import java.sql.*;

public class AuthService {
    private UserDAO userDAO;

    public AuthService() {
        userDAO = new UserDAO();
    }

    public User authenticate(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean isDoctor(User user) {
        return "DOCTOR".equalsIgnoreCase(user.getRole());
    }
}
