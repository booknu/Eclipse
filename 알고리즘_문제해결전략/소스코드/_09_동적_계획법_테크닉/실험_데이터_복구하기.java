package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

/************
 * <주소>		: p327 실험 데이터 복구하기
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 문제 조건 ]]
 *    - 연속해서 출현하는 a, b 문자열에 대해 a의 접미사와
 *      b의 접두사가 최대한 많이 겹치게 해야함
 *    - 한 문자열 조각이 다른 문자열 조각에 포함되는 경우 무시
 * 
 * 
 * 2. [[ substr 입력부 ]]
 *    - 입력받은 문자열을 그냥 substr에 넣는 것보다 
 *      if input 문자열이 이전에 저장된 문자열에 포함되면
 *        문자열을 추가하지 않고, 
 *      if input 문자열이 이전에 저장된 문자열을 포함하면
 *        해당 문자열을 substr에서 지운다.
 *      
 *      K개의 문자열에 대해 모든 조합을 만들어봐야 하기 때문에
 *      O(K!) 혹은 O(2^K)의 시간이 걸린다.
 *      이렇기 때문에 무조건 K의 숫자를 하나라도 줄이는게 시간절약에
 *      큰 도움이 된다.
 *      물론 최악의 경우에는 별로 도움이 되지는 않는다. (지울 문자열X 경우)
 *
 *
 *  3. [[ overlap[a][b] 구하기 ]]
 *     a의 접미사와 b의 접두사가 겹치는 최대 개수를 구해놓아야
 *     각 조합에 대해 max로 겹치는 경우를 찾기 쉬울 것이다.
 *     따라서 overlap[a][b]에 대해 b가 a의 substring으로 시작하는지 판단하는데,
 *     a의 substring은 [a전체, 0개 문자열]를 조사하여 가장 크게 겹치는 경우를 저장한다.
 *     
 *  4. [[ maxOverlap(int last, int remain) ]]
 *     이전에 추가한 문자열이 substr[last]이고, 남은 문자열을 bitmask로 표현한 것이 remain일 때
 *     이하 남은 문자열의 모든 조합을 수행해보고 그 중 최대로 겹치는 숫자를 반환한다.
 *     
 *     - remain = 0 이면 남은 문자열이 없으므로
 *       더이상 겹쳐질 문자열도 없기 때문에 0을 반환한다.
 *       
 *     - substr[0 ~ K] 까지 중 remain에 남아있는 문자열을 바로 다음 문자열로 시도해보는데,
 *       그 중 가장 크게 겹치는 경우를 반환한다.
 *     
 *  
 *  5. [[ reconstruct(int last, int remain) ]]
 *     이전에 추가한 문자열이 substr[last]이고, 남은 문자열을 bitmask로 표현한 것이 remain일 때
 *     남은 문자열을 조합해 만들 수 있는 문자열 중 가장 짧은 문자열을 반환한다.
 *     
 *     전체적인 구조는 maxOverlap과 비슷한데,
 *     다음 추가할 문자열을 substr[0 ~ K]중 골라 해당 문자열을 골랐을 때 overlap 되는 부분이
 *     정말 최대인지를 확인하여
 *     만약에 맞다면 그 문자열을 조합에 추가한다.
 *     
 *     - remain = 0 이면 남은 문자열이 없으므로
 *       더이상 추가할 문자열이 없기 때문에 "" 반환
 *       
 *     - substr[0 ~ K] 까지 중 remain에 남아있는 문자열을 바로 다음 문자열로 시도해보고
 *       그것이 정말로 overlap이 가장 많이 되는 경우이면
 *       그 문자열을 조합에 추가한다.
 *       ( overlap[last, next] + maxOverlap(next, nextRemain) == maxOverlap(last, remain) )
 *  
 *     
 *  6. [[ main에서의 함수 호출 ]]
 *     reconstruct는 무조건 이전에 추가한 문자열이 있어야 하므로
 *     last를 인자로 주어야 하는데,
 *     이것을 위해 main에서 각 substr[0 ~ K]를 시작으로 maxOverlap을 호출하여
 *     그 중 겹치는 부분이 최대가 되는 k값을 구하여 그것을 시작점으로
 *     reconstruct를 호출한다.
 *  
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 실험_데이터_복구하기 {

	public static int K; // number if substring ( K = [1, 15] )
	public static String[] substr; // substr
	public static int[][] overlap, memo;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			K = sc.nextInt();
			// remove string which contained by the other string
			ArrayList<String> inputSubStr = new ArrayList<String>(); // substring array ( maxLen = 20 )
			for(int i = 0; i < K; i++) {
				String input = sc.nextToken();
				if(i == 0) {
					inputSubStr.add(input);
					continue;
				}
				
				// if any string contains another string,
				// contained string must be removed
				Iterator<String> it = inputSubStr.iterator();
				boolean prevContains = false;
				while(it.hasNext()) {
					String prev = it.next();
					// if prev contains input
					if(prev.contains(input)) {
						prevContains = true;
					}
					// if input contains prev
					if(input.contains(prev)) {
						it.remove();
					}
				}
				// if input is not contained to any string
				// add to array
				if(!prevContains) {
					inputSubStr.add(input);
				}
			}
			substr = new String[inputSubStr.size()];
			inputSubStr.toArray(substr);
			K = substr.length;
			
			overlap = new int[K][K];
			// fill overlap that substr[i]'s next is substr[j]
			for(int i = 0; i < K; i++) {
				for(int j = 0; j < K; j++) {
					// if index is different
					if(i != j) {
						// from whole string of str[i] to none string of str[i]
						// if str[j] starts with substring of str[i]
						// it is max overlap
						int lenI = substr[i].length();
						for(int indexI = 0; indexI < lenI; indexI++) {
							if(substr[j].startsWith(substr[i].substring(indexI))) {
								overlap[i][j] = lenI - indexI;
								break;
							}
						}
					}
				}
			}
			
			memo = new int[K][(1 << K)];
			for(int i = 0; i < K; i++) {
				Arrays.fill(memo[i], -1);
			}
			
			// get max combination's first used string's index
			int max = 0;
			int maxIndex = 0;
			int mask = (1 << K) - 1;
			for(int i = 0; i < K; i++) {
				if(maxOverlap(i, setBit(i, false ,mask)) > max) {
					max = maxOverlap(i, setBit(i, false ,mask));
					maxIndex = i;
				}
			}
			
			System.out.println(substr[maxIndex] + reconstruct(maxIndex, setBit(maxIndex, false ,mask)));
		}
	}
	
	/**
	 * get max of overlapped
	 * 
	 * @param last last selected string index
	 * @param remain remaining indexes
	 * @return combination of least string length's overlapped value
	 */
	public static int maxOverlap(int last, int remain) {
		// nothing remaining
		if(remain == 0) {
			return 0;
		}
		if(memo[last][remain] != -1) {
			return memo[last][remain];
		}
		
		int ret = 0;
		// try all of the combination if "next" was not used
		for(int next = 0; next < K; next++) {
			if(getBit(next, remain)) {
				ret = Math.max(ret, maxOverlap(next, setBit(next, false, remain))
						+ overlap[last][next]);
			}
		}
		memo[last][remain] = ret;
		return ret;
	}

	/**
	 * return least length of string combination
	 * 
	 * @param last previously selected string index
	 * @param remain remaining indexes
	 * @return least length of string combination
	 */
	public static String reconstruct(int last, int remain) {
		if(remain == 0) {
			return "";
		}
		
		for(int next = 0; next < K; next++) {
			// next was already used
			if(!getBit(next, remain)) {
				continue;
			}
			// next was not used
			// using next, if it can make max overlapped, use it
			int overlapped = overlap[last][next];
			int ifUsed = maxOverlap(next, setBit(next, false, remain))
					+ overlapped;
			if(maxOverlap(last, remain) == ifUsed) {
				return (substr[next].substring(overlapped))
						+ reconstruct(next, setBit(next, false, remain));
			}
		}
		
		// must be returned in previous loop
		// if this sentence was executed, it means error
		return "****==ERROR==****";
	}
	
	/**
	 * set mask bit to value (true = 1, false = 0)
	 * 
	 * @param n n bit
	 * @param value true = 1, false = 0
	 * @param mask mask
	 * @return changed mask
	 */
	public static int setBit(int n, boolean value, int mask) {
		if(value) {
			// set 1
			return mask | (1 << n);
		} else {
			// set 0
			return mask & (~(1 << n));
		}
	}
	
	/**
	 * get mask bit
	 * 
	 * @param n n bit
	 * @param mask mask
	 * @return true / false
	 */
	public static boolean getBit(int n, int mask) {
		return (mask & (1 << n)) > 0;
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
