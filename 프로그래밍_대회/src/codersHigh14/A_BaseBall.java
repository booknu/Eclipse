package codersHigh14;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class A_BaseBall {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner cs = new Scanner(System.in);
		
		int T = cs.nextInt();
		int Y, K;
		for(int testCase = 0; testCase < T; testCase++)
		{
			Y = 0;
			K = 0;
			for(int i = 0; i < 9; i++)
			{
				Y += cs.nextInt();
				K += cs.nextInt();
			}
			if(Y > K)
				System.out.println("Yonsei");
			else if(Y < K)
				System.out.println("Korea");
			else
				System.out.println("Draw");
		}
	}	
}
