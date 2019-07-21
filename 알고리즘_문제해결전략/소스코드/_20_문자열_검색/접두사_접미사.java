package _20_문자열_검색;

import java.util.ArrayList;
import java.util.Scanner;

public class 접두사_접미사 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cs = new Scanner(System.in);
		String str = cs.next();
		ArrayList<Integer> result = getPrefixSuffix(str);
		
		for(Integer i : result)
			System.out.print(i + " ");
	}
	
	public static ArrayList<Integer> getPrefixSuffix(final String s)
	{
		ArrayList<Integer> ret = new ArrayList<Integer>(), pi = getPartialMatchSimple(s);
		int k = s.length();
		while(k > 0){
			ret.add(k);
			k = pi.get(k - 1);
		}
		return ret;
	}

	/**
	 * 가장 단순한 방법의 pi[i]를 찾는 방법
	 * (부분 일치 문자열에 대한 다음 시작 위치 찾기) ==> 전처리 과정
	 * @param N 찾으려는 문자열 (분석의 대상)
	 * @return
	 */
	public static ArrayList<Integer> getPartialMatchSimple(final String N)
	{
		int m = N.length();
		ArrayList<Integer> pi = new ArrayList<Integer>();
		for(int i = 0; i < m; i++)	// ArrayList 초기화
			pi.add(0);
		
		
		for(int begin = 1; begin < m; ++begin) // begin: 1 ~ MAX
			for(int i = 0; i < m - begin; ++i) // i:	 0 ~ (MAX - begin)
			{
				if(N.charAt(begin + i) != N.charAt(i)) break;
				pi.set(begin + i, Math.max(pi.get(begin + i), i + 1));
			}
		
		return pi;
	}
}
