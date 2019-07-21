package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/10942
 * **********
 * <해결방안>	: 
 * 
 * 팰린드롬은 홀수개일 때와 짝수개일 때가 있다.
 * 
 * 홀수개일 경우
 * 1개일 때 true 이고
 * 그 이후로 양 옆에 추가될 때
 * 그 수가 같지 않으면 그 뒤로 어떤 것을 추가해도 false가 된다.
 * 
 * 짝수개일 경우
 * 2개일 때는 true / false이고
 * 그 이후로 양 옆에 추가될 때
 * 그 수가 같지 않으면 그 뒤로 어떤 것을 추가해도 false가 된다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 처음에는 무식한 팰린드롬으로 S, E가 주어질 때 한 번의 팰린드롬을 계산할 때
 *    E - S번을 계산해 100만번이 되는 질문에 시간 초과가 남
 *    
 * 2. 위의 해결방안으로 코드를 바꾼 뒤 출력했는데 여전히 시간 초과가 남.
 *    그래서 BufferedWriter를 통해 출력했더니 통과됨
 *    
 * 3. 무조건 S >= E라는 사실을 깨닫고 dp[i][j] = true; dp[j][i] = true 같이
 *    2번 연산을 한 것을 1번으로 줄이고
 *    출력을 할 때 문자열 연산을 하지 않게 했더니 시간이 더 줄어듬
 * 
 * **********
 * @author LENOVO
 *
 */
public class D08_팰린드롬 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int seq[] = new int[N];
		for(int i = 0; i < N; i++) {
			seq[i] = sc.nextInt();
		}

		boolean dp[][] = new boolean[N][N];
		// 홀수 일 때
		for(int i = 0; i < N; i++) {
			for(int j = 0; i - j >= 0 && i + j < N; j++) {
				int left = i - j;
				int right = i + j;
				if(seq[left] != seq[right]) {
					break;
				}
				dp[left][right] = true;
			}
		}

		// 짝수 일 때
		for(int i = 0; i < N - 1; i++) {
			int left = i;
			int right = i + 1;
			while(left >= 0 && right < N) {
				if(seq[left] != seq[right]) {
					break;
				}
				dp[left][right] = true;
				left--;
				right++;
			}
		}

//		System.out.println();
//		for(int i = 0; i < N; i++) {
//			for(int j = 0; j < N; j++) {
//				System.out.print("[i: " + i + "][j: " + j + "] " + (dp[i][j]? 1 : 0) + " ");
//			}
//			System.out.println();
//		}

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		int M = sc.nextInt();
		for(int question = 0; question < M; question++) {
			int S = sc.nextInt();
			int E = sc.nextInt();
			if(dp[S - 1][E - 1]) {
				out.write("1\n");
			} else {
				out.write("0\n");
			}
			
//			System.out.println(dp[S - 1][E - 1]);
		}
		out.flush();
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
