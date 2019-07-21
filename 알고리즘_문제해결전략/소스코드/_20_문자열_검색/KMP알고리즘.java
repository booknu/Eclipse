package _20_문자열_검색;

import java.util.ArrayList;
import java.util.Scanner;

public class KMP알고리즘 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cs = new Scanner(System.in);

		System.out.print("String: ");
		String H = cs.next();
		System.out.print("Search: ");
		String N = cs.next();
		
		ArrayList<Integer> searched = new ArrayList<Integer>();
		searched = kmpSearch(H, N);
		for(Integer i : searched)
			System.out.print(i + " ");
	}

	/**
	 * pi를 이용해 문자열 검색 시행
	 * @param H
	 * @param N
	 * @return 일치하는 문자열이 있는 시작 index
	 */
	public static ArrayList<Integer> kmpSearch(final String H, final String N)
	{
		int n = H.length(), m = N.length();
		ArrayList<Integer> ret = new ArrayList<Integer>();
		ArrayList<Integer> pi = getPartialMatchSimple(N);

		int begin = 0, matched = 0;	// 검색 시작 위치, 일치한 문자 개수
		while(begin <= n - m)
		{
			if(matched < m && H.charAt(begin + matched) == N.charAt(matched))	// 문자열 일치 검사중
			{
				matched++;	// 해당 자리의 문자가 같으면 matched ++
				if(matched == m) ret.add(begin);	// 답을 찾아서 넣음
			}
			else {	// 불일치 발생
				if(matched == 0)	// 일치된 문자가 하나도 없다면, 단순히 다음 위치에서 시작
					begin++;
				else
				{
					// 일치된 문자가 있으면 pi를 통해 다음 시작 위치를 알아냄
					begin += matched - pi.get(matched - 1);	// 다음 시작 위치
					matched = pi.get(matched - 1);	// 이전에 이미 일치한 문자열은 다시 검사할 필요X
				}
			}
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
				pi.add(begin + i, Math.max(pi.get(begin + i), i + 1));
			}
		
		return pi;
	}

}
