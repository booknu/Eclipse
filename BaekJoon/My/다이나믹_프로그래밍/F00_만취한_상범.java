package 다이나믹_프로그래밍;
import java.util.Scanner;

/************
 * <주소>		: https://www.acmicpc.net/problem/6359
 * **********
 * <해결방안>	: 
 * 
 * n <= 100이니까 O(n^2)도 잘 돌아간다.
 * 따라서 round 1 ~ 100까지 하나하나 해보면서 최종 결과가 어떻게 나오는지
 * 확인한다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 무조건 열기, 무조건 닫기, 닫힌건 열고 열린건 닫기
 *   3가지가 로테이션으로 도는줄 알고 실수함
 * 
 * **********
 * @author LENOVO
 *
 */
public class F00_만취한_상범 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int n = sc.nextInt();
			byte[] prison = new byte[n + 1];
			for(int i = 1; i <= n; i++) {
				switch(i) {
					case 1:
						// 무조건 연다
						for(int j = i; j <= n; j += i) {
							prison[j] |= 1;
						}
						break;
					case 2:
						// 무조건 닫는다
						for(int j = i; j <= n; j += i) {
							prison[j] &= 0;
						}
						break;
					default:
						// 열려있으면 닫고, 닫혀있으면 연다.
						for(int j = i; j <= n; j += i) {
							prison[j] ^= 1;
						}
						break;
				}
			}
			
			int count = 0;
			for(int i = 1; i <= n; i++) {
				if((prison[i] & 1) == 1) {
					count++;
				}
			}
			System.out.println(count);
		}
	}	
}
