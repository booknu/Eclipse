package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import setting.FilePath;

public class MakeTestData {

	/**
	 * 파일에 저장
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner cs = new Scanner(System.in);
		PrintWriter out = new PrintWriter(new File(FilePath.AUTO_GENERATED_ARRAY));
		Random ran = new Random();
		
		System.out.print("배열 크기: ");
		int n = cs.nextInt();
		
		int arr[] = new int[n];
		for(int i = 0; i < n; i++)
			arr[i] = ran.nextInt(100000);
		
		// 파일에 출력
		out.print(n);
		for(int i = 0; i < n; i++)
		{
			if(i % 10 == 0)
				out.println();
			out.print(arr[i] + " ");
		}	
		out.close();
		cs.close();
	}
	
	/**
	 * 배열 형태로 반환
	 * @return
	 */
	public static int[] makeArray()
	{
		Scanner cs = new Scanner(System.in);
		Random ran = new Random();
		
		System.out.print("배열 크기: ");
		int n = cs.nextInt();
		
		int arr[] = new int[n];
		for(int i = 0; i < n; i++)
			arr[i] = ran.nextInt(100000);
		cs.close();
		return arr;
	}

}
