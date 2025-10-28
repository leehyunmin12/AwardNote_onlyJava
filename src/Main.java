import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int choice;

        // DB (mysql)
        String url = "jdbc:mysql://localhost:3306/member?serverTimezone=Asia/Seoul";
        String user = "root";
        String password = "leehm2292!"; // DB 비밀번호
        int user_id = 0;

        try {
            // JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            System.out.println("***** AwardNote *****");

            System.out.println("1. 회원가입\n2. 로그인");
            System.out.print(">> ");

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

            System.out.println("------------------------------------------------------------------");

            while (true) {
                System.out.println("1. 등록\n2. 삭제\n3. 수정\n4. 목록 보기\n5. 검색\n6. 나가기");
                System.out.print(">> ");
                choice = scan.nextInt();
                if (choice == 6) break; // 나가기

                if (choice == 1) { // 등록
                    add(connection, scan, user_id);
                }

                if (choice == 2) { // 삭제
                    show(connection,  user_id, scan, 1);
                    delete(scan, connection);
                }

                if (choice == 3) { // 수정
                    show(connection, user_id, scan, 1);
                    update(scan, connection);
                }

                if (choice == 4) { // 목록 보기
                    show(connection, user_id, scan, 0);
                }
                if(choice == 5) { // 검색하기
                    select(scan, connection, user_id);
                }

            }
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    private static void select(Scanner scan, Connection connection,  int user_id) {
        int cnt = 0;

        scan.nextLine();
        System.out.print("검색할 자격증 명 : ");
        String selectCertificationName = scan.nextLine();

        String selectQuery = "SELECT * FROM list WHERE user_id = " + user_id + " and CertificationName like ?";
        String param = "%" + selectCertificationName + "%";
        // System.out.println(selectQuery);

        try (PreparedStatement selectPst = connection.prepareStatement(selectQuery)){
            selectPst.setString(1, param);
            ResultSet rs = selectPst.executeQuery();

                System.out.println("------------------------------------------------------------------");
                System.out.println("                           <검색된 결과 값>                         ");
                System.out.println("------------------------------------------------------------------");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String CertificationName = rs.getString("CertificationName");
                    String CertificationDate = rs.getString("CertificationDate");
                    System.out.println(id);
                    System.out.println("자격증 명 : "+CertificationName);
                    System.out.println("취득날짜 : "+CertificationDate);
                    cnt = 1;
                }

            if(cnt == 0) System.out.println("                      일치하는 결과 값이 없습니다.");

            System.out.println("------------------------------------------------------------------");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private static void addFavorite(Scanner scan, Connection connection) {
        System.out.println("즐겨찾기 할 번호를 입력하세요 : ");
        int idNumber = scan.nextInt();

        String updateQuery = "UPDATE list SET isFavorite=true WHERE id=?";
        try(PreparedStatement updatePst = connection.prepareStatement(updateQuery)) {
            updatePst.setInt(1, idNumber);

            updatePst.executeUpdate();
            System.out.println("즐겨찾기가 완료되었습니다.");
        } catch (Exception e){
            e.getStackTrace();
        }
    }

    public static void show(Connection connection, int user_id, Scanner scan, int aof) {
        int cnt = 0;
        System.out.println("------------------------------------------------------------------");
        System.out.println("                           <목록>                                  ");
        System.out.println("------------------------------------------------------------------");

            String selectionQuery = "SELECT * FROM list WHERE user_id = "+user_id; // 쿼리문
            try (PreparedStatement selectionPst = connection.prepareStatement(selectionQuery);
                 ResultSet rs = selectionPst.executeQuery()) {

                while(rs.next()) {
                    int id = rs.getInt("id");
                    String CertificationName = rs.getString("CertificationName");
                    String CertificationDate = rs.getString("CertificationDate");
                    String CertificationMemo = rs.getString("CertificationMemo");
                    System.out.println(id);
                    System.out.println("자격증(상장) 명 : " + CertificationName);
                    System.out.println("취득 날짜 : " + CertificationDate);
                    System.out.println("메모 : " + CertificationMemo);
                    cnt = 1;
                }
                if (cnt == 0) System.out.println("                      목록이 비어 있습니다.");

            } catch (Exception e) {
                e.getStackTrace();
            }
        System.out.println("------------------------------------------------------------------");
        //
        if (cnt == 1 && aof != 1) {
            System.out.println("즐겨찾기를 설정하고 싶으면 1\n즐겨찾기 목록을 보고싶다면 2\n아니면 3을 입력하세요");
            System.out.print(">> ");
            int choice = scan.nextInt();

            if (choice == 1) addFavorite(scan, connection);
            if (choice == 2) {
                System.out.println("------------------------------------------------------------------");
                System.out.println("                           <즐겨찾기 목록>                          ");
                System.out.println("------------------------------------------------------------------");

                String selectFavoriteQuery = "SELECT * FROM list WHERE isFavorite = true and user_id = " + user_id;
                try (PreparedStatement selectFavoritePst = connection.prepareStatement(selectFavoriteQuery);
                     ResultSet rs = selectFavoritePst.executeQuery()) {

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String CertificationName = rs.getString("CertificationName");
                        String CertificationDate = rs.getString("CertificationDate");
                        String CertificationMemo = rs.getString("CertificationMemo");
                        System.out.println(id);
                        System.out.println("자격증(상장) 명 : " + CertificationName);
                        System.out.println("취득 날짜 : " + CertificationDate);
                        System.out.println("메모 : " + CertificationMemo);
                    }
                    System.out.println("------------------------------------------------------------------");

                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }
    }

    public static void add(Connection connection, Scanner scan, int user_id) throws SQLException {
        String insertionQuery = "INSERT INTO list (CertificationName, CertificationDate, CertificationMemo, isFavorite, user_id) VALUES (?, ?, ?, 0, ?)";
        PreparedStatement pst = connection.prepareStatement(insertionQuery);

        scan.nextLine(); // 공백 빼주기
        System.out.print("자격증(상장) 명 ex)MosExcel : ");
        String CertificationName = scan.nextLine();
        System.out.print("취득 날짜 ex)2024.05.06 : ");
        String CertificationDate = scan.nextLine();
        System.out.print("메모 : ");
        String CertificationMemo = scan.nextLine();

        pst.setString(1, CertificationName);
        pst.setString(2, CertificationDate);
        pst.setString(3, CertificationMemo);
        pst.setInt(4, user_id);

        pst.executeUpdate(); // 실행

        System.out.println("입력하신 값이 등록되었습니다.");
    }

    public static void delete(Scanner scan, Connection connection){
        System.out.print("삭제할 번호를 입력하시오 : ");
        int number = scan.nextInt();

        String deleteQuery = "DELETE FROM list WHERE id=?";
        try (PreparedStatement deletePst = connection.prepareStatement(deleteQuery)){
            deletePst.setInt(1, number);
            deletePst.executeUpdate();
        } catch (Exception e){
            e.getStackTrace();
        }

        System.out.println("입력한 번호의 값이 삭제되었습니다.");
    }

    public static void update(Scanner scan, Connection connection) {

        System.out.print("수정할 번호를 입력하시오 : ");
        int updateNumber = scan.nextInt();
        System.out.print("새로운 자격증(상장) 명 : ");
        scan.nextLine(); // 공백 빼주기
        String newCertificationName = scan.nextLine();
        System.out.print("새로운 취득 날짜 : ");
        String newCertificationDate = scan.nextLine();
        System.out.print("새로운 메모 : ");
        String newCertificationMemo = scan.nextLine();

        String updateQuery = "UPDATE list SET CertificationName=?, CertificationDate=?, CertificationMemo=? WHERE id=?";
        try (PreparedStatement updatePst = connection.prepareStatement(updateQuery)) {
            ///System.out.println(nCn+nCd+nCm);
            updatePst.setString(1, newCertificationName);
            updatePst.setString(2, newCertificationDate);
            updatePst.setString(3, newCertificationMemo);
            updatePst.setInt(4, updateNumber);

            updatePst.executeUpdate();
        } catch (Exception e){
            e.getStackTrace();
        }

        System.out.println("수정이 완료되었습니다.");
    }
}