//import java.sql.*;
//import java.util.Scanner;
//
//public class Service {
//    //DBManager dbManager = new DBManager();
//
//    //private Conncetion conncetion;
//    private int user_id;
//
//    public Service(Connection connection, int user_id) {
//        //this.conncetion = connection;
//        this.user_id = user_id;
//    }
//
//    public void add(Scanner scan) throws SQLException {
//        String insertionQuery = "INSERT INTO list (CertificationName, CertificationDate, CertificationMemo, isFavorite, user_id) VALUES (?, ?, ?, 0, ?)";
//        PreparedStatement pst = connection.prepareStatement(insertionQuery);
//
//        scan.nextLine(); // 공백 빼주기
//        System.out.print("자격증(상장) 명 ex)MosExcel : ");
//        String CertificationName = scan.nextLine();
//        System.out.print("취득 날짜 ex)2024.05.06 : ");
//        String CertificationDate = scan.nextLine();
//        System.out.print("메모 : ");
//        String CertificationMemo = scan.nextLine();
//
//        pst.setString(1, CertificationName);
//        pst.setString(2, CertificationDate);
//        pst.setString(3, CertificationMemo);
//        pst.setInt(4, user_id);
//
//        pst.executeUpdate(); // 실행
//
//        System.out.println("입력하신 값이 등록되었습니다.");
//    }
//
//}
