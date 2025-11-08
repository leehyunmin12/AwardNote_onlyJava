import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        int choice;

        DBManager dbManager = new DBManager();

        try {
            System.out.println("***** AwardNote *****");

            System.out.println("1. 회원가입\n2. 로그인");
            System.out.print(">> ");


            SigninLogin signinLogin = new SigninLogin();
            int user_id = signinLogin.SigninLogin();

            System.out.println("------------------------------------------------------------------");

            Service service = new Service(user_id);

            while (true) {
                System.out.println("1. 등록\n2. 삭제\n3. 수정\n4. 목록 보기\n5. 검색\n6. 나가기");
                System.out.print(">> ");
                choice = scan.nextInt();
                scan.nextLine();

                if (choice == 6) break; // 나가기

                if (choice == 1) { // 등록
                    service.add();
                }

                if (choice == 2) { // 삭제
                    service.show(1);
                    service.delete();
                }

                if (choice == 3) { // 수정
                    service.show(1);
                    service.update();
                }

                if (choice == 4) { // 목록 보기
                    service.show(0);
                }
                if (choice == 5) { // 검색하기
                    service.select();
                }
            }

        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}