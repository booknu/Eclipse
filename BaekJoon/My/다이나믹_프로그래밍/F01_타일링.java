package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/1793
 * **********
 * <해결방안>	: 
 * 
 * dp[i]는 2xi 사각형을 채우는 방법의 수
 * dp[i] = dp[i - 1] + dp[i - 2] * 2
 * (2x1을 채우는 방법은 1가지, 2x2를 채우는 방법은 2가지)
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 2x2 사각형일 경우는 2번 세야 되는데 1번만 셌음
 * 2. BigInteger 안 쓰면 오버플로 뜸
 * 3. N = 0일 때 답은 1이어야함
 * 4. N = 1일 때 dp[1]에 대해 값을 설정할 때 배열 범위 밖에 접근함
 * 
 * **********
 * @author LENOVO
 *
 */
public class F01_타일링 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while((line = sc.readLine()) != null && line.length() != 0) {
			int N = Integer.parseInt(line);
			if(N == 0) {
				System.out.println(1);
				continue;
			}
			BigInteger dp[] = new BigInteger[N];
			dp[0] = new BigInteger(1 + "");
			if(N > 1) {
				dp[1] = new BigInteger(3 + "");
			}
			for(int i = 2; i < N; i++) {
				dp[i] = dp[i - 1].add(dp[i - 2]).add(dp[i - 2]);
			}
			System.out.println(dp[N - 1]);
		}
	}
	
	public static void printArr(int arr[]) {
		for(int i = 0; i < arr.length; i++) {
			System.out.println(i + ": " + arr[i]);
		}
		System.out.println();
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
