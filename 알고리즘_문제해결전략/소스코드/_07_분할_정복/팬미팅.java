package _07_분할_정복;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

/************
 * <주소>		: p201
 * **********
 * <해결방안>	: 
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 여성멤버는 아무에게나 포옹을 해주고,
 *    남성멤버일 경우에는 여자에게만 포옹해줌.
 *    나는 성별이 달라야 포옹해주는줄 알았음
 * 
 * **********
 * @author LENOVO
 *
 */
public class 팬미팅 {

	static char[] members, fans;
	static int M, N;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int C = sc.nextInt();
		for (int testCase = 0; testCase < C; testCase++) {
			members = sc.nextLine().toCharArray();
			fans = sc.nextLine().toCharArray();
			M = members.length;
			N = fans.length;
			
			System.out.println(mySolve(0, N - 1));
		}
	}
	
	/**
	 * 모든 가능성을 하나하나 시도해봄.
	 * N-M 가지의 방법을 시도하고
	 * 각 시도당 M번의 확인을 하니까
	 * O(M(N-M)) = O(NM - M^2)
	 * 
	 * @param members 멤버들 성별
	 * @param fans 팬들 성별
	 * @param M 멤버 수
	 * @param N 팬 수
	 * @return
	 */
	public static int bruteforce(char[] members, char[] fans, int M, int N) {
		int ret = 0;
		int bound = N - M;
		for(int left = 0; left <= bound; left++) {
			int right = left + M;
			boolean allDiff = true;
			for(int i = left; i < right; i++) {
				int memberIndex = i - left;
				if(members[memberIndex] == 'M' && fans[i] == 'M') {
					allDiff = false;
					break;
				}
			}
			if(allDiff) {
				ret++;	
			}
		}
		return ret;
	}
	
	/**
	 * 시간초과 뜸
	 * 
	 * 반반씩 나누어서 왼쪽 + 오른쪽
	 * + 걸쳐있는 것 검사
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public static int mySolve(int left, int right) {
		int size = right - left + 1;
		if(size < M) {
			return 0;
		}
		
		int mid = (left + right) / 2;
		int ret = mySolve(left, mid) + mySolve(mid + 1, right);
		
		int lo = mid;
		int hi = mid + M - 1;
		while(lo >= left && hi > mid) {
			if(hi <= right) {
				boolean allDiff = true;
				for(int i = lo, memberIndex = 0; i <= hi; i++, memberIndex++) {
					if(members[memberIndex] == 'M' && fans[i] == 'M') {
						allDiff = false;
						break;
					}
				}
				if(allDiff) {
					ret++;
				}
			}
			lo--;
			hi--;
		}
		return ret;
	}
	
	/**
	 * 곱셈의 원리를 이용해 구할 수 있다.
	 * 카라츠바의 곱셈 공식을 이용하면 O(n^lg3)
	 * 
	 * @param members
	 * @param fans
	 * @return
	 */
	public static int hugs(char[] members, char[] fans) {
		int M = members.length;
		int N = fans.length;
		Vector<Integer> A = new Vector<Integer>(N);
		Vector<Integer> B = new Vector<Integer>(M);
		for(int i = 0; i < M; i++) {
			A.add(i, members[i] == 'M' ? 1 : 0);
		}
		// Fans는 거꾸로 넣어서 계산
		for(int i = 0; i < N; i++) {
			B.add(M - i - 1, fans[i] == 'M' ? 1 : 0);
		}
		
		Vector<Integer> C = 카라츠바의_빠른_곱셈.karatsuba(A, B);
		int allHugs = 0;
		for(int i = N - 1; i < M; i++) {
			if(C.get(i) == 0) {
				allHugs++;
			}
		}
		return allHugs;
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
