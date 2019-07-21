package 다이나믹_프로그래밍;
/************
 * <주소>		: https://www.acmicpc.net/problem/1937
 * **********
 * <해결방안>	: 
 * 
 * 정렬을 해서 가장 대나무양이 많은 지점부터 시작해봄
 * 정렬된 시작지점을 max[] 라고 하면 (max[1]은 가장 많은 곳)
 * max[1]에서는 무조건 1 (dp[1] = 1)
 * max[n]에서는 주위에 max[1 ~ n-1]이 있으면 해당 지점들 중 max값을 찾음
 * ==> dp[n] = max( max[n]보다 큰 것의 dp ) + 1 
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 문제에서는 같은 양의 대나무가 없다고 나와 있었지만 사실은 Fake 였음
 * 
 * **********
 * @author LENOVO
 *
 */
public class C01_욕심쟁이_판다 {

	static int dy[] = {1, 0, -1, 0};
	static int dx[] = {0, 1, 0, -1};

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		
		int n = sc.nextInt();
		int wood[][] = new int[n][n];
		Data sorted[] = new Data[n * n];	// 정렬을 위해 존재
										// data의 크기에 의해 오름차순 정렬
										// 해당 data의 x, y값을 가지고 있음
		boolean same[] = new boolean[n*n];	// i와 i+1이 같은지?
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				wood[i][j] = sc.nextInt();
				sorted[i*n + j] = new Data(wood[i][j], i, j);
			}
		}

		sort(sorted, 0, sorted.length - 1);

		// 나중에 wood 값에 대한 data나 dp의 index를 선형 시간으로 찾기 위해
		// wood 값을 임의로 변경함.
		// 문제 조건에 각 지역마다 대나무량이 다르다고 했기 때문에 문제 없음
		for(int i = 0; i < sorted.length; i++) {
			if(i < sorted.length - 1 &&
					wood[sorted[i].y][sorted[i].x] == wood[sorted[i + 1].y][sorted[i + 1].x]) {
				same[i] = true;
			}
			wood[sorted[i].y][sorted[i].x] = i;
		}
		
		// dp[i]: i에서 시작했을 때 몇 일이나 살아남는지?
		int dp[] = new int[n * n];
		dp[sorted.length - 1] = 1;

		int max = 1;
		// i: data들 중 i번째로 작은 것
		for(int i = sorted.length - 2; i >= 0; i--) {
			int y = sorted[i].y;
			int x = sorted[i].x;
			// 갈 수 있는 곳을 가봄
			for(int direction = 0; direction < 4; direction++) {
				int ny = y + dy[direction];
				int nx = x + dx[direction];
				// 범위를 벗어났는지
				if(ny >= 0 && nx >= 0 && ny < n && nx < n) {
					// 다음 숲으로 갈 수 있으면
					if(wood[ny][nx] > wood[y][x]) {
						
						// 이렇게 index를 찾다가 시간초과
						// wood[y][x]의 값을 가진 dp의 index를 찾음
//						for(index = i + 1; index < data.length; index++) {
//							if(data[index].value == wood[ny][nx]) {
//								break;
//							}
//						}
						if(!isSame(same, i, wood[ny][nx]))
							dp[i] = Math.max(dp[i], dp[wood[ny][nx]]);
					}
				}
			}
			dp[i]++;
			max = Math.max(dp[i], max);
		}
		System.out.println(max);
	}

	/**
	 * 퀵소트
	 * 
	 * @param data 정렬할 배열
	 * @param l 처음: 0
	 * @param r 처음: length - 1
	 */
	public static void sort(Data[] data, int l, int r){
		int left = l;
		int right = r;
		int pivot = data[(l+r)/2].value;

		do{
			while(data[left].value < pivot) left++;
			while(data[right].value > pivot) right--;
			if(left <= right){    
				Data temp = data[left];
				data[left] = data[right];
				data[right] = temp;
				left++;
				right--;
			}
		}while (left <= right);

		if(l < right) sort(data, l, right);
		if(r > left) sort(data, left, r);
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isSame(boolean same[], int a, int b) {
		for(int i = a; i < b; i++) {
			if(!same[i]) return false;
		}
		return true;
	}
	
	static class Data {
		public int value;
		public int y;
		public int x;

		public Data(int value, int y, int x) {
			this.value = value;
			this.y = y;
			this.x = x;
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
