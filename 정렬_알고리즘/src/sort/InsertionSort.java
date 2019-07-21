package sort;
/**
 * 삽입정렬
 * O(n^2)
 * 
 * <주의>
 * 1. arr[check + 1] = target을 for문 밖에서 실행하는 이유는
 *    else에서 실행하도록 하면 check가 -1일 때 target을 저장하지
 *    못하기 때문
 * 
 * best case(이미 정렬이 되었을 때)
 * 비교:n번  ==> shell sort에 이것 때문에 insertion sort 사용
 * 
 * worst case(거꾸로 정렬되었을 때)
 * 비교:n^2번
 * 
 * @author LENOVO
 *
 */
public class InsertionSort {
	public static void sort(int[] arr) {
		int n = arr.length;
		for(int i = 1; i < n; i++) {
			int target = arr[i];
			int check;
			for(check = i - 1; check >= 0; check--) {
				if(arr[check] > target) {
					arr[check + 1] = arr[check];
				} else {
					break;
				}
			}
			arr[check + 1] = target;
		}
	}
}
