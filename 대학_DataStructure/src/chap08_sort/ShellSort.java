package chap08_sort;

public class ShellSort {
	public static void sort(int[] arr) {
		shellSort(arr);
	}
	public static void shellSort(int[] arr) {
		int n = arr.length;
		int gap = n / 2;
		while(gap >= 1) {
			for(int start = 0; start < gap; start++) {
				// insertion sort
				for(int curr = start + gap; curr < n; curr += gap) {
					int target = arr[curr]; // insert target
					int check;
					for(check = curr - gap; check >= 0; check -= gap) {
						if(arr[check] > target) {
							// move backwards
							arr[check + gap] = arr[check];
						} else {
							// arr[0 ~ check] is sorted
							break;
						}
					}
					// insert target to proper index
					arr[check + gap] = target;
				}
			}
			
			gap /= 2;
		}
	}
}
