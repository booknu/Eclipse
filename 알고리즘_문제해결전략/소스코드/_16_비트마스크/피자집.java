package _16_비트마스크;

import java.util.Scanner;

/**
 * 비트마스크의 기본적인 이론 테스트용
 * @author Administrator
 *
 */
public class 피자집 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cs = new Scanner(System.in);
		final int KINDS = 20;
		int toppings = 0;
		
		boolean done = false;
		System.out.println("주문하실 토핑을 모두 입력해주세요 (0 ~ " + (KINDS - 1) + ") (종료: -1)");
		while(!done)
		{
			System.out.print(">> ");
			int sel = cs.nextInt();
			if(sel >= KINDS)
				System.out.println("잘못된 입력입니다.");
			else if(sel == -1)
				done = true;
			else
				toppings = add(toppings, sel);
		}
		
		System.out.println("\n<주문한 토핑들>");
		System.out.println("TOTAL: " + Integer.bitCount(toppings));
		System.out.println("FIRST: " + Integer.numberOfTrailingZeros(toppings));
		for(int i = 0; i < KINDS; i++)
			if(exist(toppings, i))
				System.out.print(i + " ");
		
		// 모든 부분 집합 순회
		System.out.println("\n\n<모든 부분 집합들>");
		for(int sub = toppings; sub != 0; sub = (toppings & (sub - 1)))
		{
			for(int i = KINDS - 1; i >= 0; i--)
				if(exist(sub, i))
					System.out.print(i + " ");
			System.out.println();
		}
		cs.close();
	}
	
	// 추가
	public static int add(int array, int value)
	{
		return array | (1 << value);
	}
	// 지우기
	public static int remove(int array, int value)
	{
		return array & ~(1 << value);
	}
	// value가 있으면 없애고, 없으면 추가
	public static int toggle(int array, int value)
	{
		return array ^ (1 << value);
	}
	// array & (1 << value) 의 결과는 0(false) 1 << value(true) 값 중 하나
	public static boolean exist(int array, int value)
	{
		return (array & (1 << value)) == (1 << value);
	}

	// arr1 ∪ arr2 (합집합)
	public static int union(int arr1, int arr2)
	{
		return arr1 | arr2;
	}
	// arr1 ∩ arr2 (교집합)
	public static int intersection(int arr1, int arr2)
	{
		return arr1 & arr2;
	}
	// arr1 - arr2 (차집합) (A ∩ ~B 이용)
	public static int differ(int arr1, int arr2)
	{
		return arr1 & (~ arr2);
	}
	// arr1, arr2 둘 중 하나에만 속한 원소 가져오기
	public static int getToggledSet(int arr1, int arr2)
	{
		return arr1 ^ arr2;
	}
	
	// 집합에 포함된 원소의 수 구하기
	// 자바 내부의 Integer.bitCount(array) 사용 가능
	public static int bitCount(int array)
	{
		// return Integer.bitCount(array);
		if(array == 0) return 0;
		return array % 2 + bitCount(array / 2);
	}
	
	// 최소 원소 찾기
	// Integer.numberOfTrailingZeros(array); 는 끝에 붙어있는 0 개수를 셈
	// CPU 명령으로 곧장 치환되 굉장히 빠름
	public static int getMinValue(int array)
	{
		return Integer.numberOfTrailingZeros(array);
	}
	
	// array 형태로 최소 원소를 찾음
	// 만약 3이 최소라면 0000 1000(2) 반환
	public static int getArrayFormOfMinValue(int array)
	{
		return array & -array;
	}
	
	// 최소 원소를 지움
	public static int removeMinValue(int array)
	{
		return array & (array - 1);
	}
}
