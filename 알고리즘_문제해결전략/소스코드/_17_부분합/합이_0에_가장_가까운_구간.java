package _17_부분합;

public class 합이_0에_가장_가까운_구간 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] arr = { -14, 7, 2, 3, -8, 4, -6, 8, 9, 11 };
		int[] psum = partialSum(arr);
		
		int min = Math.abs(psum[0]);
		int minFrom = 0;
		int minTo = 0;
		
		for(int i = 0; i < psum.length; i++)
			for(int j = i; j < psum.length; j++) {
				int val = Math.abs(rangeSum(psum, i, j));
				if(val < min)
				{
					min = val;
					minFrom = i;
					minTo = j;
				}
			}
		
		System.out.println("합이 0에 가까운 구간: (" + minFrom + " ~ " + minTo + ") \n값: " + min);
	}
	
	public static int[] partialSum(int[] arr)
	{
		int[] psum = new int[arr.length];
		psum[0] = arr[0];
		
		for(int i = 1; i < psum.length; i++)
			psum[i] = psum[i - 1] + arr[i];
		return psum;
	}
	
	public static int rangeSum(int[] psum, int a, int b)
	{
		if(a == 0)
			return psum[b];
		return psum[b] - psum[a - 1];
	}

}
