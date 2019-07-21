package w1;
import java.util.Scanner;

/**
 * 1이 포함되는지에 대해서 실수를 함
 * 
 * @author LENOVO
 *
 */
public class _3N_plus_1_변형 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextInt()) {
			int start = sc.nextInt();
			int target = sc.nextInt();
			
			boolean exists = false;
			
			if(start == target) {
				exists = true;
				break;
			}
			
			while(start != 1) {
				if((start & 1) == 1) {
					start = start * 3 + 1;
				} else {
					start /= 2;
				}
				if(start == target) {
					exists = true;
					break;
				}
			}
			
			System.out.println(exists? "Y" : "N");
		}
	}
}
