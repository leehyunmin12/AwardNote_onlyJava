import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int arr2[] = new int[1000];
        String arr[][] = new String[1000][3];
        String Cname, Cdate, Cmemo;
        String none;

        // DB (mysql)
        String url = "jdbc:mysql://localhost:3306/member?serverTimezone=Asia/Seoul";
        String user = "root";
        String password = "leehm2292!"; // DB 비밀번호

        int number,len=0,index=0;

        while(true){
            System.out.println("1. 등록"+"\n"+"2. 삭제"+"\n"+"3. 수정"+"\n"+"4. 목록 보기"+"\n"+"5. 나가기");
            number = scan.nextInt();
            if(number == 5) break; // 나가기
            if(number == 1){ // 등록
                arr2[len] = len+1;
                none = scan.nextLine();
                System.out.print("자격증(상장) 명 ex)MosExcel : ");
                arr[len][0] = scan.nextLine();
                System.out.print("취득 날짜 ex)2024.05.06 : ");
                arr[len][1] = scan.nextLine();
                System.out.print("메모 : ");
                arr[len][2] = scan.nextLine();
                len++;
                System.out.println("입력하신 값이 등록되었습니다.");
            }

            if(number == 2){ // 삭제
                System.out.println("------------------------------------------------------------------");
                for(int i=0;i<len;i++){
                    if(arr2[i]!=-1) {
                        System.out.println(arr2[i]);
                        System.out.println("자격증(상장) 명 : " + arr[i][0]);
                        System.out.println("취득 날짜 : " + arr[i][1]);
                        System.out.println("메모 : " + arr[i][2]);
                        System.out.println("------------------------------------------------------------------");
                    }
                }
                System.out.println("삭제할 번호를 입력하시오.");
                int n = scan.nextInt();
                n-=1;
                arr2[n] = -1;
                System.out.println("입력한 번호의 값이 삭제되었습니다.");
            }
            if(number == 3){
                System.out.println("------------------------------------------------------------------");
                for(int i=0;i<len;i++){
                    if(arr2[i]!=-1) {
                        System.out.println(arr2[i]);
                        System.out.println("자격증(상장) 명 : " + arr[i][0]);
                        System.out.println("취득 날짜 : " + arr[i][1]);
                        System.out.println("메모 : " + arr[i][2]);
                        System.out.println("------------------------------------------------------------------");
                    }
                }
                System.out.print("수정할 번호를 입력하시오.");
                int n = scan.nextInt();
                n-=1;
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
                System.out.println("수정이 완료되었습니다.");
            }
            if(number == 4){ // 목록 보기
                System.out.println("------------------------------------------------------------------");
                for(int i=0;i<len;i++){
                    if(arr2[i]!=-1) {
                        System.out.println(arr2[i]);
                        System.out.println("자격증(상장) 명 : " + arr[i][0]);
                        System.out.println("취득 날짜 : " + arr[i][1]);
                        System.out.println("메모 : " + arr[i][2]);
                        System.out.println("------------------------------------------------------------------");
                    }
                }
            }
        }

    }
}