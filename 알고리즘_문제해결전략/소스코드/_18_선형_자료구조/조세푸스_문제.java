package _18_선형_자료구조;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class 조세푸스_문제 {

	static int num;	// 총 사람 수
	static int cycle;	// 현재 자살한 사람 다음으로 자살할 사람의 번째
	static LinkedList<Integer> survivors;
	static Iterator<Integer> kill;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner cs = new Scanner(new File("rsc/18-선형 자료구조/조세푸스_문제.txt"));
		
		int testCase = cs.nextInt();
		
		int[] n_arr = new int[testCase];
		int[] cycle_arr = new int[testCase];
		for(int i = 0; i < testCase; i++)
		{
			n_arr[i] = cs.nextInt();
			cycle_arr[i] = cs.nextInt();
		}
		
		for(int i = 0; i < testCase; i++)
		{
			num = n_arr[i];
			cycle = cycle_arr[i];
			
			// 리스트 채움
			survivors = new LinkedList<Integer>();
			for(int j = 1; j <= num; j++)
				survivors.addLast(j);
			
			
			kill = survivors.iterator();	// 죽일 사람의 포인터
			kill.next(); // 첫 번째 죽일 사람 지정
			
			// 마지막 2명 남을 때까지 반복
			while(num > 2)
			{
				kill.remove();	// 일단 지정돼있는 사람 죽임
				num--;
				
				// 그 다음 죽일 사람을 정함
				for(int j = 0; j < cycle; j++)
				{
					if(!kill.hasNext())
						kill = survivors.iterator();
					
					kill.next();
				}
			}
			
			System.out.println(survivors.getFirst() + " " + survivors.getLast());	
		}
		
	}

}
