package sort;

/**
 * 선택정렬
 * O(n^2)
 * 
 * @author LENOVO
 *
 */
public class SelectionSort {
	public static void sort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			int min = i;
			for (int j = i; j < arr.length; j++)
				if (arr[j] < arr[min])
					min = j;
			int temp = arr[i];
			arr[i] = arr[min];
			arr[min] = temp;
		}
	}
}
