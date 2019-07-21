package 백트래킹2_줄다리기문제;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class __ppt소스코드 {

	static char[][] c = new char[45000][101], hs = new char[45000][101];
	static int n, cp[] = new int[45000], inp[] = new int[100];
	static double sum, sumd2, sol;

	public static void input() {
		FastScanner sc = new FastScanner();
		Random rand = new Random();
		n = sc.nextInt();
		for(int i = 0; i < n; i++) {
//			inp[i] = rand.nextInt(450) + 1;
			inp[i] = sc.nextInt();
		}
	}

	public static void dynamic() {
		sum = 0;	// 몸무게 총 합
		for(int i = 0; i < n; i++) {
			sum = sum + (double)inp[i];
		}
		sumd2 = sum / 2.0;	// 몸무게 총 합의 반
		sol = sumd2;	// sol = sumd2라고 가정
		cp[0] = 1;
		for(int i = 0; i <= sumd2; i++) {	// i = [0 ~ sumd2]
			for(int j = 0; j < cp[i]; j++) {	// j = [0 ~ cp[i])
				for(int k = 0; k < n; k++) {	// k = [0 ~ n)
					if(hs[i + inp[k]][c[i][j] + 1] == 0
							&& c[i][j] < n/2) {
						hs[i + inp[k]][c[i][j] + 1] = 1;
						c[i + inp[k]][cp[i + inp[k]]] = (char)(c[i][j] + 1); /////
						cp[i + inp[k]]++;
						if(c[i][j] + 1 == n/2 && Math.abs((i + inp[k]) - sumd2) < sol) {
							sol = Math.abs(i + inp[k] - sumd2);
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int i = 0; i < T; i++) {
			input();
			dynamic();
			if(i > 0) {
				System.out.println();
			}
			System.out.print((int)(sumd2 - sol) + " " + (int)(sumd2 + sol));
		}
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
