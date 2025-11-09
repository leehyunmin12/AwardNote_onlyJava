import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class BaseService {
    Scanner scan = new Scanner(System.in);
    DBManager dbManager = new DBManager();
    Connection connection = dbManager.getConnection();
    int user_id, aof;

    public BaseService(int user_id) throws SQLException, ClassNotFoundException {
        this.user_id = user_id;
    }
    abstract void printHeader(String title);
}