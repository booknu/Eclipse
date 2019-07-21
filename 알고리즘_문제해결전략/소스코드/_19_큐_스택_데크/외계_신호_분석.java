package _19_큐_스택_데크;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class 외계_신호_분석 {

	static int targetSum;
	static int signal;
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		Scanner cs = new Scanner(new File("rsc/19-큐 스택 데크/외계_신호_분석.txt"));
		
		int testCase = cs.nextInt();
		int[] targetSum_arr = new int[testCase];
		int[] signal = new int[testCase];
		for(int i = 0; i < testCase; i++)
		{
			targetSum_arr[i] = cs.nextInt();
			int signal_index = cs.nextInt();
			
		}
	}

}
