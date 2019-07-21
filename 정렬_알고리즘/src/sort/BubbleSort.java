package sort;

/**
 * 버블 정렬
 * 인접한 두 원소를 바꿔가면서 정렬
 * O(n^2)
 * 
 * best case (이미 정렬되었을 때)
 * 비교: n번, 교환: 0번
 * 
 * worst case(거꾸로 정렬되었을 때)
 *           (맨 끝에 최소값이 있을 때 그 값이 처음으로 내려오려면
 *            n^2번 루프를 다 돌아야함)
 * 비교: n^2번, 교환: n^2번
 * 
 * @author LENOVO
 *
 */
public class BubbleSort {
	public static void sort(int[] arr)
	{
		for(int i = arr.length - 1; i > 0; i--) {
			boolean sorted = true;
			for(int j = 0; j < i; j++) {
				if(arr[j + 1] < arr[j]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
					sorted = false;
				}
			}
			if(sorted) break;
		}
	}
}
