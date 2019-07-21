package 다이나믹_프로그래밍;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/1495
 * **********
 * <해결방안>	: 
 * 
 * 재귀호출을 통해 해결.
 * 현재 볼륨에서 up, down이 가능하면 해본 뒤 그것들 중 최대값을 출력
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 해결 방안이 없으면 -1을 출력해야 하는데 안 함
 * 2. 메모이제이션을 안 해서 시간초과
 * **********
 * @author LENOVO
 *
 */
public class D02_기타리스트{

	static int INF = -10000000;

	static int N, S, M;
	static int vol[];
	static int memo[][];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		N = sc.nextInt();
		S = sc.nextInt();
		M = sc.nextInt();
		vol = new int[N];
		for(int i = 0; i < N; i++) {
			vol[i] = sc.nextInt();
		}
		memo = new int[N][M + 1];
		for(int[] arr : memo) {
			Arrays.fill(arr, -1);
		}
		
		int result = getMaxVol(0, S);
		System.out.println(result == INF? -1 : result);
	}
	
	public static int getMaxVol(int song, int currVol) {
		if(song >= N) {
			return currVol;
		}
		if(memo[song][currVol] != -1) {
			return memo[song][currVol];
		}
		
		int max = INF;
		int up = currVol + vol[song];
		int down = currVol - vol[song];
		if(up <= M) {
			max = Math.max(max, getMaxVol(song + 1, up));
		}
		if(down >= 0) {
			max = Math.max(max, getMaxVol(song + 1, down));
		}
		memo[song][currVol] = max;
		return max;
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
