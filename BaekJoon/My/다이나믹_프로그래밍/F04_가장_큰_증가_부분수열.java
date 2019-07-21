package 다이나믹_프로그래밍;

/************
 * <주소>		: https://www.acmicpc.net/problem/11055
 * **********
 * <해결방안>	: 
 * 
 * F(n)을 seq(n)을 마지막 원소로 하는 가장 큰 부분수열의 합이라고 할 때,
 * F(n)을 구하려면 이전까지 구해진 것들을 이용해
 * "그것에 현재 원소가 추가된다면?"을 기본으로 점화식을 만듬
 * 
 * ==> F(n) = F(0 ~ n-1) + seq(n)
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 처음에는 원소가 가장 많은 부분수열을 구하는줄 알고 실수를 함
 * 
 * **********
 * @author LENOVO
 *
 */
public class F04_가장_큰_증가_부분수열 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FastScanner sc = new FastScanner();
		
		int N = sc.nextInt();
		int seq[] = new int[N];
		for(int i = 0; i < N; i++) {
			seq[i] = sc.nextInt();
		}
		
		// n을 마지막으로 하는 가장 큰 부분수열 수
		int dp[] = new int[N];
		int max = 0;
		dp[0] = seq[0];
		for(int i = 1; i < N; i++) {
			for(int j = 0; j < i; j++) {
				if(seq[j] < seq[i]) {
					dp[i] = Math.max(dp[i], dp[j]);
				}
			}
			dp[i] += seq[i];
			max = Math.max(dp[i], max);
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
