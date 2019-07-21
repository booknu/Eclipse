package TEST;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DirFilesLineCounter {

	public static ArrayList<File> files = new ArrayList<File>();
	public static void main(String a[]) throws IOException
	{
		subDirList("D:/대학/jv/test/FoodExpensesManager_v1/src");
		Scanner cs = new Scanner(System.in);
		System.out.println("\n\n파일들의 줄 개수를 더합니다.");
		cs.next();
		cs.close();
		try {
			int line = getLineOfFiles(files);
			System.out.println("\n총 Line: " + line);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(File file : files)
		{
			System.out.println(file.getAbsolutePath());
			
		}
	}

	/**
	 * 서브디렉토리에 있는 파일까지 구함
	 * @param source
	 */
	public static void subDirList(String source){
		File dir = new File(source); 
		File[] fileList = dir.listFiles(); 
		try{
			for(int i = 0 ; i < fileList.length ; i++){
				File file = fileList[i]; 
				if(file.isFile()){
					// 파일이 있다면 파일 이름 출력
					System.out.println("\t 파일 이름 = " + file.getName());
					files.add(file);
				}else if(file.isDirectory()){
					System.out.println("디렉토리 이름 = " + file.getName());
					// 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
					subDirList(file.getCanonicalPath().toString()); 
				}
			}
		}catch(IOException e){

		}
	}

	/**
	 * 파일들의 줄 개수를 더해서 구함
	 * @param files
	 * @throws FileNotFoundException 
	 */
	public static int getLineOfFiles(ArrayList<File> files) throws FileNotFoundException
	{
		int line = 0;

		Scanner in;

		for(File file : files)
		{
			int file_line = 0;
			in = new Scanner(file);
			while(in.hasNextLine())
			{
				in.nextLine();
				file_line ++;
			}
			line += file_line;
			System.out.println(file.getName() + ":\t" + file_line + "줄");
		}

		return line;
	}
}
