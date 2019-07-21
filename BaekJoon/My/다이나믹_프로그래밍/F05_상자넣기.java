package 다이나믹_프로그래밍;

/************
 * <주소>		: https://www.acmicpc.net/problem/1965
 * **********
 * <해결방안>	: 
 * 
 * (아이디어)
 * 1. F(n)은 n번째 상자를 마지막으로 포함하는 최대 경우의 수
 * 2. F(n)은 F(0 ~ n-1) 까지 중 최대값을 포함하는 상자
 *    ==> F(n) = max( F(0 ~ n-1) ) + 1      (n 번째 상자를 포함하므로 +1)
 * 3. F(0 ~ N)까지 중 max 값을 찾아내면 됨
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 맨 끝 상자를 반드시 포함해야 하는 것이 아닌데 마지막 출력을 dp[마지막상자]로 함
 *   ==> loop를 돌 때 dp 중 max를 찾아내게 함
 * 
 * - 각 dp는 최소값이 1이어야 하는데 아무것도 포함하지 못할 경우 0이 출력됐음
 * 
 * **********
 * @author LENOVO
 *
 */
public class F05_상자넣기 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FastScanner sc = new FastScanner();
		
		int n = sc.nextInt();
		int boxes[] = new int[n];
		for(int i = 0; i < n; i++) {
			boxes[i] = sc.nextInt();
		}
		
		int dp[] = new int[n];
		int max = 1;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < i; j++) {
				if(boxes[j] < boxes[i]) {
					dp[i] = Math.max(dp[i], dp[j]);
				}
			}
			dp[i]++;
			if(dp[i] > max) {
				max = dp[i];
			}
		}
		
		System.out.println(max);
	}

}
//class FastScanner {
//	BufferedReader br;
//	StringTokenizer st;
//
//	public FastScanner(String s) {
//
//		try {
//
//			br = new BufferedReader(new FileReader(s));
//		} catch (FileNotFoundException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	public FastScanner() {
//		br = new BufferedReader(new InputStreamReader(System.in));
//
//	}
//
//	String nextToken() {
//		while (st == null || !st.hasMoreElements()) {
//			try {
//				st = new StringTokenizer(br.readLine());
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
//		}
//		return st.nextToken();
//	}
//
//	int nextInt() {
//
//		return Integer.parseInt(nextToken());
//	}
//
//	long nextLong() {
//		return Long.parseLong(nextToken());
//	}
//
//	double nextDouble() {
//		return Double.parseDouble(nextToken());
//	}
//
//	String nextLine() {
//		String str = "";
//		try {
//			str = br.readLine();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return str;
//	}
//
//}
