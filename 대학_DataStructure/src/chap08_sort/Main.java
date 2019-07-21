package chap08_sort;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static int MAX_DIGIT; // fit the output digits
	public static int LINE_DATA = 10; // number of data output per line
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = makeArray();
		
		System.out.println("\n원본: ");
		printArray(arr);
		
		long start = System.currentTimeMillis();
		ShellSort.sort(arr);
		long end = System.currentTimeMillis();
		
		System.out.println("\n\n정렬: ");
		printArray(arr);
		
		System.out.println("\n\n실행 시간: " + (end - start) / 1000.0);
	}

	/**
	 * create random array
	 * 
	 * because of the process of identifying duplicate elements takes O(N^2)
	 * execution time, 
	 * size of array < 40000
	 * 
	 * and because we put non-overlapping elements into array
	 * size of array >= limit of data
	 * 
	 * @return
	 */
	public static int[] makeArray()
	{
		Scanner cs = new Scanner(System.in);
		Random ran = new Random();
		
		System.out.print("배열 크기: ");
		int n = cs.nextInt();
		System.out.print("데이터 크기 제한: ");
		int limit = cs.nextInt();
		
		int temp = limit;
		while(temp > 0) {
			temp /= 10;
			MAX_DIGIT++;
		}
		
		int arr[] = new int[n];
		HashSet<Integer> used = new HashSet<Integer>();
		for(int i = 0; i < n; i++) {
			boolean overlap = true;
			while(overlap) {
				arr[i] = ran.nextInt(limit);
				if(!used.contains(arr[i])) {
					overlap = false;
					used.add(arr[i]);
				}
			}
		}
		cs.close();
		return arr;
	}
	
	/**
	 * print array
	 * 
	 * @param arr
	 */
	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (i % LINE_DATA == 0)
				System.out.printf("\n");
			System.out.printf("%" + MAX_DIGIT + "d", arr[i]);
		}
	}
}
