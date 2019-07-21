package sort;

/**
 * 퀵정렬
 * O(nlgn)
 * 최악: O(n^2) (피벗을 고를 때마다 모든 원소를 움직여야 할 때)
 * 
 * 나누는데서 O(n)이 걸리는 연산을 통해 피벗을 기준으로 정렬하고,
 * 합치는데는 O(1)밖에 걸리지 않는다.
 * 재귀의 깊이는 O(lgn)이고
 * 따라서 O(n * lgn)이 된다.
 * 
 * http://yujuwon.tistory.com/entry/%ED%80%B5-%EC%A0%95%EB%A0%AC
 * 
 * @author LENOVO
 *
 */
public class QuickSort {

	public static void sort(int[] arr) {
		quickSort(arr, 0, arr.length - 1);
	}
	
	public static void quickSort(int[] arr, int left, int right) {
		int pivot = arr[left];
		int orgL = left;
		int orgR = right;
		// left와 right가 만날 때까지 반복
		while(left < right) {
			// 넘어가야할 원소를 만날 때까지 right를 이동시킴
			while((arr[right] >= pivot) && (left < right)) {
				right--;
			}
			// 왼쪽으로 넘어가야할 원소를 발견했을 때
			if(left != right) {
				arr[left] = arr[right];
			}
			// 넘어가야할 원소를 만날 때까지 left를 이동시킴
			while((arr[left] <= pivot) && (left < right)) {
				left++;
			}
			// 오른쪽으로 넘어가야할 원소를 발견했을 때
			if(left != right) {
				arr[right] = arr[left];
				right--;
			}
		}
		
		arr[left] = pivot;
		pivot = left;
		left = orgL;
		right = orgR;
		
		if(left < pivot) {
			quickSort(arr, left, pivot - 1);
		}
		if(right > pivot) {
			quickSort(arr, pivot + 1, right);
		}
	}
}
