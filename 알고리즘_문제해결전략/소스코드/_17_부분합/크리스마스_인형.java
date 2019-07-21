package _17_부분합;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class 크리스마스_인형 {

	public static int box_num, childs;
	public static int[] box;
	public static int[] psum;

	public static final int INF = 1000000000;

	public static void main(String args[]) throws FileNotFoundException
	{
		Scanner in = new Scanner(new File("rsc/17-부분합/크리스마스_인형.txt"));

		int testCase = in.nextInt();
		int[] box_num = new int[testCase], childs = new int[testCase];
		int[][] box = new int[testCase][];
		for(int i = 0; i < testCase; i++)
		{
			box_num[i] = in.nextInt();
			childs[i] = in.nextInt();
			box[i] = new int[box_num[i]];

			for(int j = 0; j < box_num[i]; j++)
				box[i][j] = in.nextInt();
		}
		for(int i = 0; i < testCase; i++)
		{
			initializeVariables(box_num[i], childs[i], box[i]);
			/* 내 방식
			psum = partialSum[크리스마스_인형.box];
			System.out.print(orderOnce() + " " + orderManyTimes(0));
			System.out.println();
			*/
		}
	}

	public static void initializeVariables(int box_num, int childs, int[] box)
	{
		크리스마스_인형.box_num = box_num;
		크리스마스_인형.childs = childs;
		크리스마스_인형.box = box;
	}

	/* 내 방식
	public static int[] partialSum(int[] arr)
	{
		int[] psum = new int[arr.length];
		psum[0] = arr[0];
		for(int i = 1; i < psum.length; i++)
			psum[i] = psum[i - 1] + arr[i];
		return psum;
	}

	public static int rangeSum(int[] psum, int a, int b)
	{
		if(a == 0)
			return psum[b];
		return psum[b] - psum[a - 1];
	}


	public static int orderOnce()
	{
		int num = 0;
		for(int i = 0; i < box_num; i++)
			for(int j = i; j < box_num; j++)
				if(rangeSum(psum, i, j) % childs == 0)
					num++;	// 만약 이 범위로 구매한 인형들이 정확히 아이들에게 나눠질 수 있으면 경우의 수에 추가
		return num % 20091101;
	}

	public static int orderManyTimes(int a)
	{
		int max = 0;
		for(int i = a; i < box_num; i++)
			for(int j = i; j < box_num; j++)
				if(rangeSum(psum, i, j) % childs == 0)
				{
					if(j == box_num)	// 맨 끝이라면
						max = Math.max(max, 1);
					else
						max = Math.max(max, orderManyTimes(j + 1) + 1);
				}

		if(max == 0)
			return 0;
		return max;

	}
	 */ // 내 방식대로

}
