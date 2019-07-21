package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

/************
 * <주소>		: p244
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
public class Quantization_TreeMap_시간초과 {

	static int N, S;
	static TreeMap<Integer, Integer> numbers;
	static Integer[] keys;
	static int[] psum;
	static int[] sizePsum;
	
	static int INF = 123456789;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for (int testCsae = 0; testCsae < T; testCsae++) {
			N = sc.nextInt();
			S = sc.nextInt();
			// 정렬된 순서로 저장됨
			// 검색: lg(n) ==> 커봤자 10
			numbers = new TreeMap<Integer, Integer>();
			for(int i = 0; i < N; i++) {
				int input = sc.nextInt();
				if(numbers.containsKey(input)) {
					numbers.replace(input, numbers.get(input) + 1);
				} else {
					numbers.put(input, 1);
				}
			}
			keys = new Integer[numbers.size()];
			numbers.keySet().toArray(keys);
			N = keys.length;
			psum();
//			test();
			
			System.out.println(solve(0, S));
		}
	}
	
	public static int solve(int from, int parts) {
//		System.out.println(from);
		if(from == N) return 0;
		if(parts == 0) return INF;
		int ret = INF;
		for(int partSize = 1; from + partSize <= N; partSize++) {
			ret = Math.min(ret, getErrorSqSum(from, from + partSize - 1)
					+ solve(from + partSize, parts - 1));
		}
		return ret;
	}
	
	public static void test() {
		numbers.clear();
		numbers.put(1, 10);
		numbers.put(3, 3);
		
		keys = new Integer[numbers.size()];
		numbers.keySet().toArray(keys);
	
		psum();
		System.out.println(getErrorSqSum(0, 0));
	}
	
	/**
	 * numbers의 원소 중
	 * index a ~ b 사이의 원소들 중
	 * 오차가 가장 적게 생기는 값을 반환.
	 * 
	 * @param a 시작 index
	 * @param b 끝 index
	 * @return 오차가 가장 적은 값
	 */
	public static int minError(int a, int b) {
		int sum = sum(psum, a, b);
		int sizeSum = sum(sizePsum, a, b);
		double aver = 1.0 * sum / sizeSum;
		return (int)Math.round(aver);
	}
	
	/**
	 * a ~ b까지의 오차 제곱 합을 구함
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int getErrorSqSum(int a, int b) {
		int min = minError(a, b);
		int ret = 0;
		for(int i = a; i <= b; i++) {
			int temp = keys[i] - min;
			temp *= temp;
			ret += temp * numbers.get(keys[i]);
		}
		return ret;
	}
	
	/**
	 * a ~ b까지의 합을 구함
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int sum(int[] psum, int a, int b) {
		if(a == 0) {
			return psum[b];
		}
		return psum[b] - psum[a - 1];
	}
	
	/**
	 * 부분합을 구해놓음
	 */
	public static void psum() {
		psum = new int[numbers.size()];
		psum[0] = keys[0] * numbers.get(keys[0]);
		sizePsum = new int[numbers.size()];
		sizePsum[0] = numbers.get(keys[0]);
		for(int i = 1; i < psum.length; i++) {
			psum[i] = psum[i - 1] + keys[i] * numbers.get(keys[i]);
			sizePsum[i] = sizePsum[i - 1] + numbers.get(keys[i]);
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
