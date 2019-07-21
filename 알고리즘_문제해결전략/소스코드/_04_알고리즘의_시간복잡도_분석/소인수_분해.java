package _04_알고리즘의_시간복잡도_분석;

import java.util.ArrayList;

public class 소인수_분해 {

	/**
	 * n을 소인수 분해한 결과를 반환
	 * 
	 * 수행 시간
	 * n이 소수일 때: n - 1
	 * ==> 입력은 1개인데 수행 시간은 달라짐 (비 직관적) (입력 memory와 관계가 X)
	 * 
	 * ==> 입력의 bit 수로 따지면 2^N 의 수행 시간 (직관적) (입력 memory와 관계가 O)
	 * 
	 * @param n 소인수 분해 대상
	 * @return 결과 (인수들)
	 */
	public static ArrayList<Integer> factor(int n){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		
		if(n == 1) return ret;	// n = 1이면 예외임
		
		for(int div = 2; n > 1; ++div){
			while(n % div == 0)
			{
				n /= div;
				ret.add(div);
			}
		}
		return ret;
	}
}
