package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2602
 * **********
 * <해결방안>	: 
 * 
 * dp[다리종류][i][j]
 * = j: 밟을 알파벳 index
 *   i: i번째 다리
 *   i까지 올 때 알파벳 순서대로 올 수 있는 경우
 *   
 * 인데, j같은 경우는 loop를 통해 사용하지 않을 수 있다.
 * j는 바로 이전 알파벳까지의 dp만이 필요하므로, 그 이전은 필요가 없다.
 * 그런데 다리를 0 ~ N 순서대로 하면 수정된 이전 dp를 사용할 수 있기 때문에
 * 역순으로 N-1 ~ 0으로 사용한다.
 * 
 * dp[i][n]을 구하려면
 * dp[another][0 ~ n-1] 의 합들을 구하면 된다
 * (이전 알파벳까지 온 것에서 현재 알파벳으로 건너 뛰면 됨)
 * 이 때 배열을 1차원 더 사용하지 않기 때문에 dp[i][n]이 수정되면
 * dp[i][n + 1]을 구하는데 영향을 주니까 역순으로 검사해야함
 * (dp[i][n] -> dp[i][n-1] 순으로 가면 수정된 dp가 영향을 주지 않음)
 * 
 * (그림 참조)
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_돌다리_건너기 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		char[] order = sc.readLine().toCharArray();
		char[][] bridge = new char[2][];
		bridge[0] = sc.readLine().toCharArray();
		bridge[1] = sc.readLine().toCharArray();
		int N = bridge[0].length;
		int M = order.length;
		
		int[][] dp = new int[2][N];
		// 1번째 알파벳에 대해 초기화
		for(int j = 0; j < N; j++) {
			if(bridge[0][j] == order[0]) {
				dp[0][j] = 1;
			}
			if(bridge[1][j] == order[0]){
				dp[1][j] = 1;
			} 
		}
//		dpPrint(dp);
		for(int i = 1; i < M; i++) {
			for(int j = N - 1; j >= 0; j--) {
				for(int kind = 0; kind < 2; kind++) {
					dp[kind][j] = 0;
					if(bridge[kind][j] == order[i]) {
						int another = kind ^ 1;
						for(int k = 0; k < j; k++) {
							dp[kind][j] += dp[another][k];
						}
					}
				}
			}
//			if(i == 1) dpPrint(dp);
		}
		int result = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < 2; j++) {
				result += dp[j][i];
			}
		}
//		dpPrint(dp);
		System.out.println(result);
	}
	
	public static void dpPrint(int[][] dp) {
		for(int i = 0; i < dp.length; i++) {
			for(int j = 0; j < dp[0].length; j++) {
				System.out.print(dp[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("----------------------------------");
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
