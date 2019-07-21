package _04_알고리즘의_시간복잡도_분석;
public class 최대의_부분합 {
	
	static final int MIN = -999999;
	
	/**
	 * 무식한 방법
	 * 수행 시간: O(N^3)
	 * @param arr
	 * @return
	 */
	static int maxSum1(int[] arr){
		int ret = MIN;
		for(int i = 0; i < arr.length; i++) {
			for(int j = i; j < arr.length; j++) {
				int sum = 0;
				// i ~ j 까지의 부분합 구함
				for(int k = i; k <= j; k++)
					sum += arr[k];
				ret = Math.max(ret, sum);
			}
		}
		return ret;
	}

	/**
	 * 좀 더 나은 방법
	 * 수행 시간: O(N^2)
	 * @param arr
	 * @return
	 */
	static int maxSum2(int[] arr){
		int ret = MIN;
		for(int i = 0; i < arr.length; i++) {
			int sum = 0;
			
			// 합을 구하자마자 비교
			for(int j = i; j < arr.length; j++) {
				sum += arr[j];
				Math.max(ret, sum);
			}
		}
		return ret;
	}
	
	/**
	 * 빠른 방법
	 * 수행 시간: O(NlgN)
	 * 
	 * lo ~ hi를 반으로 쪼개 두 조각에 걸쳐 있는 최대합과
	 * 						   하나의 조각에만 있는 최대합을 찾아 (재귀로)
	 * 그 중 최대값을 반환하는 방법
	 * @param arr
	 * @return
	 */
	static int maxSum3(int[] arr, int lo, int hi) {
		// 기저 사례: 구간의 길이가 1인 경우
		if(lo == hi) return arr[lo];
		
		int mid = (lo + hi) / 2;
		int left = MIN, right = MIN, sum = 0;
		
		//**** 배열을 반으로 쪼개서 두 부분에 모두 걸쳐 있는 최대 합 구간을 찾는다. ****/
		// 왼쪽에서 (i ~ mid)
		for(int i = mid; i >= lo; i--) {
			sum += arr[i];
			left = Math.max(left, sum);
		}
		
		sum = 0;
		// 오른쪽에서 (mid ~ j)
		for(int j = mid + 1; j <= hi; j++) {
			sum += arr[j];
			right = Math.max(right, sum);
		}
		//********************************************/
		
		// 두 조각 중 하나에만 있는 경우를 찾음
		int single = Math.max(maxSum3(arr, lo, mid), maxSum3(arr, mid, hi));
		return Math.max(left + right, single);
	}
	
	/**
	 * 가장 빠른 방법
	 * 수행 시간: O(N)
	 * @param arr
	 * @return
	 */
	static int maxSum4(int[] arr) {
		int ret = MIN, psum = 0;
		for(int i = 0; i < arr.length; i++) {
			psum = Math.max(psum, 0) + arr[i];
			ret = Math.max(psum, ret);
		}
		return ret;
	}
}
