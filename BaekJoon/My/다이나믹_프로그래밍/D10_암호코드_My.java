package 다이나믹_프로그래밍;
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
 * dp[i]는 i번째 코드까지의 경우의 수인데
 * dp[i][0]는 마지막 2자리가 각각으로 해석된 경우,
 * dp[i][1]은 마지막 2자리가 하나로 해석된 경우임
 * 
 * i == 0인 경우
 * (앞뒤 == 10 or 20)
 * dp[i][0] = 0  (묶이는 경우가 없음)
 * dp[i][1] = dp[i - 1][0]
 * 
 * (앞뒤 != 10 or 20)
 * dp[i][0] = 0
 * dp[i][1] = 0 (묶을 수 없음
 * 
 * i != 0인 경우
 * dp[i][0] = dp[i - 1][0] + dp[i - 1][1]
 * dp[i][1] = (두 자리가 묶일 수 있는 경우) dp[i - 1][0]
 * 
 * 
 * 
 * **********
 * <오답노트>	: 
 * 
 * 0의 처리를 잘못함.
 * 101010인 경우 방법은 10 10 10 1가지인데,
 * 1 0 1 0 1 0과 같은 경우도 셈.
 * 따라서 현재 자리가 0인 경우에는
 * i번쨰와 i-1번째가 묶이지 않는 경우가 없고, 묶이는 경우만 있음
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_암호코드_My {

	static int DIV = 1000000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		char[] code = sc.nextLine().toCharArray();

		// dp[i][0] = i번째 코드까지 경우의 수 (i번째와 i-1번째는 하나로 묶이지 않음)
		// dp[i][1] = i번째 코드까지 경우의 수 (i번째와 i-1번째는 하나로 묶임)
		int[][] dp = new int[code.length][2];
		dp[0][0] = code[0] == '0'? 0 : 1;
		for(int i = 1; i < code.length; i++) {
			// 마지막 두 자리가 하나의 코드로 해석될 수 있는 경우
			int last = (code[i - 1] - '0') * 10 + code[i] - '0';
			if(code[i] == '0') {
				if(last == 10 || last == 20) {
					dp[i][0] = 0;
					dp[i][1] = dp[i - 1][0];
				}  else {
					dp[i][0] = 0;
					dp[i][1] = 0;
				}
			} else if(last >= 10 && last <= 26) {
				dp[i][0] = (dp[i - 1][0] + dp[i - 1][1]) % DIV;
				dp[i][1] = dp[i - 1][0];
			} else {
				dp[i][0] = (dp[i - 1][0] + dp[i - 1][1]) % DIV;
			}
		}
		//		for(int i = 0; i < code.length; i++) {
		//			for(int j = 0; j < 2; j++) {
		//				System.out.print(dp[i][j] + " ");
		//			}
		//			System.out.println();
		//		}
		System.out.println((dp[code.length - 1][0] + dp[code.length - 1][1]) % DIV);
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
