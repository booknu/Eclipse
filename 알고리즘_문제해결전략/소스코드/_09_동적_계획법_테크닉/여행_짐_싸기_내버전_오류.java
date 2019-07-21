package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/************
 * <주소>		: p281
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
public class 여행_짐_싸기_내버전_오류 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt();
			int W = sc.nextInt();
			Thing[] A = new Thing[N];
			for(int i = 0; i < N; i++) {
				A[i] = new Thing(sc.nextToken(), sc.nextInt(), sc.nextInt());
			}
			
			Thing[] B = Arrays.copyOf(A, A.length);
			
			Arrays.sort(A, new Comparator<Thing>() {
				@Override
				public int compare(Thing a, Thing b) {
					return a.v - b.v;
				}
			});
			
			int dp[] = new int[W + 1]; // 부피가 i인 조합 중 절박도가 가장 높은 경우
			ArrayList<Thing>[] dpList = new ArrayList[W + 1];
			for(int i = 0; i <= W; i++) {
				dpList[i] = new ArrayList<Thing>();
				for(int j = 0; j < N; j++) {
					int prev = i - A[j].v;
					if(prev >= 0) {
						if(!dpList[prev].contains(A[j])) {
							int prevSum = dp[prev] + A[j].d;
							// 현재 조합이 이전까지 구한 dp[i]보다 정답에 가깝고
							// prev가 0, 즉 이전 부피가 0이거나
							// prev가 0이 아니고 prev 부피에 대한 조합이 있을 경우
							if(prevSum > dp[i] && (dp[prev] != 0 || prev == 0)) {
								// dp[i] = dp[prev] + 추가한 물건의 절박도
								dp[i] = prevSum;
								// prev의 dpList에 현재 물건 추가
								dpList[i] = new ArrayList<Thing>(dpList[prev]);
								dpList[i].add(A[j]);
							}
						}
					} else {
						// prev가 범위를 벗어나면 그 이후 것은 볼 필요도 없이 break
						break;
					}
				}
			}
			
			int sum = 0;
			int max = 0;
			for(int i = 0; i < N; i++) {
				System.out.println(A[i].name + ": " + A[i].v + " " + A[i].d);
			}
			for(int i = 0; i <= W; i++) {
				System.out.println(i + ": " + dp[i]);
				if(dp[i] > dp[max]) {
					max = i;
				}
			}
			for(Thing t : dpList[max]) {
				sum += t.d;
			}
			System.out.println(sum + " " + dpList[max].size());
			for(Thing t : dpList[max]) {
				System.out.println(t.name);
			}
			
			// test
//			for(Thing a : A) {
//				System.out.println(a.name + " " + a.v + " " + a.d);
//			}
//			for(int i = 0; i < dp.length; i++) {
//				System.out.println(i + ": " + dp[i]);
//				for(Thing a : dpList[i]) {
//					System.out.println(a.name + " " + a.v + " " + a.d);
//				}
//			}
		}
	}
	
	static class Thing {
		public String name;
		public int v, d; // 부피, 절박도
		public Thing(String n, int v, int w) {
			name = n;
			this.v = v;
			this.d = w;
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
