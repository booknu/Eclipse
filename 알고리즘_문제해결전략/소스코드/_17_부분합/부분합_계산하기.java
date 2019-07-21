package _17_부분합;

public class 부분합_계산하기 {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[] arr = {1, 1, 1, 1, 1};
		int[] sqarr = new int[arr.length];	// arr의 제곱 배열
		for(int i = 0; i < arr.length; i++)
			sqarr[i] = arr[i] * arr[i];
		int[] psum = partialSum(arr);	// 부분합 배열
		int[] sqpsum = partialSum(sqarr); // arr 제곱의 부분합 배열
		
		int from = 0; int to = 4;
		System.out.println("구간합 ( " + from + " ~ " + to + " ) = " + rangeSum(psum, from, to));
		
		from = 0; to = 4;
		System.out.println("분산 ( " + from + " ~ " + to + " ) = " + variance(sqpsum, psum, from, to));
	}
	
	/**
	 * 부분 합 배열을 구한다.
	 * @param arr 배열
	 * @return 부분 합 배열
	 */
	public static int[] partialSum(int[] arr)
	{
		int[] psum = new int[arr.length];
		
		psum[0] = arr[0];
		for(int i = 1; i < psum.length; i++)
			psum[i] = psum[i - 1] + arr[i];
		
		return psum;
	}
	
	/**
	 * arr의 a ~ b 까지의 방의 합을 구한다.
	 * @param arr 배열
	 * @param a 시작 index
	 * @param b 끝 index
	 * @return 합
	 */
	public static int rangeSum(int[] psum, int a, int b)
	{
		if(a == 0)
			return psum[b];
		return psum[b] - psum[a - 1];
	}

	/**
	 * arr의 a ~ b 까지의 방의 분산을 구한다.
	 * @param sqpsum 제곱의 부분 합 (A[i]^2 의 구간 합을 구하기 위함)
	 * @param psum 부분 합 (A[i] 의 구간 합을 구하기 위함)
	 * @param a 시작 index
	 * @param b 끝 index
	 * @return 분산
	 */
	public static double variance(int[] sqpsum, int[] psum, int a, int b)
	{
		double mean = rangeSum(psum, a, b) / (double)(b - a + 1);
		return (1.0 / (b - a + 1)) * (rangeSum(sqpsum, a, b) - 2 * mean * rangeSum(psum, a, b) + (b - a + 1) * mean * mean);
	}
}
