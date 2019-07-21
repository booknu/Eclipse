import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/************
 * <주소>		: 예선 3번
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
public class 예선_3번 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt();
			int K = sc.nextInt();
			int W = sc.nextInt();
			Point trace[] = new Point[N];
			int left = Integer.MAX_VALUE;
			int right = Integer.MIN_VALUE;
			for(int i = 0; i < N; i++) {
				trace[i] = new Point(sc.nextInt(), sc.nextInt());
				left = Math.min(left, trace[i].x);
				right = Math.max(right, trace[i].x);
			}
			
			
			Point sortX[] = Arrays.copyOf(trace, trace.length);
			Point sortY[] = Arrays.copyOf(trace, trace.length);
			Arrays.sort(sortX, new XCompare());
			Arrays.sort(sortY, new YCompare());
			
			// 일단 양쪽 사이드 벽에 들어갈 수 있는 것들 중 top, bot을 찾아놓음
			int top = Integer.MIN_VALUE;
			int bot = Integer.MAX_VALUE;
			for(int i = 0; i < N; i++) {
				Point curr = trace[i];
				if(curr.distX(left) <= W || curr.distX(right) <= W) {
					top = Math.max(top, curr.y);
					bot = Math.min(bot, curr.y);
				}					
			}
			for(int i = 0; i < N; i++) {
				Point curr = trace[i];
				if(curr.distX(left) > W && curr.distX(right) > W) {
					if(curr.distY(top) > curr.distX(bot)) {
						if(curr.y > top) {
							
						}
					} else {
						
					}
				}
			}
		}
	}

	static class Point {
		public int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public long distX(int x) {
			return Math.abs((long)this.x - x);
		}
		public long distY(int y) {
			return Math.abs((long)this.y - y);
		}
	}
	
	static class XCompare implements Comparator<Point> {
		@Override
		public int compare(Point o1, Point o2) {
			// TODO Auto-generated method stub
			return o1.x - o2.x;
		}
	}
	
	static class YCompare implements Comparator<Point> {
		@Override
		public int compare(Point o1, Point o2) {
			// TODO Auto-generated method stub
			return o1.y - o2.y;
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
