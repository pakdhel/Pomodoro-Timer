
import javax.swing.plaf.TableHeaderUI;
import java.util.Scanner;

public class waktu{

    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.print("enter the seconds: "); int sec = in.nextInt();
        System.out.print("enter the minutes: "); int min = in.nextInt();
        System.out.print("enter the hours: ");int hrs = in.nextInt();

        while (hrs > 0 || min > 0 || sec > 0) {
            System.out.println(hrs+":"+min+":"+sec);
            sec--;
            if (sec < 0) {
                min--;
                sec = 59;
                if (min < 0) {
                    hrs--;
                    min = 59;

                }
            }
            Thread.sleep(1000);

        }
    }
}
