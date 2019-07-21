package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p240
 * **********
 * <해결방안>	: 
 * 
 * dp[i] = i까지의 최소 난이도
 * dp[i] = min( dp[i-3], dp[i-4], dp[i-5]에 최근 3, 4, 5개의
 * 										 난이도를 더한 수)
 * 
 * 
 * **********
 * <오답노트>	: 
 * 
 * getLevel() 함수에서 실수가 있었던 것 같음
 * isLevel(레벨, 시작, 끝) 함수를 만들었었는데
 * 레벨을 인자로 줄 필요가 없다는 것을 깨닫고 소스를 고쳤더니
 * 문제 해결
 * 
 * **********
 * @author LENOVO
 *
 */
public class 원주율_외우기 {

	static char[] seq;
	static int INF = 12345678;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			seq = sc.nextLine().trim().toCharArray();
			int N = seq.length;
			int[] dp = new int[N];
			
//			System.out.println(isLevel(3, 0, 2));
//			System.exit(0);
			
			// dp 처음 3개 ~ 5개 초기화
			for(int i = 2; i < 5; i++) {
				int lv = getLevel(0, i);
				dp[i] = lv;
			}
			
			for(int index = 5; index < N; index++) {
				dp[index] = INF;
				for(int num = 3; num <= 5; num++) {
					int prev = index - num;
					if(prev < 2) {
						continue;
					}
					int lv = getLevel(prev + 1, index);;
					dp[index] = Math.min(dp[index], dp[prev] + lv);
				}
			}
			
//			for(int i = 0; i < N; i++) {
//				if(i % 5 == 0) {
//					System.out.println();
//				}
//				System.out.print(dp[i] + " ");
//			}
//			System.out.println();
			System.out.println(dp[N - 1]);
		}
	}
	/**
	 * 수열이 레벨에 해당하는지?
	 * @param lv 0 ~ 4 (일관성을 위해)
	 * @param from 수열의 시작 index
	 * @param to 수열의 끝 index
	 * @return 맞으면 그에 해당하는 값 (1, 2, 4, 5, 10)
	 */
	public static int getLevel(int from, int to) {
		// 모든 숫자가 같음
		boolean isLevel = true;
		for(int i = from; i < to; i++) {
			if(seq[i] != seq[i + 1]) {
				isLevel = false;;
			}
		}
		if(isLevel) {
			return 1;
		}
		
		// 숫자가 1씩 증가 or 감소
		boolean progressive = true;
		int diff = seq[from] - seq[from + 1];
		for(int i = from + 1; i < to; i++) {
			if(seq[i] - seq[i + 1] != diff) {
				progressive = false;
			}
		}
		if(progressive && (diff == -1 || diff == 1)) {
			return 2;
		}
		isLevel = true;
		
		// 두 개의 숫자가 번갈아가며 나옴
		isLevel = true;
		int even = seq[from];
		int odd = seq[from + 1];
		for(int i = from + 2; i <= to; i++) {
			if(((i - from) & 1) == 1) {
				if(seq[i] != odd) {
					isLevel = false;
				}
			} else {
				if(seq[i] != even) {
					isLevel = false;
				}
			}
		}
		if(isLevel) {
			return 4;
		}
		
		// 숫자가 등차수열
		if(progressive) {
			return 5;
		}
		
		// 모두 아니면
		return 10;
	}
	
	public static void solve() {
		
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
