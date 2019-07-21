package _19_큐_스택_데크;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 조세푸스 문제를 Queue를 사용해 해결
 * @author Administrator
 *
 */
public class 조세푸스_문제_Queue {

	static int num;	// 총 사람 수
	static int cycle;	// 현재 자살한 사람 다음으로 자살할 사람의 번째
	static Queue<Integer> survivors;
	static Iterator<Integer> kill;
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner cs = new Scanner(new File("rsc/19-큐 스택 데크/조세푸스_문제.txt"));

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
			
			survivors = new LinkedList<Integer>();
			for(int j = 1; j <= num; j++)
				survivors.offer(j);
				
			while(num > 2)
			{
				survivors.remove();
				num--;
				
				for(int j = 0; j < cycle - 1; j++)
				{
					int temp = survivors.remove();
					survivors.offer(temp);
				}
					
			}
			
			System.out.println(survivors.remove() + " " + survivors.remove());
			
		}
	}

}
