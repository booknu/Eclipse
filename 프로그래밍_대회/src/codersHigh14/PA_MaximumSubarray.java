package codersHigh14;
import java.util.Scanner;

public class PA_MaximumSubarray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner cs = new Scanner(System.in);
		
		int T = cs.nextInt();
		
		for(int testCase = 0; testCase < T; testCase++)
		{
			int N = cs.nextInt();
			int arr[] = new int[N];
			int max = - (int)Math.pow(2, 32);
			
			for(int i = 0; i < N; i++)
			{
				arr[i] = cs.nextInt();
				if(i > 0 && arr[i - 1] + arr[i] > arr[i])
					arr[i] = arr[i - 1] + arr[i];
				if(arr[i] > max) max = arr[i];
			}
			System.out.println(max);
		}
	}

}
