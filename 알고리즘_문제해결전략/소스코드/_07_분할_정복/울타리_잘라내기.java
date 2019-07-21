package _07_분할_정복;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p195
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
public class 울타리_잘라내기 {

	static int N, height[];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int C = sc.nextInt();
		for(int testCase = 0; testCase < C; testCase++) {
			N = sc.nextInt();
			height = new int[N];
			for(int i = 0; i < N; i++) {
				height[i] = sc.nextInt();
			}
			System.out.println(solve(0, N-1));
		}
	}
	
	/**
	 * 답을 구하는 무식한 방법
	 * (l, r)을 가능한 경우를 모두 해보고
	 * 그 결과 중 가장 큰 것을 반환
	 * (l, r)의 각각의 경우는
	 * (r - l - 1) * minHeight(l ~ r)
	 * O(n^2)
	 * 
	 * @return
	 */
	public static int bruteForce() {
		int ret = 0;
		for(int left = 0; left < N; left++) {
			int minHeight = height[left];
			for(int right = left; right < N; right++) {
				minHeight = Math.min(minHeight, height[right]);
				ret = Math.max(ret, (right - left + 1) * minHeight);
			}
		}
		return ret;
	}
	
	/**
	 * 분할정복으로 해결
	 * O(nlgn)
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public static int solve(int left, int right) {
		if(left == right) {
			return height[left];
		}
		int mid = (left + right) / 2;
		// 가장 큰 직사각형이 오른쪽, 왼쪽에만 있을 경우
		int ret = Math.max(solve(left, mid), solve(mid + 1, right));
		// 잘린 변을 공유하는 두 판자
		// 가장 큰 직사각형이 오른쪽과 왼쪽에 걸쳐 있을 경우
		int lo = mid;
		int hi = mid + 1;
		int h = Math.min(height[lo], height[hi]);
		ret = Math.max(ret, h * 2);
		while(left < lo || hi < right) {
			// 오른쪽으로 확장할 수 있고 
			// 왼쪽으로는 더 이상 확장할 수 없거나 오른쪽 확장이 더 최대값인 경우
			if(hi < right && (lo == left || height[lo - 1] < height[hi + 1])) {
				hi++;
				h = Math.min(h, height[hi]);
			} else {
				// 오른쪽 확장이 안 되면 왼쪽 확장으로
				lo--;
				h = Math.min(h, height[lo]);
			}
			// 확장한 후 이게 최대인지를 확인
			ret = Math.max(ret, h * (hi - lo + 1));
		}
		return ret;
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
