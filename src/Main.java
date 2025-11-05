import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        int choice;

        DBManager dbManager = new DBManager();
        Connection connection = dbManager.getConnection();

        try {
            System.out.println("***** AwardNote *****");

            System.out.println("1. 회원가입\n2. 로그인");
            System.out.print(">> ");


            SigninLogin signinLogin = new SigninLogin();
            int user_id = signinLogin.SigninLogin();
            System.out.println(user_id);

            System.out.println("------------------------------------------------------------------");

            Service service = new Service(user_id);

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
                    System.out.println("자격증 명 : " + CertificationName);
                    System.out.println("취득날짜 : " + CertificationDate);
                    cnt = 1;
                }

            if(cnt == 0) System.out.println("                      일치하는 결과 값이 없습니다.");

            System.out.println("------------------------------------------------------------------");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private static void addFavorite(Scanner scan, Connection connection) {
        System.out.print("즐겨찾기 할 번호를 입력하세요 : ");
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