package _07_분할_정복;


/************
 * <주소>		: p176
 * **********
 * <해결방안>	: 
 * 
 * sum(1~N)을 분할 정복으로 해결하는 법
 * 
 * F(N)을 1~N까지의 합이라고 가정할 때
 * 
 * F(N) = F(N/2) + sum( (N/2 + 1) ~ N )
 * 
 * 여기서 sum( (N/2 + 1) ~ N )
 * = (N/2 + 1) + (N/2 + 2) + ... + (N/2 + N/2)
 * = (N/2 * N/2) + F(N/2)
 * 
 * 따라서
 * F(N) = F(N/2) + F(N/2) + (N/2 * N/2)
 *      = 2*F(N/2) + (N^2 / 4)
 *      
 * 많아야 두 번에 한 번 꼴로 절반으로 줄어드니까 
 * 실행시간 = O(lgN)
 * 
 * 일반 sum은 O(N)
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 수열의_빠른_합 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		long sum;
		long to = 1000000000;
		
		long start = System.currentTimeMillis();
		System.out.println("start...");
		sum = fastSum(to);
		long end = System.currentTimeMillis();
		System.out.println(sum);
		System.out.println("fast sum: " + (end - start));
		
		start = System.currentTimeMillis();
		System.out.println("start...");
		sum = 0;
		for(int i = 1; i <= to; i++)
			sum += i;
		end = System.currentTimeMillis();
		System.out.println(sum);
		System.out.println("normal sum: " + (end - start));
		
	}

	/**
	 * 1부터 n까지의 합을 반환한다.
	 * 필수조건: n은 자연수
	 * @param n
	 * @return 1~n 까지의 합
	 */
	public static long fastSum(long n) {
		if(n == 1) return 1;
		// 홀수일 때 
		if((n & 1) == 1) return fastSum(n-1) + n;
		// 짝수일 때
		return 2*fastSum(n/2) + (n/2)*(n/2);
	}
	
}
