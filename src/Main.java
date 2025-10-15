import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
//        int arr2[] = new int[1000];
//        String arr[][] = new String[1000][3];
        String none;

        // DB (mysql)
        String url = "jdbc:mysql://localhost:3306/member?serverTimezone=Asia/Seoul";
        String user = "root";
        String password = "leehm2292!"; // DB 비밀번호

        int number, len = 0, user_id = 0;

        try {
            // JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            System.out.println("1. 회원가입\n2. 로그인");
            int us = scan.nextInt();
            while(true) {
                if (us == 1) {
                    String sql = "INSERT INTO login (username, password, email) VALUES (?, ?, ?)";
                    String sql2 = "SELECT * FROM login WHERE username = ?";
                    try (PreparedStatement pst = connection.prepareStatement(sql);
                    PreparedStatement pst2 = connection.prepareStatement(sql2)) {

                        System.out.print("이메일을 입력하세요 : ");
                        none = scan.nextLine();
                        String email = scan.nextLine();
                        System.out.print("아이디를 입력하세요 : ");
                        String id = scan.nextLine();
                        System.out.print("비밀번호를 입력하세요 : ");
                        String pw = scan.nextLine();

                        pst.setString(1, id);
                        pst.setString(2, pw);
                        pst.setString(3, email);
                        pst2.setString(1, id);

                        ResultSet rs = pst2.executeQuery();

                        if (rs.next()) System.out.println("입력하신 회원은 이미 존재합니다.");
                        else{
                            System.out.println("회원가입이 완료되었습니다.");
                            pst.executeUpdate();
                            us = 3;
                        }

                    } catch (Exception e) {
                        System.out.println("회원가입을 실패하였습니다.");
                        e.getStackTrace();
                    }
                }

                if (us == 2 || us == 3) {
                    String sql = "SELECT * FROM login WHERE username = ?";

                    if (us == 2) none = scan.nextLine();
                    System.out.print("아이디를 입력하세요 : ");
                    String id = scan.nextLine();
                    System.out.print("비밀번호를 입력하시오 : ");
                    String pw = scan.nextLine();

                    try (PreparedStatement pst = connection.prepareStatement(sql)) {

                        pst.setString(1, id);
                        ResultSet rs = pst.executeQuery();

                        if (!rs.next()) {
                            System.out.println("입력하신 아이디는 존재하지 않습니다.");
                        } else {
                            if (pw.equals(rs.getString("password"))) {
                                user_id = rs.getInt("id");
                                System.out.println("로그인이 완료되었습니다.");
                                break;
                            } else {
                                System.out.println("비밀번호가 알맞지 않습니다.");
                            }
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                        e.getStackTrace();
                    }

                    us = 3;
                }
            }

            while (true) {
                System.out.println("1. 등록\n2. 삭제\n3. 수정\n4. 목록 보기\n5. 나가기");
                number = scan.nextInt();
                if (number == 5) break; // 나가기

                if (number == 1) { // 등록

                    // 입력 받고 배열에 저장
//                    arr2[len] = len + 1;
//                    none = scan.nextLine();
//                    System.out.print("자격증(상장) 명 ex)MosExcel : ");
//                    arr[len][0] = scan.nextLine();
//                    System.out.print("취득 날짜 ex)2024.05.06 : ");
//                    arr[len][1] = scan.nextLine();
//                    System.out.print("메모 : ");
//                    arr[len][2] = scan.nextLine();

                    // DB에 저장
                    add(connection, scan, len, user_id);
                }

                if (number == 2) { // 삭제

//                    System.out.println("------------------------------------------------------------------");
//                    for (int i = 0; i < len; i++) {
//                        if (arr2[i] != -1) {
//                            System.out.println(arr2[i]);
//                            System.out.println("자격증(상장) 명 : " + arr[i][0]);
//                            System.out.println("취득 날짜 : " + arr[i][1]);
//                            System.out.println("메모 : " + arr[i][2]);
//                            System.out.println("------------------------------------------------------------------");
//                        }
//                    }
                    show(connection, 1, user_id);
                    delete(scan, connection);
                }

                if (number == 3) { // 수정
                    /*
                    System.out.println("------------------------------------------------------------------");
                    for (int i = 0; i < len; i++) {
                        if (arr2[i] != -1) {
                            System.out.println(arr2[i]);
                            System.out.println("자격증(상장) 명 : " + arr[i][0]);
                            System.out.println("취득 날짜 : " + arr[i][1]);
                            System.out.println("메모 : " + arr[i][2]);
                            System.out.println("------------------------------------------------------------------");
                        }
                    }
                    System.out.print("수정할 번호를 입력하시오.");
                    int n = scan.nextInt();
                    n -= 1;
                    System.out.println("현재 값 : 자격증(상장) 명 : " + arr[n][0]);
                    System.out.print("수정 할 값을 입력하시오 : ");
                    none = scan.nextLine();
                    Cname = scan.nextLine();
                    arr[n][0] = Cname;
                    System.out.println("현재 값 : 취득 날짜 : " + arr[n][1]);
                    System.out.print("수정 할 값을 입력하시오 : ");
                    Cdate = scan.nextLine();
                    arr[n][1] = Cdate;
                    System.out.println("현재 값 : 메모 : " + arr[n][2]);
                    System.out.print("수정 할 값을 입력하시오 : ");
                    Cmemo = scan.nextLine();
                    arr[n][2] = Cmemo;
                     */
                    show(connection, 1, user_id);
                    update(scan, connection);
                }

                if (number == 4) { // 목록 보기
                    System.out.println("------------------------------------------------------------------");
                    /* for (int i = 0; i < len; i++) {
                        if (arr2[i] != -1) {
                            System.out.println(arr2[i]);
                            System.out.println("자격증(상장) 명 : " + arr[i][0]);
                            System.out.println("취득 날짜 : " + arr[i][1]);
                            System.out.println("메모 : " + arr[i][2]);
                            System.out.println("------------------------------------------------------------------");
                        }
                    } */
                    show(connection, 1, user_id);
                    System.out.println("즐겨찾기를 설정하고 싶으면 1\n즐겨찾기 목록을 보고싶다면 2\n아니면 3을 입력하세요");
                    number = scan.nextInt();
                    if(number == 1){
                        System.out.println("즐겨찾기 할 번호를 입력하세요.");
                        int n = scan.nextInt();

                        String sql = "UPDATE list SET Cfavorite=true WHERE id=?";
                        try(PreparedStatement pst = connection.prepareStatement(sql)) {
                            pst.setInt(1, n);

                            pst.executeUpdate();
                            System.out.println("즐겨찾기가 완료되었습니다.");
                        } catch (Exception e){
                            e.getStackTrace();
                        }

                    }
                    if(number == 2) {
                         show(connection, 2, user_id);
                    }
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    public static void show(Connection connection, int aof, int user_id) {
        System.out.println("------------------------------------------------------------------");
        if(aof == 1){
            String sql = "SELECT * FROM list WHERE user_id = "+user_id; // 쿼리문
            try (PreparedStatement pst = connection.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while(rs.next()) {
                    int id = rs.getInt("id");
                    String Cn = rs.getString("Cname");
                    String Cd = rs.getString("Cdate");
                    String Cm = rs.getString("Cmemo");
                    System.out.println(id);
                    System.out.println("자격증(상장) 명 : " + Cn);
                    System.out.println("취득 날짜 : " + Cd);
                    System.out.println("메모 : " + Cm);
                }
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        if(aof == 2){
            String sql2 = "SELECT * FROM list WHERE Cfavorite = true and user_id = "+user_id;
            try(PreparedStatement pst2 = connection.prepareStatement(sql2);
                ResultSet rs = pst2.executeQuery()){

                while(rs.next()){
                    int id = rs.getInt("id");
                    String Cn = rs.getString("Cname");
                    String Cd = rs.getString("Cdate");
                    String Cm = rs.getString("Cmemo");
                    System.out.println(id);
                    System.out.println("자격증(상장) 명 : " + Cn);
                    System.out.println("취득 날짜 : " + Cd);
                    System.out.println("메모 : " + Cm);
                }

            } catch (Exception e){
                e.getStackTrace();
            }
        }
        System.out.println("------------------------------------------------------------------");
    }

    public static void add(Connection connection, Scanner scan, int len, int user_id) throws SQLException {
        String none;
        String sql = "INSERT INTO list (Cname, Cdate, Cmemo, Cfavorite, user_id) VALUES (?, ?, ?, 0, ?)";
        PreparedStatement pst = connection.prepareStatement(sql);

        none = scan.nextLine();
        System.out.print("자격증(상장) 명 ex)MosExcel : ");
        String Cn = scan.nextLine();
        System.out.print("취득 날짜 ex)2024.05.06 : ");
        String Cd = scan.nextLine();
        System.out.print("메모 : ");
        String Cm = scan.nextLine();

        // 값 바인딩
        pst.setString(1, Cn);
        pst.setString(2, Cd);
        pst.setString(3, Cm);
        pst.setInt(4, user_id);

        pst.executeUpdate(); // 실행

        System.out.println("입력하신 값이 등록되었습니다.");
    }

    public static void delete(Scanner scan, Connection connection){
        System.out.println("삭제할 번호를 입력하시오.");
        int n = scan.nextInt();

        String sql2 = "DELETE FROM list WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql2)){
            pst.setInt(1, n);
            pst.executeUpdate();
        } catch (Exception e){
            e.getStackTrace();
        }

        System.out.println("입력한 번호의 값이 삭제되었습니다.");
    }

    public static void update(Scanner scan, Connection connection) {
        String none;
        System.out.print("수정할 번호를 입력하시오. ");
        int id = scan.nextInt();
        System.out.print("새로운 자격증(상장) 명 : ");
        none = scan.nextLine();
        String nCn = scan.nextLine();
        System.out.print("새로운 취득 날짜 : ");
        String nCd = scan.nextLine();
        System.out.print("새로운 메모 : ");
        String nCm = scan.nextLine();

        String sql2 = "UPDATE list SET Cname=?, Cdate=?, Cmemo=? WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql2)) {
            ///System.out.println(nCn+nCd+nCm);
            pst.setString(1, nCn);
            pst.setString(2, nCd);
            pst.setString(3, nCm);
            pst.setInt(4, id);

            pst.executeUpdate();
        } catch (Exception e){
            e.getStackTrace();
        }

        System.out.println("수정이 완료되었습니다.");
    }
}
