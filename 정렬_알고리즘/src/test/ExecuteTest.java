package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import setting.FilePath;
import sort.InsertionSort;
import sort.QuickSort;

/**
 * Sort가 제대로 실행되는지 확인
 * 
 * @author Administrator
 *
 */
public class ExecuteTest {

	public static void main(String[] args) throws IOException {
		int original[] = MakeTestData.makeArray(); // MakeTestData.makeArray()
		// 메소드를 사용해도 됨
		int answer[] = arrayCopy(original);
		int mySort[] = arrayCopy(original);
		System.out.println("------- default sort start... -------");
		defaultSort(answer);
		System.out.println("------- default sort complete! -------");
		System.out.println("------- my sort start... -------");
		double time = measureTime(mySort);
		System.out.println("------- my sort complete! -------");

		printByConsole(original, answer, mySort, time);
		printByFile(original, answer, mySort, time);
	}

	/**
	 * 실행 시간을 측정할 코드 (정렬 알고리즘) 여기만 바꾸면서 정렬 알고리즘을 테스트 하면 됨
	 */
	public static void code(int[] arr) {
		// 여기에 내 Sort 알고리즘을 사용하면 됨
		InsertionSort.sort(arr);
	}

	/**
	 * 파일에 저장된 대로 배열을 만든다.
	 * 
	 * @return 만들어진 배열
	 * @throws FileNotFoundException
	 */
	public static int[] readData() throws FileNotFoundException {
		Scanner in = new Scanner(new File(FilePath.AUTO_GENERATED_ARRAY));
		int n = in.nextInt();

		int arr[] = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = in.nextInt();
		in.close();
		return arr;
	}

	/**
	 * 테스트용 기본 정렬
	 * 
	 * @param arr 정렬할 배열
	 */
	public static void defaultSort(int[] arr) {
		QuickSort.sort(arr);
	}

	/**
	 * 정렬 알고리즘의 실행 시간을 측정함
	 * 
	 * @param arr 반드시 복사된 배열이어야함
	 * @return 실행 시간
	 */
	public static double measureTime(int[] arr) {
		long start = System.currentTimeMillis();
		code(arr);
		long end = System.currentTimeMillis();
		return 1.0 * (end - start) / 1000;
	}

	/**
	 * 배열 내용 자체를 복사함
	 * 
	 * @param arr
	 *            복사할 배열
	 * @return 복사된 배열
	 */
	public static int[] arrayCopy(int[] arr) {
		int ret[] = new int[arr.length];
		for (int i = 0; i < arr.length; i++)
			ret[i] = arr[i];
		return ret;
	}

	/**
	 * 두 배열이 같은지 알아냄
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static boolean isSameArray(int[] arr1, int[] arr2) {
		if (arr1.length != arr2.length)
			return false;
		for (int i = 0; i < arr1.length; i++)
			if (arr1[i] != arr2[i])
				return false;
		return true;
	}

	/**
	 * 배열에 대한 정보를 문자열로 반환
	 * 
	 * @param arr
	 *            배열
	 * @return 정보
	 */
	public static String arrayToString(int[] arr) {
		final int MAX_DIGIT = 7; // 자리수 맞추기
		final int LINE_DATA = 10; // 한 줄에 몇 개의 데이터를 표시할지?
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < arr.length; i++) {
			if (i % LINE_DATA == 0)
				sb.append("\n");
			sb.append(String.format("%" + MAX_DIGIT + "d", arr[i]));
		}
		return sb.toString();
	}

	/**
	 * 출력할 결과를 문자열로 반환
	 * 
	 * @param origin
	 *            정렬할 배열
	 * @param answer
	 *            제대로 정렬된 배열
	 * @param my
	 *            내 알고리즘으로 정렬한 배열
	 * @return 출력할 결과
	 */
	public static String resultToString(int[] origin, int[] answer, int[] my) {
		
		StringBuilder sb = new StringBuilder("");
		sb.append("원본 배열:\n");
		sb.append(arrayToString(origin));
		sb.append("\n\n");

		sb.append("정답 배열:\n");
		sb.append(arrayToString(answer));
		sb.append("\n\n");

		sb.append("제출 배열:\n");
		sb.append(arrayToString(my));
		sb.append("\n\n");

		String result = isSameArray(answer, my) ? "정답과 일치합니다.\n" : "잘못된 알고리즘 입니다.\n";
		sb.append("결과: " + result);

		return sb.toString();
	}

	/**
	 * 콘솔창에 출력한다. Data가 많으면 잘릴 수 있다.
	 * 
	 * @param origin
	 *            원본 배열
	 * @param answer
	 *            제대로 정렬된 배열
	 * @param my
	 *            내 알고리즘으로 정렬한 배열
	 * @throws IOException 
	 */
	public static void printByConsole(int[] origin, int[] answer, int[] my, double time) throws IOException {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		out.write(resultToString(origin, answer, my));
		out.newLine();
		out.write("실행 시간: " + time);
		out.newLine();
		out.close();
	}

	/**
	 * 파일을 통해 출력한다.
	 * 
	 * @param origin
	 *            원본 배열
	 * @param answer
	 *            제대로 정렬된 배열
	 * @param my
	 *            내 알고리즘으로 정렬한 배열
	 */
	public static void printByFile(int[] origin, int[] answer, int[] my, double time) {
		System.out.println("File path: " + FilePath.EXECUTE_RESULT);
		try {
			PrintWriter out = new PrintWriter(new File(FilePath.EXECUTE_RESULT));
			out.println(resultToString(origin, answer, my));
			out.println("실행 시간: " + time);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
