package 다이나믹_프로그래밍;
import java.util.Arrays;
import java.util.Scanner;

/************
 * <주소>		: https://www.acmicpc.net/source/4106136
 * **********
 * <해결방안>	: 
 * 
 * - 일단 제곱수들을 계산해 메모리에 넣어둔다.
 * - F(N)을 구하려면
 *   F(N - 1^2) + 1^2(1가지)
 *   F(N - 2^2) + 2^2
 *   ...
 *   과 같이 현재 N에서 가능한 제곱수들을 하나하나 빼보며 경우의 수를 세었다.
 *   
 *   ==>
 *   k = 1 ~ root(N)
 *   F(N) = min( F(N - K^2) + 1 )
 *   
 * **********
 * <다른풀이>	:
 * https://www.acmicpc.net/source/3333485
 * ==> 제곱수의 조합이 어떻게 되든 결과가 4를 넘지 않는다는 것을 전제로
 *     문제를 푼 것 같음
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 처음에는
 *   k = 1 ~ N-1 이라 할 때 
 *   F(N) = min( F(k) + F(N - k) ) 방식으로 모든 경우를 검사 했는데, 이럴 경우
 *   O(N^2)의 시간이 걸려 시간 초과가 되었다.
 *   
 * - 이것을 제곱수들만에 대하여 확인하게 하면
 *   제곱수의 경우의 수 = root(N)
 *   O(N * root(N))의 시간이 걸린다.
 * 
 * **********
 * @author LENOVO
 *
 */
public class D06_제곱수의_합 {

	static int INF = 9999999;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		
		
		int pow[] = new int[(int)Math.sqrt(N) + 1];
		for(int i = 1; i < pow.length; i++) {
			pow[i] = i * i;
		}
		
		int dp[] = new int[N + 1];
		Arrays.fill(dp, INF);
		dp[0] = 0;
		dp[1] = 1;
		for(int i = 2; i <= N; i++) {
			for(int j = 1; j < pow.length && i >= pow[j]; j++) {
				dp[i] = Math.min(dp[i], dp[i - pow[j]] + 1);
			}
		}
		System.out.println(dp[N]);
	}

}
