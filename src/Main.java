import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int choice;

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

                switch (choice){
                    case 6: System.exit(0); // 종료
                    case 1: service.add(); break;
                    case 2: service.show(1); service.delete(); break;
                    case 3: service.show(1); service.update(); break;
                    case 4: service.show(0); break;
                    case 5: service.select(); break;
                    default: System.out.println("잘못된 입력입니다. 다시 입력하세요");
                }
            }

        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}