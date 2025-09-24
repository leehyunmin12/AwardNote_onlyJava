import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class testdb {
    public static void main(String[] args) {
        // DB 접속 정보
        String url = "jdbc:mysql://localhost:3306/member?serverTimezone=Asia/Seoul";
        String user = "root"; // DB 계정jdbc:mysql://localhost:3306/testdb?serverTimezone=UTC
        String password = "leehm2292!"; // DB 비밀번호

        Scanner scanner = new Scanner(System.in);

        try {
            // JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB 연결
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ DB 연결 성공!");

            // 사용자 입력 받기
            System.out.print("아이디 입력: ");
            String username = scanner.nextLine();

            System.out.print("비밀번호 입력: ");
            String pwd = scanner.nextLine();

            System.out.print("이메일 입력: ");
            String email = scanner.nextLine();

            // SQL 준비
            String sql = "INSERT INTO login (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 값 바인딩
            pstmt.setString(1, username);
            pstmt.setString(2, pwd);  // ⚠️ 실제로는 비밀번호를 해싱해야 함!
            pstmt.setString(3, email);

            // 실행
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("🎉 회원가입 성공!");
            } else {
                System.out.println("❌ 회원가입 실패!");
            }

            // 정리
            pstmt.close();
            conn.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
