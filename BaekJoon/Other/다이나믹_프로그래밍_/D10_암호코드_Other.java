package 다이나믹_프로그래밍_;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2011
 * **********
 * <해결방안>	: 
 * 
 * D03 극장 좌석 문제와 비슷한데,
 * 
 * dp[i]는 i - 1까지의 코드에서 해석할 수 있는 경우의 수
 * 
 * code[i - 1]가 0이 아닌 경우에는 
 * dp[i] += dp[i - 1] (바로 이전 것에 한자리수만을 해석)
 * 
 * code[i - 2]code[i - 1] (두 자리수)가 해석할 수 있는 범위 (10 ~ 26)인 경우
 * dp[i] += dp[i - 2] (이전이전 것에서 두 자리수를 해석)
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_암호코드_Other{

	static int DIV = 1000000;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
		char[] code = br.readLine().toCharArray();
		
		int[] dp = new int[code.length + 1]; // i - 2까지 처리해야 되기 때문에 +1
		dp[0] = dp[1] = 1;
		for(int i = 2; i <= code.length; i++) {
			int singleDigits = code[i - 1] - '0';
			if(singleDigits > 0) {
				dp[i] = dp[i - 1];
			}
			
			int doubleDigits = (code[i - 2] - '0') * 10 + singleDigits;
			if(doubleDigits >= 10 && doubleDigits <= 26) {
				dp[i] += dp[i - 2];
			}
		}
		
		System.out.println(dp[code.length]);
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
