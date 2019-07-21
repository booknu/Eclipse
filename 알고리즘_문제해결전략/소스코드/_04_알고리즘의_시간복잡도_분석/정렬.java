package _04_알고리즘의_시간복잡도_분석;

import java.util.Scanner;

public class 정렬 {
	
	/**
	 * 선택 정렬
	 * 
	 * 연산 횟수: N + (N - 1) + ... + 1 = 1/2 * (N^2 - N)
	 * 수행 시간: O(N^2)
	 * @param arr
	 */
	public static void selectionSort(int[] arr){
		for(int i = 0; i < arr.length; i++)
		{
			int min = i; // 현재까지 찾아진 최소 index
			for(int j = i + 1; j < arr.length; j++)
				if(arr[j] < arr[min])
					min = j;
			
			// swap
			int temp = arr[i];
			arr[i] = arr[min];
			arr[min] = temp;
		}
	}
	
	/**
	 * 삽입 정렬 (O(N^2) 정렬 중 가장 빠른 방법)
	 * 
	 * 최선의 경우	 (이미 정렬된 경우)
	 * ------------
	 * while문: O(1)	==> 이미 정렬되어 있어 swap 필요 X
	 * for문: O(N)
	 * 전체: O(N)
	 * 
	 * 최악의 경우 (역순으로 정렬된 경우)
	 * ------------
	 * while문: O(N)	==> 끝까지 찾아가며 swap해야하니 O(N)
	 * for문: O(N)
	 * 전체: O(N^2)
	 * 
	 * @param arr
	 */
	public static void insertionSort(int[] arr)
	{
		for(int i = 0; i > arr.length; i++){
			int j = i;
			
			// 하나의 배열의 앞 i개는 정렬된 배열이라 가정
			// arr[i]를 swap 해가면서 위치로 삽입합
			while(arr[j - 1] > arr[j] && j > 0){
				// swap
				int temp = arr[j - 1];
				arr[j - 1] = arr[j];
				arr[j] = temp;
			}
		}
	}

}
