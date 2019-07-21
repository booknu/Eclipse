package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p359 지니어스
 * **********
 * <해결방안>	: 
 * 
 * 1. 
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. K, M을 혼동해서 사용하는 실수가 많았음
 * 
 * 2. dp[i][j] = j라는 곡이 정확히 i분에 재생 시작할 확률인데,
 *    답을 출력할 때 단순하게 dp[K][favorite]으로 출력하여
 *    정확히 K분에 시작하는 favoite만 계산하게 하는 실수를 하였다.
 *    따라서 favorite의 재생 길이 min에 따라서
 *    (K-min, K]의 범위의 확률 (favorite이라는 곡이 이 범위 안에서 시작할 확률)
 *    을 더하여 K분 30초째에 favorite이 재생되는 확률을 구하였다.
 *    
 *    ex) 10분 30초째에 재생길이 3분짜리 곡이 재생되고 있을 확률:
 *        정확히 10, 9, 8 분에 곡이 재생 시작할 확률을 더하면 됨
 * 
 * 3. 슬라이딩 윈도를 적용할 때 이전에 사용했던 메모리를 초기화하지 않았음.
 *    ==> 슬라이딩 윈도를 적용했더니 메모리 초과는 안 뜨지만 시간초과뜸
 *    
 * 4. 시간초과 뜸 (행렬제곱 사용해야함)
 * **********
 * @author LENOVO
 *
 */
public class 지니어스_행렬제곱_미사용 {

	public static final int SIZE = 5; // sliding window
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int C = sc.nextInt();
		for(int testCase = 0; testCase < C; testCase++) {
			int N = sc.nextInt(); // number of songs [1, 50]
			int K = sc.nextInt(); // how many minute have passed [1, million]
			int M = sc.nextInt(); // number of favorite songs [1, 10]
			int length[] = new int[N]; // each song's length [1, 4]
			double next[][] = new double[N][N]; // probability of song i's next is song j
			int favorite[] = new int[M]; // favorite song list
			for(int i = 0; i < N; i++) {
				length[i] = sc.nextInt();
			}
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					next[i][j] = sc.nextDouble();
				}
			}
			for(int i = 0; i < M; i++) {
				favorite[i] = sc.nextInt();
			}
			
			double dp[][] = new double[SIZE][N]; // probability of listening j at start time is i minute
			dp[0][0] = 1;
			for(int min = 1; min <= K; min++) {
				for(int song = 0; song < N; song++) {
					int curr = min % SIZE;
					int prev = (min - length[song]) % SIZE; // add "song" to dp[prev]
					dp[curr][song] = 0; // reset dp, because of using sliding window
					if(prev >= 0) {
						// check all of the song at "prev" min
						for(int prevSong = 0; prevSong < N; prevSong++) {
							dp[curr][song] += dp[prev][prevSong] * next[prevSong][song];
						}
					}
				}
			}
			
			// get total probability
			// is it linear? ( sum(K-length, K] )
			for(int i = 0; i < M; i++) {
				double sum = 0.0;
				for(int songLength = 0; songLength < length[favorite[i]]; songLength++) {
					if(K >= songLength) {
						sum += dp[(K - songLength) % SIZE][favorite[i]];
					}
				}
				System.out.print(sum + " ");
			}
			System.out.println();
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
