package _10_탐욕법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/************
 * <주소>		: p380 문자열 합치기
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 아이디어 ]]
 *    현재 합쳐야 될 문자열들 중 길이가 가장 짧은 것 2개를 합친다.
 *    그렇게 생긴 문자열을 합쳐야 될 문자열에 추가한 후 다시
 *    길이가 가장 짧은 것 2개를 합치는 과정을 반복한다.
 *    
 * 
 * 2. [[ 탐욕적 선택 속성 ]]
 *    가장 짧은 문자열 2개 a, b가 가장 먼저 합쳐지는게 아니라고 가정했을 때
 *    a, b를 먼저 합쳐도 결과가 같거나 더 이득인 것을 보이면 됨 (p384)
 *    
 *    
 * 3. [[ 최적 부분 구조 ]]
 *    
 *    
 * 
 * 4. [[ 구현 ]]
 *    남은 문자열 중 짧은 것 2개씩을 골라야하니까
 *    삽입, 삭제 연산이 O(lgN)인 heap으로 구현된 priority queue를 사용한다.
 *    이렇게 되면 O(NlgN)의 시간이 걸린다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 문자열_합치기 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt();
			PriorityQueue<Integer> words = new PriorityQueue<Integer>();
			for (int i = 0; i < N; i++) {
				words.offer(sc.nextInt());
			}
			
			int total = 0;
			while(words.size() != 1) {
				int combined = words.poll() + words.poll();
				total += combined;
				words.offer(combined);
			}
			System.out.println(total);
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
