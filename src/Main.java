import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String arr[][] = new String[1000][3];
        String Cname, Cdate, Cmemo;
        String none;

        int number,index,len=0;

        while(true){
            System.out.println("1. 등록"+"\n"+"2. 삭제"+"\n"+"3. 수정"+"\n"+"4. 자세히 보기"+"\n"+"5. 나가기");
            number = scan.nextInt();
            if(number == 5) break; // 나가기
            if(number == 1){
                none = scan.nextLine();
                System.out.print("자격증(상장) 명 ex)MosExcel : ");
                arr[len][0] = scan.nextLine();
                System.out.print("취득 날짜 ex)2024.05.06 : ");
                arr[len][1] = scan.nextLine();
                System.out.print("메모 : ");
                arr[len][2] = scan.nextLine();
                len++;
            }
            if(number == 2){
                System.out.println("------------------------------------------------------------------");
                for(int i=0;i<len;i++){
                    System.out.println(i+1);
                    System.out.println("자격증(상장) 명 : "+arr[i][0]);
                    System.out.println("취득 날짜 : "+arr[i][1]);
                    System.out.println("메모 : "+arr[i][2]);
                    System.out.println("------------------------------------------------------------------");
                }
                System.out.println("삭제할 번호를 입력하시오.");
                int n = scan.nextInt();
                n-=1;
            }


        }

    }
}