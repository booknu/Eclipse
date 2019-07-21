package Free;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.crypto.Data;

/**
 * 사용법:
 * targetDirPath에 txt들을 넣고 분석
 * resultFilePath에 결과를 어느 파일에 출력할지 결정
 * keywords에 어떤 키워드로 분석할지 결정
 * sort에 어떤 키워드를 기준으로 할지 결정
 * print를 어떤 식으로 할 지 결정
 * 
 * @author Administrator
 *
 */
public class 단어_검색기 {

	public static final String targetDirPath = "D:\\다운로드\\Text\\TEST";
	public static final String resultFilePath = "D:\\다운로드\\Text\\output.txt";
	public static String[] keywords = {
			"Test1", "Test2", "Test3"
	};
	
	public static void main(String args[]) throws IOException {
		File targetDir = new File(targetDirPath);
		if(!targetDir.exists() || !targetDir.isDirectory()) {
			System.err.println("잘못된 대상 폴더입니다.");
			System.exit(0);
		}
		
		System.out.println("parse to File array....");
		String[] filesPath = targetDir.list();
		File[] files = new File[filesPath.length];
		for(int i = 0; i < filesPath.length; i++) {
			files[i] = new File(filesPath[i]);
		}
		System.out.println("parsing complete!!!!\n");
		
		int wordCount[][] = new int[files.length][keywords.length];
		StringBuilder[] sb = new StringBuilder[files.length];
		PrintWriter out = new PrintWriter(resultFilePath);
		for(int fileIndex = 0; fileIndex < files.length; fileIndex++) {
			System.out.println("parsing File: " + files[fileIndex]);
			sb[fileIndex] = new StringBuilder();
			BufferedReader sc = new BufferedReader(new FileReader(targetDirPath + "\\" + files[fileIndex].getName()));
			String line;
			int lineCount = 0;
			while((line = sc.readLine()) != null) {
				lineCount++;
				for(int keyIndex = 0; keyIndex < keywords.length; keyIndex++) {
					if(line.contains(keywords[keyIndex])) {
						wordCount[fileIndex][keyIndex]++;
						sb[fileIndex].append(keywords[keyIndex] + "\t" + line + "\n");
					}
				}
			}
			
			sc.close();
		}
		
		// sort 기준이 되는걸 정해야 함
		sort(wordCount, files, sb, 0, files.length - 1, 0);
		
		// 그냥 모두 출력
//		for(int fileIndex = files.length - 1; fileIndex >= 0; fileIndex--) {
//			int total = 0;
//			for(int i = 0; i < keywords.length; i++) {
//				total += wordCount[fileIndex][i];
//			}
//			out.println("<<" + files[fileIndex] + ">>");
//			for(int i = 0; i < keywords.length; i++) {
//				out.print(keywords[i] + ": " + wordCount[fileIndex][i] + "\t");
//			}
//			out.println("total: " + total);
//			out.println(sb[fileIndex]);
//		}
		
		// 키워드 중 하나라도 포함하지 않으면 출력X
		for(int fileIndex = files.length - 1; fileIndex >= 0; fileIndex--) {
			int total = 0;
			boolean existZero = false;
			for(int i = 0; i < keywords.length; i++) {
				if(wordCount[fileIndex][i] == 0) {
					existZero = true;
				}
				total += wordCount[fileIndex][i];
			}
			if(!existZero) {
				out.println("<<" + files[fileIndex] + ">>");
				for(int i = 0; i < keywords.length; i++) {
					out.print(keywords[i] + ": " + wordCount[fileIndex][i] + "\t");
				}
				out.println("total: " + total);
				out.println(sb[fileIndex]);
			}
		}
		
		out.close();
	}
	
	/**
	 * 퀵소트
	 * 
	 * @param data 정렬할 배열
	 * @param l 처음: 0
	 * @param r 처음: length - 1
	 * @param 기준이 되는 것 (배열 범위 벗어나는 경우에는 Total)
	 */
	public static void sort(int[][] data, File[] files, StringBuilder[] sb, int l, int r, int basedOn){
		int left = l;
		int right = r;
		int pivot = data[(l+r)/2][basedOn];

		do{
			while(data[left][basedOn] < pivot) left++;
			while(data[right][basedOn] > pivot) right--;
			if(left <= right){    
				int[] temp = data[left];
				data[left] = data[right];
				data[right] = temp;
				
				File temp2 = files[left];
				files[left] = files[right];
				files[right] = temp2;
				
				StringBuilder temp3 = sb[left];
				sb[left] = sb[right];
				sb[right] = temp3;
				
				left++;
				right--;
			}
		}while (left <= right);

		if(l < right) sort(data, files, sb, l, right, basedOn);
		if(r > left) sort(data, files, sb, left, r, basedOn);
	}
}
