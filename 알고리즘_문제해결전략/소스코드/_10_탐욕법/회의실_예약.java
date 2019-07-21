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
 * <주소>		: p366 회의실 예약
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 탐욕적 선택 조건 ]]
 *    end time이 가장 빠른 회의를 포함하는 최적해가 존재한다.
 *    
 *    최적해의 답에서 end time만 나열한 것이 1, 4 .... 이라면,
 *    end time = 2인 답도 최적해에 포함될 수 있겠지만, 그보다 짧은
 *    end time = 1인 답은 항상 최적해에 포함될 수 있다.
 *    
 *    따라서 남은 회의가 있을 때까지 end time이 가장 짧은 것을 고르고,
 *    그것보다 end time이 큰 회의들 중 shortest 회의와 겹치는 것을 지운다.
 *    (shortest end time보다 start time이 작은 것들을 지운다.)
 *    
 * 
 * 2. [[ 최적 부분 구조 ]]
 *    위 1번 방법을 계속 반복하다 보면 결국은 가장 많은 회의를 고를 수 있다.
 *    
 *    
 * 3. [[ 구현 ]]
 *    남은 회의를 지우는 것보다 미리 회의들을 end time을 기준으로 정렬을 해두면
 *    첫 번째 선택은 무조건 첫 번째 회의를 고르고, 그 다음부터 현재 선택된
 *    회의의 end time과 겹치지 않는 (start time이 end time보다 큰) 회의가 나오면
 *    그 회의를 선택해 추가하고, 이 과정을 반복한다.
 *    
 *    이미 end time은 정렬되어 있기 때문에 앞에서 가장 짧은 end time을 골랐다면
 *    다음에 고르는 겹치지 않는 것은 겹치지 않는 것들 중 가장 짧은 end time을 가지고
 *    있으므로 문제가 없다.
 *    
 * 4. [[ 시간복잡도 ]]
 *    O(lgN) ==> loop를 도는데는 O(N)이 걸리지만, 정렬하는데 O(lgN)이 걸림
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 회의실_예약 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt(); // number of conference
		Conference[] conf = new Conference[N];
		for(int i = 0; i < N; i++) {
			conf[i] = new Conference(sc.nextInt(), sc.nextInt());
		}
		
		// sort by shortest conference end time
		Arrays.sort(conf, new Comparator<Conference>() {
			@Override
			public int compare(Conference arg0, Conference arg1) {
				return arg0.end - arg1.end;
			}
		});
		
		int currEndTime = conf[0].end; // shortest conference always can be added
		int selected = 1;
		for(int i = 1; i < N; i++) {
			// can add this conference
			if(conf[i].start < currEndTime) {
				selected++;
				currEndTime = conf[i].end;
			}
		}
		System.out.println(selected);
	}
	
	static class Conference {
		int start, end;
		public Conference(int s, int e) {
			start = s;
			end = e;
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
