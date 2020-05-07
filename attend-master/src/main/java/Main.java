import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-03-05
 * Time: 15:28
 */
public class Main {
    public static void main1(String[] args) {
       Scanner scanner = new Scanner(System.in);
       int n = scanner.nextInt();
       //0000 0010
        for (int i = 31; i >= 1 ; i-=2) {
            System.out.print(((n>>>i) & 1) + " " );
        }
        System.out.println();
        for (int i = 30; i >= 0 ; i-=2) {
            System.out.print(((n>>>i) & 1) + " " );
        }
    }
}
