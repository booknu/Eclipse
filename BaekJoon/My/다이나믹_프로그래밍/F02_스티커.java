package 다이나믹_프로그래밍;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/9465
 * **********
 * <해결방안>	: 
 * 
 * - N이 얼마가 되든 세로로는 2줄 밖에 안 된다는 것에서 착안
 * 
 * - 왼쪽부터 오른쪽으로 line 번호를 매긴다고 생각함
 * 
 * - line n 까지의 max는 line n-1 까지의 max 값에 현재 line에서 뗄 스티커의 점수를 합한 것
 *   <F(n) = F(n - 1) + 지금 뗄 스티커>
 *   
 * - 따라서 지금 뗄 스티커가 (위, 아래, 안뗌) 일 경우를 나눠서 생각해야함
 * 
 * - '위' 의 경우	: F위(n) = max(F아래(n - 1), F안뗌(n - 1)) + board(n, 위)
 *   '아래'의 경우	: F아래(n) = max(F위(n - 1), F안뗌(n - 1)) + board(n, 아래)
 *   '안뗌'의 경우	: F안뗌(n) = max(F위(n - 1), F아래(n - 1))
 *   ==> 이 경우에는 F안뗌(n - 1)을 생각할 필요가 없음
 *   ==> 이전에 아무것도 안 뗀 경우에는 위든 아래든 떼는게 이득이기 때문
 *   
 * - 마지막으로 max(F위(N), F아래(N))으로 최대값을 찾음
 *   ==> 이 경우 역시 F안뗌(N)을 생각할 필요가 없음
 *   ==> 마지막에는 무엇이든 떼는게 이득이기 때문
 * **********
 * <오답노트>	: 
 * 
 * 1. board 입력을 각 라인마다 위, 아래로 번갈아가며 받는 실수를 함 (입력 실수)
 * => 제대로 입력을 받게 수정함
 * 
 * 2. 일반 Scanner로 입력을 받아 느려짐 (시간 초과)
 * => BufferedReader를 사용하는 FastScanner를 사용하도록 수정
 * 
 * **********
 * @author 임 성 열
 *
 */
public class F02_스티커 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T, N, board[][], dp[][];
		T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {

			N = sc.nextInt();
			board = new int[N][2];
			dp = new int[N][3];
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < N; j++) {
					board[j][i] = sc.nextInt();
				}
			}


			dp[0][0] = board[0][0];
			dp[0][1] = board[0][1];
			dp[0][2] = 0;
			for(int i = 1; i < N; i++) {
				dp[i][0] = Math.max(dp[i - 1][1], dp[i - 1][2]) + board[i][0];
				dp[i][1] = Math.max(dp[i - 1][0], dp[i - 1][2]) + board[i][1];
				dp[i][2] = Math.max(dp[i - 1][0], dp[i - 1][1]);
			}


			int max = Math.max(dp[N - 1][0], dp[N - 1][1]);
			System.out.println(max);
		}
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
