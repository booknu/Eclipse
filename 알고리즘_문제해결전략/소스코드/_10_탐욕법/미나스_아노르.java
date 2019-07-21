package _10_탐욕법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/************
 * <주소>		: p388 미나스 아노르
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 감시 범위 표현 ]]
 *    각 초소의 감시 범위를 0rad를 기준으로 감시가 시작되는 곳, 감시가 끝나는 곳으로
 *    표시한다.
 *    p391 참고
 * 
 * 
 * 2. [[ 0, 2PI의 원형 구간 처리 ]]
 *    2PI에서 각도가 조금만 커져 원의 처음으로 돌아오면
 *    각도가 0으로 되는 경우가 생긴다.
 *    따라서 0을 덮는 초소는 따로 처리를 해줘야한다.
 *    이것은 solveCircular 메소드를 통해서 해결하는데,
 *    0을 덮는 구간 하나를 선택한 후 그것을 제외하고
 *    나머지 부분을 덮는 최소 수를 구한 후
 *    그것에 0을 덮는 경우 하나를 더한 것들 중
 *    최소값을 찾으면 된다.
 *    
 * 
 * 3. [[ 0을 덮는 구간 제외 후 최소 경우 구하기 ]]
 *    0을 덮는 구간을 제외한 [begin, end]구간에서의
 *    최소 경우는 
 *    begin 이전에 시작하는 것들 중 end가 가장 큰 것을 찾으면 된다.
 *    이것을 반복하면 되는데,
 *    이것은 solveLinear 메소드를 통해 해결한다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 미나스_아노르 {

	static final double PI = Math.PI;
	static final int INF = 987654321;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt();
			Range[] ranges = new Range[N];
			for(int i = 0; i < N; i++) {
				double y = sc.nextDouble();
				double x = sc.nextDouble();
				double r = sc.nextDouble();
				ranges[i] = new Range(y, x, r);
			}
			
			Arrays.sort(ranges, new Comparator<Range>() {
				@Override
				public int compare(Range arg0, Range arg1) {
					if(arg0.s > arg1.s) {
						return 1;
					} else if(arg0.s < arg1.s) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			
			int solve = solveCircular(N, ranges);
			System.out.println(solve >= INF? "IMPOSSIBLE" : solve);
		}
	}
	
	public static int solveCircular(int N, Range[] ranges) {
		int ret = INF;
		// select section which covers 0
		for(int i = 0; i < N; i++) {
			if(ranges[i].s <= 0 || ranges[i].s >= 2*PI) {
				// get range of except section which covers 0
				double begin = ranges[i].e % (2*PI);
				double end = (ranges[i].s + 2*PI) % (2*PI);
				ret = Math.min(ret, 1 + solveLinear(begin, end, N, ranges));
			}
		}
		return ret;
	}
	
	/**
	 * get minimum number of cases to cover [begin, end] which except cover 0
	 * 
	 * @return
	 */
	public static int solveLinear(double begin, double end, int N, Range[] ranges) {
		int used = 0;
		int idx = 0;
		while(begin < end) {
			// find longest section which starts before "begin"
			double maxCover = -1;
			while(idx < N && ranges[idx].s <= begin) {
				maxCover = Math.max(maxCover, ranges[idx].e);
				idx++;
			}
			// can't find case to cover section
			if(maxCover <= begin) return INF;
			begin = maxCover;
			used++;
		}
		return used;
	}
	
	static class Range {
		double s, e;
		public Range(double y, double x, double r) {
			double loc = (Math.atan2(y, x) + 2*PI) % (2*PI);
			double range = 2.0 * Math.asin(r / 2.0 / 8.0);
			s = loc - range;
			e = loc + range;
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
