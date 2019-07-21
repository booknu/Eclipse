package 백트래킹2_줄다리기문제;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

/************
 * <주소>		: 
 * **********
 * <해결방안>	: 
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1 3 1 2 3
 * 1 3 1 1 2
 * 1 3 1 2 1
 * 과 같이 몸무게의 합이 같은게 있는 경우 오류 발생
 * 
 * 1 3 2 1 1 
 * 1 3 3 1 1
 * 오류 발생
 * 
 * 1 7   3 2 1 1 1 1 1
 * 넣으면 w=5일 때 n=4인 경우를 포함하지 않음
 * 
 * 1 15   1 1 1 1 1 1 1 1 1 1 2 3 4 5 6
 * 넣으면 답: (2 3 5 1(5개), 4 6 1(5개)) = 15, 15가 나와야하는데 오류
 * 
 * **********
 * @author LENOVO
 *
 */
public class Recursive {

	static int N, half;
	static int[] W;
	static ArrayList<Integer> list;
	static boolean memo[][][];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		Random rand = new Random();
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			N = sc.nextInt();
			half = N / 2;
			W = new int[N];
			int sumW = 0;
			for(int i = 0; i < N; i++) {
				W[i] = sc.nextInt();
				sumW += W[i];
			}
			
			memo = new boolean[sumW][N][N];
			list = new ArrayList<Integer>();
			
			for(int i = 0; i < N; i++) {
				recur(0, 0, i);
			}
			int minSub = Integer.MAX_VALUE;
			int minA = 0, minB = 0;
			for(int i = 0; i < list.size(); i++) {
				int a = list.get(i);
				int b = sumW - a;
				int sub = Math.abs(a - b);
				if(sub < minSub) {
					minA = a;
					minB = b;
					minSub = sub;
				}
			}
			
			if(minA > minB) {
				int temp = minA;
				minA = minB;
				minB = temp;
			}
			System.out.println(minA + " " + minB);
		}
	}

	public static void recur(int w, int n, int curr) {
		if(n == half - 1) {
			list.add(w + W[curr]);
			return;
		}
		if(curr == N - 1) {
			return;
		}
		if(n + (N - curr) < half) {
			return;
		}
		if(memo[w][n][curr]) {
			return;
		}
		
		for(int i = curr + 1; i < N; i++) {
			recur(w + W[curr], n + 1, i);
		}
		memo[w][n][curr] = true;
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
