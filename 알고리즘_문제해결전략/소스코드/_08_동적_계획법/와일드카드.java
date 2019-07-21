package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		: p218
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
public class 와일드카드 {

	static String W, S;
	static int memo[][];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int C = sc.nextInt();
		for (int testCase = 0; testCase < C; testCase++) {
			String strW = sc.nextLine();
			char[] target = strW.toCharArray();
			int N = sc.nextInt();
			String[] strFiles = new String[N];
			char[][] files = new char[N][];
			for(int i = 0; i < N; i++) {
				strFiles[i] = sc.nextLine();
				files[i] = strFiles[i].toCharArray();
			}
			
			memo = new int[N + 1][N + 1];
			ArrayList<char[]> ans = new ArrayList<char[]>();
			
			for(int fileIndex = 0; fileIndex < N; fileIndex++) {
//				char[] file = files[fileIndex];
				// 내가 짠 알고리즘
//				if(match(W, file)) {
//					ans.add(file);
//				}
				
				W = strW;
				S = strFiles[fileIndex];
				
				if(exhaustiveSearch(strW, strFiles[fileIndex])) {
					System.out.println(strFiles[fileIndex]);
				}
			}
			
//			System.out.println("TestCase: " + (testCase + 1));
			
			// 내가 짠 알고리즘
//			for(char[] a : ans) {
//				System.out.println(a);
//			}
		}
	}
	/**
	 * 내가 짠 알고리즘
	 * 
	 * W와 S 각 자리당 검사를 하는데,
	 * dp[j] = file의 j문자열까지 매칭이 되는지 여부고,
	 * 이것은 dp[i][j]와 같은데, i까지의 W에 대해 j까지가 매칭이 되는지인데,
	 * 이것을 for문을 거꾸로 돌리면서 메모리 절약
	 * 
	 * i, j가 '?'거나 '같다' 이면 dp[j] = dp[j-1] (이전이 만들 수 있었던건지?)
	 * 
	 * i, j가 '*'이면
	 * dp[i-1][0~j]가 만들 수 있는 것인지에 따라 dp[i][j] 계산
	 * 
	 * @param W 와일드카드
	 * @param file 파일 이름
	 * @return 매치되는지?
	 */
	public static boolean match(char[] W, char[] file) {
		int WL = W.length;
		int FL = file.length; // file명 길이
		
		boolean[] dp = new boolean[FL + 1]; // file의 i까지 매칭이 되는지
		char currW = W[0];
		// dp 초기화
		if(currW == '?' || currW == file[0]) {
			dp[1] = true;
		} else if(currW == '*') {
			for(int i = 0; i <= FL; i++) {
				dp[i] = true;
			}
		}
//		print(dp, file);
		
		// 와일드카드 문자를 차례로 돌아봄
		for(int i = 1; i < WL; i++) {
			currW = W[i];
			// dp채우기
			for(int j = FL; j >= 1; j--) {
				if(currW == '?' || currW == file[j - 1]) {
					dp[j] = dp[j - 1];
				} else if(currW == '*') {
					for(int k = 0; k <= j; k++) {
						if(dp[k]) {
							dp[j] = true;
							break;
						}
					}
				} else {
					dp[j] = false;
				}
			}
			if(currW != '*') {
				dp[0] = false;
			}
//			System.out.println(currW);
//			print(dp, file);
		}
		return dp[FL];
	}
	
	/**
	 * 완전 탐색으로 구현
	 * 
	 * @param W
	 * @param S
	 * @return
	 */
	public static boolean exhaustiveSearch(String W, String S) {
		int pos = 0;
		// 한 문자씩 같은지 확인
		while((pos < S.length() && pos < W.length()) &&
				(W.charAt(pos) == '?' || W.charAt(pos) == S.charAt(pos))) {
			pos++;
		}

		// pos가 끝까지 온 경우
		// S에 남아있는 문자열이 있는지에 따라 반환
		if(pos == W.length()) {
			return pos == S.length();
		}
		// 현재 검사할 문자가 *인 경우
		// *에 글자 몇 개를 대응할지 재귀호출
		if(W.charAt(pos) == '*') {
			for(int skip = 0; pos + skip <= S.length(); skip++) {
				if(exhaustiveSearch(W.substring(pos + 1), S.substring(pos + skip))) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 메모이제이션 사용시
	 * 최대 O(n^3)
	 * 
	 * @param w 와일드카드의 w번째부터의 문자열
	 * @param s 파일 이름의 s번째부터의 문자열
	 * @return
	 */
	public static boolean recur(int w, int s) {
		if(memo[w][s] != 0) {
			return memo[w][s] == 1;
		}
		while((w < W.length() && s < S.length()) &&
				(W.charAt(w)) == '?' || W.charAt(w) == S.charAt(s)) {
			w++;
			s++;
		}
		if(w == W.length()) {
			memo[w][s] = (s == S.length())? 1 : 2;
			return memo[w][s] == 1;
		}
		if(W.charAt(w) == '*') {
			for(int skip = 0; s + skip <= S.length(); skip++) {
				if(recur(w + 1, s + skip)) {
					memo[w][s] = 1;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 좀 더 빠른 재귀
	 * while문으로 '?'와 '같은지'를 돌리던 것을 다시 재귀로 돌림으로서
	 * 메모이제이션을 좀 더 촘촘히 함
	 * 
	 * 또한 for문으로 돌리던 '*'처리를 재귀로 돌려서 메모이제이션을 촘촘히
	 * 
	 * ==> 재귀 안에 loop가 없으면 더 빨라짐.
	 * 
	 * O(n^2)
	 * 
	 * @param w 와일드카드의 w번째부터의 문자열
	 * @param s 파일 이름의 s번째부터의 문자열
	 * @return
	 */
	public static boolean fastRecur(int w, int s) {
		if(memo[w][s] != 0) {
			return memo[w][s] == 1;
		}
		if((w < W.length() && s < S.length()) &&
				(W.charAt(w)) == '?' || W.charAt(w) == S.charAt(s)) {
			memo[w][s] = fastRecur(w + 1, s + 1)? 1 : 2;
			return memo[w][s] == 1;
		}
		if(w == W.length()) {
			memo[w][s] = (s == S.length())? 1 : 2;
			return memo[w][s] == 1;
		}
		if(W.charAt(w) == '*') {
			if(fastRecur(w + 1, s) || (s < S.length() && fastRecur(w, s + 1))) {
				memo[w][s] = 1;
				return true;
			}
		}
		return false;
	}
	
	public static void print(boolean dp[], char[] str) {
		System.out.println(str);
		for(int i = 0; i < dp.length; i++) {
			System.out.println(dp[i]);
		}
		System.out.println();
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
