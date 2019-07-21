package sort;

import java.util.Arrays;

/**
 * 병합정렬
 * O(nlgn)
 * 
 * 나누는데는 적은 시간이 걸리지만 (새로운 배열을 만드는데 좀 걸림)
 * 합치는데는 O(n)만큼의 시간이 걸린다.
 * 재귀의 깊이는 O(lgn)이고
 * 따라서 O(n * lgn)이 된다.
 * 
 * 퀵 정렬보다 느리고 데이터에 비례하는 메모리가 필요하지만, 
 * stable sort
 * 
 * @author LENOVO
 *
 */
public class MergeSort {
	public static void sort(int[] arr) {
		mergeSort(arr);
	}
	
	public static void mergeSort(int[] arr) {
		int n = arr.length;
		if(n == 1) {
			return;
		}
		int mid = n / 2;
		int[] A = new int[mid];
		int[] B = new int[n - mid];
		A = Arrays.copyOfRange(arr, 0, mid);
		B = Arrays.copyOfRange(arr, mid, n);
		mergeSort(A);
		mergeSort(B);
		merge(A, B, arr);
	}
	
	public static void merge(int[] A, int[] B, int[] R) {
		int iA = 0;
		int iB = 0;
		int iR = 0;
		while(iA < A.length) {
			if(iB < B.length) {
				if(A[iA] < B[iB]) {
					R[iR] = A[iA];
					iA++;
				} else {
					R[iR] = B[iB];
					iB++;
				}
				iR++;
			} else {
				// A가 마저 안 채워졌으면 채움
				while(iA < A.length) {
					R[iR] = A[iA];
					iA++;
					iR++;
				}
			}
		}
		
		// B가 마저 안 채워졌으면 채움
		while(iB < B.length) {
			R[iR] = B[iB];
			iB++;
			iR++;
		}
	}
}
