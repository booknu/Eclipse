package lgCodemonster;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://codemonster.lgcns.com/contest/practice/3
 * **********
 * <해결방안>	: 
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 문자열순환_small {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(sc.readLine());
		for (int testCase = 0; testCase < T; testCase++) {
			int N = Integer.parseInt(sc.readLine());
			String A = sc.readLine();
			String B = sc.readLine();

			int res = -1;
			for(int i = 0; i < N; i++) {
				boolean correct = true;
				if(A.charAt(i) != B.charAt(0)) {
					continue;
				}
				for(int j = 0; j < i; j++) {
					if(A.charAt(j) != B.charAt(j + N - i)) {
						correct = false;
						break;
					}
				}
				for(int j = i; j < N; j++) {
					if(A.charAt(j) != B.charAt(j - i)) {
						correct = false;
						break;
					}
				}
				if(correct) {
					res = i;
					break;
				}
			}
			if(res == 0 || res == -1) {
				System.out.println(res);
			} else {
				System.out.println(N - res);
			}
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
