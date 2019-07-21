package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2352
 * **********
 * <해결방안>	: 
 * 
 * (무식한 방법 -> 2780MS 시간 나옴)
 * 
 * 그림에 있는 예제를 보면
 * 1, 2, 3, 4.... 가 어디에 연결되어 있는지만 집중해서 보면
 * 4 2 6 3 1 5 가 된다.
 * 이 때, 겹치는 부분이 없으려면 2 3 5 와 같이 내려가는 부분은 없고 증가만 해야한다.
 * 따라서 몇 개를 지워 증가하는 최대 길이의 수열을 만들면 된다.
 * 
 * dp[i]를 i번째 원소를 반드시 포함하는 i까지의 증가하는 수열의 최대 길이라고 하면,
 * A[i+1]이 A[i]보다 크면, 즉 dp[i]에 A[i+1]을 추가할 수 있다면 dp[i+1]을 구할 수 있다.
 * 따라서
 * k < i, A[i] > A[k]
 * dp[i] = max( dp[k] )
 * 
 * 이 알고리즘은 O(n^2)이 걸리는데, 입력이 40000이어서 아슬아슬하게 통과함
 * 
 * **********
 * <오답노트>	: 
 * 
 * 답을 출력할 때 N번째 원소를 반드시 포함하는 증가 수열의 길이를 출력해 틀림
 * 
 * 0 ~ N번째 원소를 포함하는 것 중 가장 긴 것을 찾게 했더니 통과
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_반도체_설계 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int conn[] = new int[N];
		for(int i = 0; i < N; i++) {
			conn[i] = sc.nextInt();
		}
		
		int dp[] = new int[N]; // i번째 원소를 반드시 포함하는 가장 긴 증가 수열
		dp[0] = 1;
		int max = 1;
		for(int curr = 1; curr < N; curr++) {
			for(int prev = 0; prev < curr; prev++) {
				if(conn[curr] > conn[prev]) {
					dp[curr] = Math.max(dp[curr], dp[prev]);
				}
			}
			dp[curr]++;
			max = Math.max(dp[curr], max);
		}
		System.out.println(max);
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
