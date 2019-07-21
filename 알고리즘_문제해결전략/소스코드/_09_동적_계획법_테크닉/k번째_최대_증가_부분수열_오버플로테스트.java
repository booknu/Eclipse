package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.StringTokenizer;

/************
 * <주소>		: p299 k번째 LIS
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
public class k번째_최대_증가_부분수열_오버플로테스트 {

	public static int seq[];
	public static int lenLIS;
	public static int dp[];
	public static ArrayList<Pair> numLIS[];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		Random rand = new Random();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int N = 500; // number of sequence
			int K = 1000000000; // Kth LIS
			seq = new int[N]; // sequence
			for(int i = 0; i < N; i++) {
				// random input
//				boolean exists = true;
//				while(exists) {
//					seq[i] = rand.nextInt(100000) + 1;
//					exists = false;
//					for(int j = 0; j < i; j++) {
//						if(seq[i] == seq[j]) {
//							exists = true;
//							break;
//						}
//					}
//				}
				
				// biggest number of LIS input
				// 이 때 dp2[i]가 overflow가 남
				if((i & 1) == 0) {
					seq[i] = i + 2;
				} else {
					seq[i] = i;
				}
			}
			
			/* count length of LIS in seq *************/
			dp = new int[N]; // length of LIS's last element = seq[i]
			dp[0] = 1;
			lenLIS = 1; // length of LIS
			for(int i = 1; i < N; i++) {
				for(int j = 0; j < i; j++) {
					// element seq[i] can be added to LIS that last element = seq[j]
					if(seq[i] > seq[j]) {
						// renew dp
						dp[i] = Math.max(dp[i], dp[j]);
					}
				}
				// add element seq[i] to prev longest LIS
				dp[i]++;
				if(dp[i] > lenLIS) {
					lenLIS = dp[i];
				}
			}
			
			System.out.println("<< dp >>");
			for(int i = 0; i < N; i++) {
				System.out.print(dp[i] + " ");
			}
			System.out.println("\n" + lenLIS);
			
			long[] dp2 = new long[N]; // regardless of previous combination (LIS[i] is already maden (prev element num = dp[i])),
									  // number of LIS combination we can make
									  // that starts with LIS[i] + after
			for(int i = N - 1; i >= 0; i--) {
				// if dp[i] = ans (LIS's length)
				if(dp[i] == lenLIS) {
					// number of LIS combination which starts with seq[i] is 1
					dp2[i] = 1;
					continue;
				}
				for(int j = N - 1; j > i; j--) {
					// LIS[j] can be made by add seq[j] to LIS[i]
					// AND seq[j] can be added to LIS[i]
					if(dp[j] == dp[i] + 1 && seq[j] > seq[i]) {
						dp2[i] += dp2[j];
						if(dp2[i] > Integer.MAX_VALUE) {
							dp2[i] = (long)Integer.MAX_VALUE + 1;
						}
					}
				}
			}
			System.out.println("<< seq >>");
			for(int i = 0; i < N; i++) {
				System.out.print(seq[i] + " ");
			}
			System.out.println("\n------------");
			System.out.println("<< dp2 >>");
			for(int i = 0; i < N; i++) {
				System.out.print(dp2[i] + " ");
			}
			System.out.println("\n------------");
			
			numLIS = new ArrayList[lenLIS + 1]; // dp2 was separated by dp[LIS len]
												// and each of numLIS[LIS len] was sorted by seq[index]
			long total = 0;
			for(int i = 0; i < N; i++) {
				if(numLIS[dp[i]] == null) {
					numLIS[dp[i]] = new ArrayList<Pair>();
				}
				numLIS[dp[i]].add(new Pair(i, dp2[i]));
				if(dp[i] == 1) {
					total += dp2[i];
				}
			}
			
			for(int i = 1; i <= lenLIS; i++) {
				numLIS[i].sort(new Comparator<Pair>() {
					@Override
					public int compare(Pair a, Pair b) {
						return seq[a.index] - seq[b.index];
					}
				});
			}
			// total == 0일 때 처리하면 런타임 오류 안 남
			// 가능한 LIS 개수가 엄청 많다면?
			if (K > total) {
				K %= total;
			} 
			System.out.println(K + " " + total);
			System.out.println(lenLIS);
			System.out.println(KthCombination(1, 0, Integer.MIN_VALUE, K));
		}
	}
	
	/**
	 * get skipped combination of LIS
	 * 
	 * @param dpNum curr maden LIS's length
	 * @param dp2Ind iterating numLIS's index
	 * @param prev previously added element's index
	 * @param skip number of skip left
	 * @return skipped combination of LIS
	 */
	public static String KthCombination(int dpNum, int dp2ind, int prev, long skip) {
		if(dpNum == lenLIS + 1) {
			return "";
		}
		long currDP2_val = numLIS[dpNum].get(dp2ind).value;
		int currDP2_ind = numLIS[dpNum].get(dp2ind).index;
		// can't add curr element to maden LIS
		if(prev >= 0 && seq[currDP2_ind] <= seq[prev]) {
			return KthCombination(dpNum, dp2ind + 1, prev, skip);
		}
		
		if(skip > currDP2_val) {
			return KthCombination(dpNum, dp2ind + 1, prev, skip - currDP2_val);
		}
		return seq[currDP2_ind] + " " + KthCombination(dpNum + 1, 0, currDP2_ind, skip);
	}
	
	static class Pair {
		int index; // index of seq
		long value; // index's dp2 value
		public Pair(int i, long d) {
			index = i;
			value = d;
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
