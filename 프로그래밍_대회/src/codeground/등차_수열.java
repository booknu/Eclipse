package codeground;
import java.util.Scanner;

class 등차_수열 {
	public static void main(String args[]) throws Exception	{
		/* 아래 메소드 호출은 앞으로 표준입력(키보드) 대신 input.txt 파일로 부터 읽어오겠다는 의미의 코드입니다.
		   만약 본인의 PC 에서 테스트 할 때는, 입력값을 input.txt에 저장한 후 이 코드를 첫 부분에 사용하면,
		   표준입력 대신 input.txt 파일로 부터 입력값을 읽어 올 수 있습니다.
		   또한, 본인 PC에서 아래 메소드를 사용하지 않고 표준입력을 사용하여 테스트하셔도 무방합니다.
		   단, Codeground 시스템에서 "제출하기" 할 때에는 반드시 이 메소드를 지우거나 주석(//) 처리 하셔야 합니다. */
		//Scanner sc = new Scanner(new FileInputStream("input.txt"));
		Scanner cs = new Scanner(System.in);
		int T, M;
		long[] seq;
		int test_case;
		T = cs.nextInt();        
		for(test_case = 1; test_case <= T; test_case++) {
			
			M = cs.nextInt();
			seq = new long[M];
			for(int i = 0; i < M; i++)
				seq[i] = cs.nextLong();
			
			long d;
			long gcd = seq[1] - seq[0];
			for(int i = 2; i < M - 1; i++)
			{
				d = seq[i + 1] - seq[i];
				gcd = gcd(gcd, d);
				if(gcd < 0)	// 약수가 없음
					break;
			}
			
			long result = numberOfDivisor(gcd);
			// 이 부분에서 정답을 출력하십시오.
			System.out.println("Case #" + test_case);
			System.out.println(result);
		}
	}
	
	public static long gcd(long a, long b)
	{
		if(a == 0 && b == 0)
			return 0;
		else if(a == 0 || b == 0)
			return -1;
		long mod = a % b;
		while(mod > 0)
		{
			a = b;
			b = mod;
			mod = a % b;
		}
		return b;
	}
	
	public static long numberOfDivisor(long n){
		if(n < 0)
			return 0;
		if(n == 1 || n == 0)
			return 1;
		long result = 1;
		double sqrt = Math.sqrt(n);
		for(int i = 2; i < sqrt; i++)
		{
			if(n % i == 0)
				result++;
		}
		result = result * 2;
		if(sqrt == (int)sqrt)
			result++;
		return result;
	}
}
