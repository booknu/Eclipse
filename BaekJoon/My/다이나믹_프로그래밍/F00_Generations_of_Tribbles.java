package 다이나믹_프로그래밍;
import java.util.Scanner;

/************
 * <주소>		: https://www.acmicpc.net/problem/9507
 * **********
 * <해결방안>	: 
 * 
 * 걍 피보나치 구하듯이 하면 됨
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 쓸데없이 점화식 풀다가
 *   F(n) = 2*F(n-1) - F(n-4) 가 나왔는데 작동 안 함
 * 
 * **********
 * @author LENOVO
 *
 */
public class F00_Generations_of_Tribbles {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		int bound = 68;
		long fibo[] = new long[bound];
		fibo[0] = 1;
		fibo[1] = 1;
		fibo[2] = 2;
		fibo[3] = 4;
		fibo[4] = 8;
		for(int i = 5; i < bound; i++) {
			fibo[i] = fibo[i - 1] + fibo[i - 2] + fibo[i - 3] + fibo[i - 4];
		}
		
		for(int testCase = 0; testCase < T; testCase++) {
			System.out.println(fibo[sc.nextInt()]);
		}
	}

}
