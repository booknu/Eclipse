package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2624
 * **********
 * <해결방안>	: 
 * 
 * dp[i] = i원을 만들 수 있는 방법의 수
 * coin[0]만을 사용하여 dp를 구하고,
 * coin[1]을 추가하여 dp를 구하고,
 * ...
 * 
 * T - coin[i]에서부터 거꾸로 돌리는 이유는
 * 중복되는 경우를 거르기 위하여
 * ex)
 * 1000원 만들기
 * 900원에 100원 추가
 * 800원에 200원 추가
 * 순서로 해야 중복x
 * 
 * 800원에 200원 추가
 * 900원에 100원 추가
 * 의 경우에는
 * 800 + 100의 경우가 900에 포함되고,
 * 그것이 900원의 경우에 포함되는데,
 * 이 때 800원에서 200원을 추가하는 경우와
 * 900원에서 100원을 추가하는 경우 (800 + 100 + 100)가 중복됨
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 각 동전의 개수 제한이 있는데 그것을 어떻게 처리할지 아이디어가 떠오르지 않음
 * 
 * - 동전을 사용하는 순서를 먼저 강제해서 그것을 해결함
 *   (첫 번째 동전으로 만들 수 있는 dp 계산 -> 두 번째 ~~~)
 * 
 * - for 루프에 coin[i]와 같은 배열 접근이나, T - coin[i]와 같은 연산을 하지 않고
 *   변수로 선언하여 먼저 연산해놓으면 속도가 빨라짐
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_동전_바꿔주기 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		int K = sc.nextInt();
		int coin[] = new int[K];
		int num[] = new int[K];
		for(int i = 0; i < K; i++) {
			coin[i] = sc.nextInt();
			num[i] = sc.nextInt();
		}	
		
		int dp[] = new int[T + 1];
		dp[0] = 1;
		// 각각의 동전에 대해 dp를 구함
		for(int i = 0; i < K; i++) {
			// 현재 금액에 coin[i]를 추가해서 만들 수 있는
			// 금액을 구함
			for(int won = T - coin[i]; won >= 0; won--) {
				// won을 만드는 방법이 0개이면 굳이 next를 계산할 필요가 없음
				if(dp[won] == 0) {
					continue;
				}
				// coin[i]를 use개 만큼 사용할 경우
				for(int use = 1; use <= num[i]; use++) {
					int add = use * coin[i];
					int next = won + add;
					if(next > T) {
						break;
					}
					// won에 coin[i]를 use개 추가하여 만들 수 있는 금액
					// dp[next]에 dp[won]을 추가한다.
					dp[next] += dp[won];
				}
				if(i == 0) {
					for(int loop = 0; loop <= T; loop++) {
						System.out.println(dp[loop]);
					}
				}
			}
		}
		System.out.println(dp[T]);
	}	
	static class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner(String s) {
			try {
				br = new BufferedReader(new FileReader(s));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		public FastScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String nextToken() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(nextToken());
		}

		long nextLong() {
			return Long.parseLong(nextToken());
		}

		double nextDouble() {
			return Double.parseDouble(nextToken());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
