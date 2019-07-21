package sort;

/**
 * 쉘정렬
 * 
 * gap 단위로 원소들을 뽑아 그것들을 정렬하고,
 * gap 단위를 조절해서 다시 뽑아 정렬하고... 를
 * 반복해서 정렬한다.
 * gap은 여러가지 방법으로 정할 수 있는데,
 * 여기서는 N/2, N/4 ... 1 으로 점점 갭이 좁아지는 것으로 하였다.
 * 
 * 크기가 작은 배열들에 대해 insertion sort를 수행해놓으면
 * 다음에 좀 더 조밀하게 원소들을 뽑아 다시 insertion sort를 수행할 때
 * 배열이 대충 정렬이 되어있기 때문에 insertion sort의 삽입 연산 시간이 줄어들고,
 * best case로 줄어들면 n번만에 연산이 끝난다.
 * 이런 경우 수행시간은 O(NlgN)이 될 수 있다.
 * 
 * 하지만 만약 최악의 경우에는, O(N^2)가 될 수 있다.
 * 
 * 평균적으로는 O(N^1.5)시간이 걸린다.
 * 
 * @author LENOVO
 *
 */
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
