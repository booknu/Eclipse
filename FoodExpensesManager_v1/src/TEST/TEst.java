package TEST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TEst {

	/*
	public static void main(String a[])
	{
		List<File> dirList = getDirFileList("D:/");	//	user에 있는 모든 파일 가져옴
		for(File file : dirList)
			System.out.println(file.getPath());
	}
	*/
	
	public static List<File> getDirFileList(String dirPath)
	 {
	  // 디렉토리 파일 리스트
	  List<File> dirFileList = null;
	  
	  // 파일 목록을 요청한 디렉토리를 가지고 파일 객체를 생성함
	  File dir = new File(dirPath);
	  
	  // 디렉토리가 존재한다면
	  if (dir.exists())
	  {
	   // 파일 목록을 구함
	   File[] files = dir.listFiles();
	   
	   // 파일 배열을 파일 리스트로 변화함 
	   dirFileList = Arrays.asList(files);
	  }
	  
	  return dirFileList;
	 }
	
	public static void fileCopy(String inFileName, String outFileName) {
		  try {
		   FileInputStream fis = new FileInputStream(inFileName);
		   FileOutputStream fos = new FileOutputStream(outFileName);
		   
		   int data = 0;
		   while((data=fis.read())!=-1) {
		    fos.write(data);
		   }
		   fis.close();
		   fos.close();
		   
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		 }
	
}
