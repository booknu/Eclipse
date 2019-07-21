package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2616
 * **********
 * <해결방안>	: 
 * 
 * (그림 참조)
 * 
 * dp[i][j] = j+1개의 객차를 사용해 i번째 까지 태울 수 있는 사람의 최대값
 * 
 * 일단 객차 1개만을 사용해 사람을 태울 수 있는 최대값을 구하는건 (dp[i][0])
 * O(N) 시간으로 가능하다.
 * 그렇게 dp[i][0]을 다 채운 뒤
 * 
 * dp[i][1]을 구할 때는 dp[i-capacity][0]에 객차 1개를 추가해보고
 * 그것이 그 이전까지 구한 dp (dp[i-1][1])와 비교해 크면 max를 갱신한다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. capacity개 만큼 미리 더해놓는 sum[]을 구해놨더니 속도가 2배 빨라짐
 * 
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_소형기관차 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt(); // 객차 수
		int people[] = new int[N];
		for(int i = 0; i < N; i++) {
			people[i] = sc.nextInt();
		}
		int capacity = sc.nextInt();
		if(capacity == 0) {
			System.out.println(0);
			System.exit(0);
		}
		
		int dp[][] = new int[N][3];
		int start = capacity - 1;
		int sum[] = new int[N];
		// dp 초기화 (1개 객차만 사용)
		for(int i = start; i < N; i++) {
			if(i == start) {
				for(int j = 0; j < capacity; j++) {
					dp[i][0] += people[j];
				}
				sum[i] = dp[i][0];
			} else {
				sum[i] = sum[i - 1] - people[i - capacity];
				sum[i] = sum[i] + people[i];
				dp[i][0] = Math.max(sum[i], dp[i - 1][0]);
			}
		}
		
		// 2개, 3개 객차 사용
		for(int i = 1; i < 3; i++) {
			start = capacity * i;
			for(int j = start; j < N; j++) {
				dp[j][i] = Math.max(dp[j - capacity][i - 1] + sum[j],
						dp[j - 1][i]);
			}
		}
		
		int max = 0;
		for(int i = 0; i < N; i++) {
			max = Math.max(dp[i][2], max);
		}
		System.out.println(max);
	}
	
	public static void sumPrint(int[] sum) {
		System.out.println("--sum--");
		for(int i = 0; i < sum.length; i++) {
			System.out.println(i + ": " + sum[i]);
		}
		System.out.println();
	}
	
	public static void dpPrint(int[][] dp, int index) {
		for(int i = 0; i < dp.length; i++) {
			System.out.println(i + ": " + dp[i][index]);
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
