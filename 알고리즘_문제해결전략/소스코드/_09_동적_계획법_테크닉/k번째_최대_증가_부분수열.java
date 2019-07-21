package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

/************
 * <주소>		: p299 k번째 LIS
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 정의 ]]
 *    dp[i] = seq[i]를 마지막 원소로 하는 LIS의 길이
 * 
 *    dp2[i] = LIS[i] 뒤에 seq[i] 이후의 원소를 추가하여 만들 수 있는
 *             LIS(전체)의 가능한 조합 개수
 *    
 *    ==> LIS[i] = i를 포함하는 i까지 원소들을 활용하여
 *                 만들 수 있는 전체 LIS에 포함되는 LIS (길이 = dp[i])
 *                 
 *    numLIS[i] = LIS의 길이가 i인 것들의 ArrayList (dp2를 정리한 것임) (i = dp[n])
 *    ==> 각각 ArrayList들은 seq에 따라 정렬되어 있음
 *                 
 *                 
 * 2. [[ dp[i] 구하기 ]]
 *    dp[i]는 j < i일 때 dp[j]에 seq[i]를 추가하여 만드는 수열 중
 *    가장 긴 것을 선택하면 된다.
 *    
 *    dp[i] = max( dp[j] + 1 )    (j = [0, i), seq[j] < seq[i])
 *    
 *    
 * 3. [[ dp2[i] 구하기 ]]
 *    만들 수 있는 LIS의 길이 = lenLIS 라고 하면
 *    dp2[i]는 dp[i] = lenLIS인 경우 이미 완성된 수열이기 때문에 1가지 경우 밖에 없다.
 *    그것이 아닌 경우 dp2[i]는 j > i일 때 dp[i] + 1 = dp[j]이고, seq[j] > seq[i]인 경우
 *    (현재까지 완성된 LIS[i]에 seq[j]를 추가하여 LIS[j]를 만들 수 있다면)
 *    dp2[i] += dp2[j]를 하면 된다.
 *    
 *    dp2[i] = 1    (dp[i] = lenLIS)
 *    dp2[i] = sum( dp2[j] )    (j = (N, i), dp[i] + 1 = dp[j] && seq[j] > seq[i])
 *    
 *    
 * 4. [[ KthCombination 메소드 ]]
 *    말 그대로 LIS들을 사전 순으로 정렬했을 때 k번째 오는 LIS를 반환하는 메소드이다.
 *    
 *    dpNum:  numLIS의 index에 접근하기 위한 요소. = dp[i]
 *            이전에 dpNum - 1개의 LIS 수열을 구성했다고 볼 수 있다.
 *    
 *    dp2ind: numLIS[i]의 리스트를 순회하기 위한 요소.
 *            numLIS[i]는 원소의 크기에 따라 정렬되어 있기 때문에
 *            이전 원소를 수열에 추가하지 않고 skip 했다면
 *            다음 원소에 대해서도 확인해야 하기 때문에
 *            현재 길이 i의 LIS에 대해 현재 확인중인 원소의 index를 저장할 필요가 있음 
 *   
 *    prev:   방금 전에 수열에 추가한 seq[i]의 인덱스 
 *            다음에 추가할 원소를 LIS에 추가할 수 있는지 확인용
 *   
 *    skip:   skip 해야 할 LIS 조합들의 수
 *    
 *    - 기저사례: dpNum == lenLIS + 1: 현재까지 만들어진 길이=lenLIS 이므로 더 이상 재귀호출할 필요가 없음
 *                 따라서 그냥 ""를 return
 *    - 현재 원소를 만들고 있는 LIS에 추가할 수 없을 때: seq[curr] < seq[prev_added]
 *                 길이 dpNum에 대한 dp2를 저장하고 있는 numLIS[dpNum]의 현재 원소는 dp2ind 이므로
 *                 dp2ind + 1을 한 후 재귀호출 (같은 길이의 LIS를 만들 수 있는 다음 원소를 확인)
 *    - 현재 원소를 skip할 수 있을 때: skip > currDP2_val:
 *                 dp2ind + 1을 한 후 skip에서 skip한 조합 수 만큼 뺀 재귀호출
 *                 (다음 원소를 확인하되, 현재 원소는 skip 했으므로 skip한 만큼 뺌)
 *    - 현재 원소를 skip할 수 없다면: default
 *                 현재 원소를 만들고 있는 LIS에 추가한 후 그 이후의 조합은
 *                 dpNum + 1, dp2ind = 0으로 만든 재귀호출을 추가.
 *                 (현재까지 만들어진 길이 = dpNum + 1인 LIS에 skip한 만큼의 조합)
 *           
 *                
 * 5. [[ dp[i], dp2[i], numLIS의 관계 ]]
 *    dp[i]와 dp2[i]는 서로 연결되어 있음.
 *    (길이)  (개수)
 *    dp[i]개만큼 만들어진 LIS에 어떤 원소들의 조합을 추가해 LIS를 만들 수 있는데,
 *    그 가능한 조합수가 dp2[i]임
 *    따라서
 *    numLIS[1]: dp[i] = 1 (현재까지 만들어진 길이=1)에 어떤 원소들의 조합을 추가해 만들 수 있는 LIS 개수
 *               그런데 dp[i] = 1인 원소들이 많을 수 있으니까 그것들을 List 형태로 관리하되,
 *               조합을 할 때 원소의 크기가 작은 것 부터 확인을 해야하니까 정렬을 해둠
 *    
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 예제 입력 2번을 길이 500까지 쭉 이어가면(오버플로 테스트 클래스처럼)
 *    가능한 LIS의 개수가 2^(N / 2)개가 되므로 오버플로가 생기게 된다.
 *    따라서 K의 최대 범위인 Integer.MAX_VALUE가 넘어가는 dp2[i]가 나오게 되면
 *    dp2[i] = Integer.MAX_VALUE + 1 과 같이 처리하여 나중의 KthCombination 메소드에서
 *    어차피 seq[i]는 수열에 포함시켜야 하는 것으로(경우의 수가 많으니 skip 할 수 없는 것으로)
 *    처리하게 된다.
 * 
 * **********
 * @author LENOVO
 *
 */
public class k번째_최대_증가_부분수열 {

	public static int seq[];
	public static int lenLIS;
	public static int dp[];
	public static ArrayList<Pair> numLIS[];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt(); // number of sequence
			int K = sc.nextInt(); // Kth LIS
			seq = new int[N]; // sequence
			for(int i = 0; i < N; i++) {
				seq[i] = sc.nextInt();
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
			
//			System.out.println("<< dp >>");
//			for(int i = 0; i < N; i++) {
//				System.out.print(dp[i] + " ");
//			}
//			System.out.println("\n" + lenLIS);
			
			/* count number of LIS ********************/
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
						// number of combination excesses K, it could be overflowed.
						// so regard very big number of combination to (limit of K + 1)
						if(dp2[i] > Integer.MAX_VALUE) {
							dp2[i] = (long)Integer.MAX_VALUE + 1;
						}
					}
				}
			}
//			System.out.println("<< dp2 >>");
//			for(int i = 0; i < N; i++) {
//				System.out.print(dp2[i] + " ");
//			}
//			System.out.println("\n------------");
			
			numLIS = new ArrayList[lenLIS + 1]; // dp2 was separated by dp[LIS len]
												// and each of numLIS[LIS len] was sorted by seq[index]
			for(int i = 0; i < N; i++) {
				if(numLIS[dp[i]] == null) {
					numLIS[dp[i]] = new ArrayList<Pair>();
				}
				numLIS[dp[i]].add(new Pair(i, dp2[i]));
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
		// base case: already made length = lenLIS of LIS
		if(dpNum == lenLIS + 1) {
			return "";
		}
		long currDP2_val = numLIS[dpNum].get(dp2ind).value;
		int currDP2_ind = numLIS[dpNum].get(dp2ind).index;
		// can't add curr element to maden LIS (seq[curr] < seq[prev_added])
		if(prev >= 0 && seq[currDP2_ind] <= seq[prev]) {
			// just check next element
			return KthCombination(dpNum, dp2ind + 1, prev, skip);
		}
		
		// can skip this element (number of (LIS[i] + nextComb) is less than skip) 
		if(skip > currDP2_val) {
			// skip this element and reduce "skip" by skipped number of combination
			return KthCombination(dpNum, dp2ind + 1, prev, skip - currDP2_val);
		}
		// can't skip this element
		// add this element to LIS and check next (length - 1) combination
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
