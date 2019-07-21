package _06_무식하게_풀기;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class 소풍 {
	
	static int M, N;
	static boolean friend[][];
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		
		for(int testCase = 1; testCase <= T; testCase++){
			N = sc.nextInt(); // 학생 수
			M = sc.nextInt(); // 짝의 수
			friend = new boolean[N][N];
			int a, b;
			for(int i = 0; i < M; i++) {
				a = sc.nextInt();
				b = sc.nextInt();
				friend[a][b] = true;
				friend[b][a] = true;
			}
			
			System.out.println(func(new boolean[N]));
		}
		
	}
	
	public static int func(boolean taken[]) {
		boolean finished = true;
		for(boolean isTaken : taken) if(!isTaken) finished = false;
		if(finished) return 1;
		
		int ret = 0;
		for(int i = 0; i < N; i++) {
			for(int j = i + 1; j < N; j++) {
				if(!taken[i] && !taken[j] && friend[i][j]) {
					taken[i] = taken[j] = true;
					ret += func(taken);
					taken[i] = taken[j] = false;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 중복 제거한 코드
	 * @param taken 현재까지 짝이 지어진 사람들 = 1 (bitmask)
	 * @return 가능한 pairing 경우의 수
	 */
	public static int countPairing2(int taken) {	
		int firstFree = -1;
		for(int i = 0; i < N; i++)
			if(!isTaken(taken, i)) {
				firstFree = i;
				break;
			}
		
		// 기저 사례: 모두가 짝을 찾았음
		if(firstFree == -1)
			return 1;
		
		// 이 학생과 짝지을 학생 결정
		int ret = 0;
		for(int i = firstFree + 1; i < N; i++)
			if(!isTaken(taken, i) && friend[firstFree][i]) {
				taken = setTaken(taken, firstFree);
				taken = setTaken(taken, i);
				ret += countPairing2(taken);
				taken = setFree(taken, firstFree);
				taken = setFree(taken, i);
			}
		return ret;
	}

	private static boolean isTaken(int bitmask, int index) {
		return (bitmask & (1 << index)) > 0;
	}
	
	private static int setTaken(int bitmask, int index) {
		return bitmask | (1 << index);
	}
	
	private static int setFree(int bitmask, int index) {
		return bitmask & ~(1 << index);
	}
}
