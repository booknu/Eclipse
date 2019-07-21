package codersHigh14;
import java.util.Scanner;

/**
 * 그릴 자체를 뒤집는다고 생각
 * @author Administrator
 *
 */
public class F_MeatsOnTheGrill {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner cs = new Scanner(System.in);
		
		int testCase = cs.nextInt();
		for(int test = 0; test < testCase; test++)
		{
			int H = cs.nextInt();
			int W = cs.nextInt();
			cs.nextLine();
			for(int i = 0; i < H; i++)
			{
				String line = cs.nextLine();
				for(int j = W - 1; j >= 0; j--)
					System.out.print(line.charAt(j));
				System.out.println();
			}
				
		}
	}

}
