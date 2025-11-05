import java.sql.*;
import java.util.Scanner;

public class SigninLogin {
    Scanner scan = new Scanner(System.in);
    int user_id;
    DBManager dbManager = new DBManager();
    Connection connection = dbManager.getConnection();

    public SigninLogin() throws SQLException, ClassNotFoundException {

        while (true) {
            int choiceLogin = scan.nextInt();
            if (choiceLogin != 1 && choiceLogin != 2 && choiceLogin != 3) System.out.println("잘못된 숫자입니다. 다시 입력하세요.");

            if (choiceLogin == 1) {
                String insertionQuery = "INSERT INTO login (username, password, email) VALUES (?, ?, ?)";
                String selectionQuery = "SELECT * FROM login WHERE username = ?";
                try (PreparedStatement insertionPst = connection.prepareStatement(insertionQuery);
                     PreparedStatement selectionPst = connection.prepareStatement(selectionQuery)) {

                    System.out.print("이메일을 입력하세요 : ");
                    scan.nextLine(); // enter 빼주기
                    String email = scan.nextLine();
                    System.out.print("아이디를 입력하세요 : ");
                    String username = scan.nextLine();
                    System.out.print("비밀번호를 입력하세요 : ");
                    String pw = scan.nextLine();

                    insertionPst.setString(1, username);
                    insertionPst.setString(2, pw);
                    insertionPst.setString(3, email);
                    selectionPst.setString(1, username);

                    ResultSet rs = selectionPst.executeQuery();

                    if (rs.next()){
                        System.out.println("입력하신 회원은 이미 존재합니다. 다시 입력해 주세요.");
                        System.out.println("1. 회원가입\n2. 로그인");
                        System.out.print(">> ");
                        continue;
                    }
                    else {
                        insertionPst.executeUpdate();
                        choiceLogin = 3;
                        System.out.println("회원가입이 완료되었습니다.");
                    }

                } catch (Exception e) {
                    System.out.println("회원가입을 실패하였습니다.");
                    System.out.println(e);
                    e.getStackTrace();
                }
            }

            if (choiceLogin == 2 || choiceLogin == 3) {
                String selectionQuery = "SELECT * FROM login WHERE username = ?";

                if (choiceLogin == 2) scan.nextLine(); // 공백 빼주기
                System.out.print("아이디를 입력하세요 : ");
                String username = scan.nextLine();
                System.out.print("비밀번호를 입력하시오 : ");
                String pw = scan.nextLine();

                try (PreparedStatement pst = connection.prepareStatement(selectionQuery)) {

                    pst.setString(1, username);
                    ResultSet rs = pst.executeQuery();

                    if (!rs.next()) {
                        System.out.println("입력하신 아이디는 존재하지 않습니다. 다시 입력해 주세요.");
                        System.out.println("1. 회원가입\n2. 로그인");
                        System.out.print(">> ");
                        continue;
                    } else {
                        if (pw.equals(rs.getString("password"))) {
                            user_id = rs.getInt("id");
                            System.out.println("로그인이 완료되었습니다.");
                            User user1 = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
                            break;
                        } else {
                            System.out.println("비밀번호가 알맞지 않습니다.");
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e);
                    e.getStackTrace();
                }

                choiceLogin = 3;
            }
        }
    }
}
