package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/1660
 * **********
 * <해결방안>	: 
 * 
 * 일단 N개 이하의 대포알로 쌓을 수 있는 사면체들을 모두 구해놓음
 * 이 때 N <= 300000일때 사면체의 size는 120이 넘어가지 않음
 * 사면체는 size가 1 늘어날수록
 * 1 ~ size개를 더한만큼의 대포알을 더 필요로하게 된다. (등차수열)
 * bullets[size] = bullets[size - 1] + ((size + 1) * size / 2)
 * 
 * dp[i]는 i개의 대포알로 만들 수 있는 사면체의 최소 개수인데,
 * dp[i] = min( dp[i - 사면체 1개] ) + 1
 * (현재 구할 i개의 대포알에 사면체 1개만큼 뺀 것에 그 사면체를 추가)
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 처음에는 평면 삼각형인줄 알았는데
 *    알고보니 입체 사면체였음
 * 
 * 2. 가장 큰 것부터 하나씩 빼보면서 세봤는데 그렇게 되면 N = 91인 경우에는
 *    5: 35와 6: 56이 합쳐져 결과가 2가 나와야 되는데,
 *    7: 84 1개와 1: 1 4개가 합쳐져 결과가 5가 나옴
 * 
 * **********
 * @author LENOVO
 *
 */
public class D5_캡틴_이다솜 {

	static int INF = 300001;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		
		// 30만까지면 121개면 충분함
		int[] bullets = new int[121];
		bullets[1] = 1;
		int size, bullet;
		for(size = 2; ; size++) {
			bullet = bullets[size - 1] + (size + 1) * size / 2;
			if(bullet > N) {
				break;
			}
			bullets[size] = bullet;
		}
//		printArr(bullets, size);

		int[] dp = new int[N + 1];
		dp[1] = 1;
		for(int i = 2; i <= N; i++) {
			dp[i] = INF;
			for(int j = 1; j < size; j++) {
				if(bullets[j] <= i) {
					dp[i] = Math.min(dp[i], dp[i - bullets[j]]);
				}
			}
			dp[i]++;
		}
//		printArr(dp, dp.length);
		System.out.println(dp[N]);
	}
	
	public static void printArr(int[] arr, int size) {
		for(int i = 0; i < size; i++) {
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
