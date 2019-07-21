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
 * <주소>		: p376 도시락 데우기
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 개요 ]]
 *    스케줄링 하는 문제는 탐욕법을 쓰는 경우가 많다.
 *  
 *  
 * 2. [[ 아이디어 ]]
 *    시간이 많이 걸리는 도시락부터 먹는다.
 * 
 * 
 * 3. [[ 탐욕적 선택 속성 ]]
 *    - 만약 시간이 적은 도시락이 먼저 오는 것이 최적해라고 가정하면,
 *      i에 그 도시락이 있고 j에 시간이 더 걸리는 도시락이 있다고 할 때
 *      
 *      i를 먹는데 걸리는 시간 = sum(ready[0, i]) + eat[i]
 *      j를 먹는데 걸리는 시간 = sum(ready[0, i] + ready[i+1, j]) + eat[j]
 *      
 *      만약 두 개의 순서를 바꾼다고 했을 때
 *      j+1번째 도시락부터는 ready[0, j+1]은 변하지 않기 때문에 영향을 받지 않는다.
 *      
 *      또한 각 도시락을 먹는데 걸리는 시간을 다시 쓰면
 *      i를 먹는데 걸리는 시간 = sum(ready[0, j] + ready[j+1, i]) + eat[i]
 *      j를 먹는데 걸리는 시간 = sum(ready[0, j]) + eat[j]
 *      이렇게 되면 바뀌었을 때 i를 먹는 시간은 바뀌기 전 j를 먹는 시간보다 짧고,
 *      바뀌었을 때 j를 먹는 시간은 바뀌기 전 i를 먹는 시간보다 길지만,
 *      그 시간은 최소한 바뀌기 전 j를 먹는 시간보다 짧다.
 *      또한 바뀐 뒤 i를 먹는 시간도 바뀌기 전 j를 먹는 시간보다 짧기 때문에
 *      이것 역시 최적해가 될 수 있다.
 *      
 *  
 * 4. [[ 최적 부분 구조 ]]
 *    첫 번쨰 도시락을 정하고 난 뒤 나머지 도시락을 배치할 때
 *    남는 도시락에 대해서도 가장 늦게 다 먹는 시간을 최소화해서 손해볼 일은 없다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 처음에 시간이 가장 많이 걸리는 도시락부터 먹는다는 생각을 하긴 했는데
 *    10000 1  1  (ready)
 *    100   99 98 (eat)
 *    이 경우에 오류가 나는줄 알았는데 아니었음
 * 
 * **********
 * @author LENOVO
 *
 */
public class 도시락_데우기 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt();
			Lunch[] lunch = new Lunch[N];
			int totalReady = 0;
			for(int i = 0; i < N; i++) {
				lunch[i] = new Lunch();
				lunch[i].ready = sc.nextInt();
			}
			for(int i = 0; i < N; i++) {
				lunch[i].eat = sc.nextInt();
			}

			// eat most time-consuming lunch first
			// sort by descending order
			Arrays.sort(lunch, new Comparator<Lunch>() {
				@Override
				public int compare(Lunch o1, Lunch o2) {
					return o2.eat - o1.eat;
				}
			});
			
			int prevReady = 0; // before eating this lunch, how many time took to heat lunch
			int maxEat = 0; // max time-consuming heating + eating
			for(int i = 0; i < N; i++) {
				prevReady += lunch[i].ready;
				maxEat = Math.max(maxEat, lunch[i].eat + prevReady);
			}
			System.out.println(maxEat);
		}
	}
	
	static class Lunch {
		int ready, eat;
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
