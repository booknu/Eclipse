package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p355 회전초밥
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 입력 ]]
 *    평범하게 입력을 받되,
 *    스시의 가격은 항상 100의 배수이므로 각 가격을
 *    100으로 나눈 수를 가격으로 사용해도 될 것이다.
 *    또한 예산도 10010과 같이 애매하게 100 이하의 숫자가 남더라도
 *    사용할 수 없으니까 100으로 나눈 수를 사용해도 문제가 없을 것이다.
 *    
 * 
 * 2. [[ dp 과정 ]]
 *    dp[i] = i의 가격으로 만들 수 있는 최대 선호도 합
 *    그런데 i의 범위가 M(21억) 까지여서 고민을 했었는데,
 *    가격이 100의 배수라는 점을 이용하여 M을 100으로 나눈 값
 *    2000만 만큼의 배열만 잡으면 됐다.
 *    
 *    i = [0, M]을 순서대로 루프를 돌며
 *    i에 모든 음식들의 가격을 빼보면 이전 가격 prev가 나올텐데,
 *    이 prev가 0 이상이면 dp[prev]에 이 음식을 추가해봐도 된다는 소리다.
 *    따라서 prev 가격에서의 최대 선호도 조합에 현재 food[j]의 선호도를 더해서
 *    dp[i]의 max값이 되는지를 검사해보며 최대 선호도 조합의 값을 찾으면 된다.
 *  
 *    
 * 3. 위 방법에 슬라이딩윈도를 사용하여 공간복잡도를 줄였다.
 *    초밥의 최대 가격은 20000 / 100 = 200 임을 고려하면 dp[i]를 구할 때 필요한 것은
 *    prev = [i-200, i-1]이다.
 *    따라서 dp의 크기를 201로 잡고, 각 index에 접근 할 때 price % 201을 하면 된다.
 * 
 * **********
 * <오답노트>	: 
 *
 * 처음에는 dp[i]의 i를 가격으로 사용하면 너무 많은 배열이 잡힐 것 같아
 * dp[i]를 선호도의 합(i)에 따른 최소 가격으로 사용해 봤는데,
 * 생각해보니 최대 선호도를 구하는 문제여서 dp 배열의 크기를 최대 선호도로
 * 정해야 하는데 그것을 하지 못하는 문제점이 있었고, 최악의 경우 가격이 가장 싼
 * 100원짜리가 선호도가 20이라면 최대 선호도는 21억 / 100 * 20이 될텐데 이것은
 * 위 dp보다 20배 가량 많은 수여서 문제가 있었다.
 * 
 * 코드 제출시 런타임 에러나는데 왜 그런지 모르겠음
 * 
 * **********
 * @author LENOVO
 *
 */
public class 회전초밥_슬라이딩윈도_런타임오류 {

	static final int SIZE = 201;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			// (n = [1, 20], m = [1, Integer.max], price = [0, 20000] (multiple of 100)
			int N = sc.nextInt();
			int M = sc.nextInt() / 100; // because price is multiple of 100, use price / 100 to price
			int[] price = new int[N]; // use price / 100 to price
			int[] prefer = new int[N];
			for(int i = 0; i < N; i++) {
				price[i] = sc.nextInt() / 100;
				prefer[i] = sc.nextInt();
			}
			
			int dp[] = new int[SIZE]; // i: price, value: max sum or prefer
			int maxPrefer = 0;
			for(int i = 0; i <= M; i++) { // current price
				int index = i % SIZE;
				dp[index] = 0; // reset dp
				for(int j = 0; j < N; j++) { // food[j]
					// prev price must not be negative
					if(i >= price[j]) {
						// try to add food[j] to prev price
						int prev = (i - price[j]) % SIZE;
						dp[index] = Math.max(dp[index], dp[prev] + prefer[j]);
					}
				}
				maxPrefer = Math.max(maxPrefer, dp[index]);
			}
			System.out.println(maxPrefer);
		}
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
