import java.sql.*;
import java.util.*;

public class Service {

    Scanner scan = new Scanner(System.in);
    DBManager dbManager = new DBManager();
    Connection connection = dbManager.getConnection();
    int user_id;

    public Service() throws SQLException, ClassNotFoundException {

    }
}