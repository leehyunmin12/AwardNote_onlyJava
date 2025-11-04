import java.sql.*;

public class DBManager {
    String url = "jdbc:mysql://localhost:3306/member?serverTimezone=Asia/Seoul";
    String user = "root";
    String password = "leehm2292!";
    private Connection connection;

    public DBManager() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, password);
    }

    public Connection getConnection() { return connection; }

}
