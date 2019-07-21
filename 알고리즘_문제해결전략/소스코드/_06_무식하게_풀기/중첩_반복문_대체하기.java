package _06_무식하게_풀기;
import java.util.ArrayList;

public class 중첩_반복문_대체하기 {
	
	public static void main(String[] a) {
		System.out.println("----iter----");
		iterativePick(5);
		System.out.println("\n----recur----");
		recursivePick(5, new ArrayList<Integer>(), 4);
	}
	
	/**
	 * n개 중 4개를 고르는 알고리즘
	 * 4개라는 숫자는 바꿀 수 없음 (for문이 더 늘어나야 함)
	 * 제한: n >= 4
	 */
	static void iterativePick(int n) {
		for(int i = 0; i < n; i++)
			for(int j = i + 1; j < n; j++)
				for(int k = j + 1; k < n; k++)
					for(int l = k + 1; l < n; l++)
						System.out.println(i + " " + j + " " + k + " " + l);
	}
	
	/**
	 * n개 중 toPick개를 고르는 알고리즘
	 * toPick은 변경 가능
	 * @param n	몇 개중
	 * @param picked 이미 고른 리스트
	 * @param toPick 몇 개를 고를지
	 */
	static void recursivePick(int n, ArrayList<Integer> picked, int toPick) {
		// 기저 사례
		if(toPick == 0)
		{
			for(Integer i : picked)
				System.out.print(i + " ");
			System.out.println();
			return;
		}
			
		int smallest = picked.isEmpty()? 0 : picked.get(picked.size() - 1) + 1;
		for(int next = smallest; next < n; next++) {
			picked.add(next);
			recursivePick(n, picked, toPick - 1);
			picked.remove(picked.size() - 1);
		}
	}
}
