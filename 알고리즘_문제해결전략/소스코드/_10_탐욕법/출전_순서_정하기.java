package _10_탐욕법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/************
 * <주소>		: p371 출전 순서 정하기
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 아이디어 ]]
 *    - 현재 상대보다 높은 레이팅이 우리에게 없으면,
 *      가장 낮은 레이팅을 내보낸다.
 *    - 높은 레이팅이 있으면,
 *      그것들 중 가장 작은 레이팅을 내보낸다.
 * 
 * 
 * 2. [[ 탐욕적 선택 속성 ]]
 *    - 만약 현재 상대보다 높은 레이팅이 없을 때
 *      다른 레이팅을 아무리 내보내도 패배한다.
 *      이것은 가장 낮은 레이팅을 내보내도 마찬가지이므로
 *      최소한 다른 경우와 같거나, 아니면 더 많이 이길 수도 있다.
 *      따라서 이것은 최적해에 포함된다.
 *    
 *    - 만약 높은 레이팅이 있을 경우 그들 중 누가 나가도 이기겠지만,
 *      그들 중 가장 작은 사람이 나가서 이기면 최소한 다른 경우와 같은
 *      승수이거나 더 많은 승수를 가질 수도 있다.
 *      따라서 이것은 최적해에 포함된다.
 *      
 *    - 만약 높은 레이팅이 있을 경우 이 경기에 C가 나가서 지는 최적해가
 *      있다고 가정할 때, 그보다 큰 A가 나가서 이 경기를 이기면
 *      1승을 챙기게 되고, A대신 C가 들어간 경기가 지더라도 1승 1패이기 때문에
 *      최적해에 포함된다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. BST를 쓰면 key값보다 큰 첫 번째 원소를 찾는데 lgN의 시간이 걸리고
 *    원소를 지우는데 lgN의 시간이 걸리므로
 *    O(lgN)의 시간 밖에 안 걸린다.
 * 
 * **********
 * @author LENOVO
 *
 */
public class 출전_순서_정하기 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt();
			int[] rus = new int[N];
			ArrayList<Integer> kor = new ArrayList<Integer>();
			for(int i = 0; i < N; i++) {
				rus[i] = sc.nextInt();
			}
			for(int i = 0; i < N; i++) {
				kor.add(sc.nextInt());
			}
			
			kor.sort(new Comparator<Integer>(){
				@Override
				public int compare(Integer o1, Integer o2) {
					// TODO Auto-generated method stub
					return o1 - o2;
				}
			});
			
			int win = 0;
			// execute time is O(N^2)
			// because there is no BST in Collection Framework
			// if using BST, finding specified element in BST will take O(lgN)
			// so it should takes O(lgN)
			for(int i = 0; i < N; i++) {
				Integer higher = null;
				for(int j = 0; j < kor.size(); j++) {
					if(kor.get(j) >= rus[i]) {
						higher = kor.get(j);
						break;
					}
				}
				// there is no higher korean player than rus[i]
				if(higher == null) {
					kor.remove(0);
				} else {
					kor.remove(higher);
					win++;
				}
			}
			System.out.println(win);
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
