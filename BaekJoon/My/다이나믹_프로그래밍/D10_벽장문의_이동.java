package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2666
 * **********
 * <해결방안>	: 
 * 
 * (문제 분석)
 * 1. 하나의 target으로 가려면 하나의 열려있는 문을 한 방향으로
 *    이동해야 한다.
 * 2. 그림1과 같이 두 열려있는 문이 한 문을 지나 건너가는 경우는 없다.
 * 3. 그림2와 같이 하나의 열려있는 문과 target이 배치될 수 있는 가지수는 3가지이며
 *    문이 이동할 수 있는 가지수는 4가지이다.
 *    
 * 재귀를 통해 해결했는데,
 * 우선 현재 열려있는 문이 곧 사용할 문이면 다음 target으로 넘어가고,
 * target이 가운데 있으면 두 개의 문을 각각 움직이는 경우를 해보아 min을 취하고
 * target이 한쪽에 치우쳐져 있으면 가까이 있는 문을 움직이는 경우를 생각해본다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 재귀를 돌 때 현재 열려진 벽장이 곧 사용할 벽장일 경우 처리를 하지 않음
 * 
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_벽장문의_이동 {

	static int num, useNum, useOrder[], open[];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		num = sc.nextInt();
		open = new int[2];
		for(int i = 0; i < 2; i++) {
			open[i] = sc.nextInt();
		}
		useNum = sc.nextInt();
		useOrder = new int[useNum];
		for(int i = 0; i < useNum; i++) {
			useOrder[i] = sc.nextInt();
		}

		System.out.println(func(0, open[0], open[1]));
	}
	
	/**
	 * 열려있는 벽장 문이 주어져있고,
	 * 현재 열고 싶은 벽장 문이 있을 때
	 * 문을 움직이는 최소값을 구함
	 * 항상 open1 <= open2임
	 * 
	 * @param targetIndex 현재 사용하고 싶은 벽장 (useOrder 배열의 index)
	 * @param open1 열려있는 벽장1
	 * @param open2 열려있는 벽장2
	 * @return
	 */
	public static int func(int targetIndex, int open1, int open2) {
		// 기저사례: 모든 벽장 사용
		if(targetIndex == useNum) {
			return 0;
		}
		// 사용할 벽장이 이미 열려있음
		int target = useOrder[targetIndex];
		if(target == open1 || target == open2) {
			return func(targetIndex + 1, open1, open2);
		}
		// 항상 open1 < open2
		if(open1 > open2) {
			int temp = open1;
			open1 = open2;
			open2 = temp;
		}
		
		int min;
		if(open1 < target && target < open2) {
			// o1 t o2
			min = Math.min(
					func(targetIndex + 1, target, open2) + target - open1,
					func(targetIndex + 1, open1, target) + open2 - target);
		} else if(target < open1) {
			// t o1 o2
			min = func(targetIndex + 1, target, open2) + open1 - target;
			
		} else {
			// o1 o2 t
			min = func(targetIndex + 1, open1, target) + target - open2;
		}
//		System.out.println(useOrder[targetIndex] + " " + open1 + " " + open2);
//		System.out.println(min);
		return min;
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
