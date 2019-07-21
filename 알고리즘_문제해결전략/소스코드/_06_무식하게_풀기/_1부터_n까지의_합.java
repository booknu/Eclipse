package _06_무식하게_풀기;
public class _1부터_n까지의_합 {
	
	static int iterativeSum(int n) {
		int sum = 0;
		for(int i = 1; i <= n; i++)
			sum += n;
		return sum;
	}
	
	static int recursiveSum(int n) {
		if(n == 1) return 1;
		return n + recursiveSum(n - 1);
	}

}
