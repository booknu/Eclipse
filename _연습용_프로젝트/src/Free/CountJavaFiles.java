package Free;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemNotFoundException;

public class CountJavaFiles {

	static String dirPath = "D:\\대학\\Java\\MainProject";
//	static String dirPath = "D:\\대학\\Java\\MainProject\\TumblrTest";
	public static void main(String[] args) throws Exception {
		// TODO Auto-gSenerated method stub
		File target = new File(dirPath);
		if(!target.exists()) {
			throw new FileNotFoundException("\n<ERROR: target directory was not found>");
		}
		if(!target.isDirectory()) {
			throw new FileSystemNotFoundException("\n<ERROR: target directory is not a directory>");
		}
		
		int countJava = allJavaFiles(target);
		System.out.println("===========================");
		System.out.println("Java Files: " + countJava);
	}
	
	/**
	 * print all of the file / directories inside currDir
	 * return how many java file is exists
	 * 
	 * @param currDir current directory
	 * @return java files' number
	 */
	public static int allJavaFiles(File currDir) {
		System.out.println("---------------------------");
		System.out.println("< " + currDir.getAbsolutePath().replace(dirPath, "~") + " >");
		File[] sub = currDir.listFiles();
		int countJava = 0;
		for(int i = 0; i < sub.length; i++) {
			String fileName = sub[i].getName();
			if(sub[i].isFile()) {
				if(fileName.substring(fileName.lastIndexOf(".") + 1
						, fileName.length()).equals("java")) {
					countJava++;
					System.out.println(fileName);
				}
			}
//			
		}
		System.out.println("\n Total: " + countJava);
		for(File next : sub) {
			if(next.isDirectory()) {
				countJava += allJavaFiles(next);
			}
		}
		return countJava;
	}
}
