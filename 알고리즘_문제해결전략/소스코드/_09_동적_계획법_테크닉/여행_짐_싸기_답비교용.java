package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
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
public class 여행_짐_싸기_답비교용 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Random rand = new Random();
//		int T = 1;
//		for (int testCase = 0; testCase < T; testCase++) {
//			int N = 100;
//			int W = rand.nextInt(1000) + 1;
//			Thing[] A = new Thing[N];
//			for(int i = 0; i < N; i++) {
//				A[i] = new Thing(i + "", rand.nextInt(100) + 1, rand.nextInt(1000) + 1);
//			}
//			
//			solve(N, W, A);
//		}
		for(int i = 0; i < 1000000000; i++)
		test();
	}
	
	public static void test() {
		FastScanner sc = new FastScanner();
		Random rand = new Random();
		int n = 20;
		int cap = rand.nextInt(1000) + 1;
		int vol[] = new int[n];
		int need[] = new int[n];
		String name[] = new String[n];
		Thing[] A = new Thing[n];
		for(int i = 0; i < n; i++) {
			name[i] = i + "";
			vol[i] = rand.nextInt(300) + 1;
			need[i] = rand.nextInt(1000) + 1;
			A[i] = new Thing(name[i], vol[i], need[i]);
		}
		
		ArrayList<Integer> ansA = 여행_짐_싸기_책버전.solve(n, cap, vol, need, name);
		ArrayList<Thing> ansB = solve(n, cap, A);
		
		int sumA = 0, sumB = 0;
		for(int i = 0; i < ansA.size(); i++) {
			sumA += need[ansA.get(i)];
		}
		for(int i = 0; i < ansB.size(); i++) {
			sumB += ansB.get(i).d;
		}
		
		if(sumA != sumB) {
			System.out.println("----- wrong case detected -----");
			System.out.println("ans: " + sumA + " " + sumB);
			System.out.println("cap: " + cap);
			System.out.println("<Total List>");
			for(int i = 0; i < n; i++) {
				System.out.println(name[i] + " " + vol[i] + " " + need[i]);
			}
			System.out.println("\n<Ans List: " + ansA.size() + ">");
			for(int i = 0; i < ansA.size(); i++) {
				System.out.println(name[ansA.get(i)] + " " + vol[ansA.get(i)] + " " + need[ansA.get(i)]);
			}
			System.out.println("\n<My List: " + ansB.size() + ">"); 
			for(int i = 0; i < ansB.size(); i++) {
				Thing t = ansB.get(i);
				System.out.println(t.name + " " + t.v + " " + t.d);
			}
			
			ArrayList<Integer> onlyA = new ArrayList<Integer>();
			ArrayList<Integer> onlyB = new ArrayList<Integer>();
			for(int i = 0; i < ansA.size(); i++) {
				boolean searched = false;
				for(int j = 0; j < ansB.size(); j++) {
					if(ansA.get(i) == Integer.parseInt(ansB.get(j).name)) {
						searched = true;
						break;
					}
				}
				if(!searched) {
					onlyA.add(ansA.get(i));
				}
			}
			for(int i = 0; i < ansB.size(); i++) {
				boolean searched = false;
				for(int j = 0; j < ansA.size(); j++) {
					if(ansA.get(j) == Integer.parseInt(ansB.get(i).name)) {
						searched = true;
						break;
					}
				}
				if(!searched) {
					onlyB.add(Integer.parseInt(ansB.get(i).name));
				}
			}
			
			System.out.print("\nOnly A had: ");
			for(int i = 0; i < onlyA.size(); i++) {
				System.out.print(onlyA.get(i) + " ");
			}
			System.out.print("\nOnly B had: ");
			for(int i = 0; i < onlyB.size(); i++) {
				System.out.print(onlyB.get(i) + " ");
			}
			System.out.println();
			
			System.out.print("continue? >> ");
			sc.nextLine();
			System.out.println("continued...");
			
		}
	}
	
	public static ArrayList<Thing> solve(int N, int W, Thing[] A) {
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
		for(int i = 0; i <= W; i++) {
			if(dp[i] > dp[max]) {
				max = i;
			}
		}
		// TEST
		for(Thing t : dpList[max]) {
			sum += t.d;
		}
//		System.out.println(dp[max] + " " + dpList[max].size());
//		for(Thing t : dpList[max]) {
//			System.out.println(t.name);
//		}
		// TEST
//		return sum + "";
		
		// test
//		for(Thing a : A) {
//			System.out.println(a.name + " " + a.v + " " + a.d);
//		}
//		for(int i = 0; i < dp.length; i++) {
//			System.out.println(i + ": " + dp[i]);
//			for(Thing a : dpList[i]) {
//				System.out.println(a.name + " " + a.v + " " + a.d);
//			}
//		}
		
		// test
		return dpList[max];
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
