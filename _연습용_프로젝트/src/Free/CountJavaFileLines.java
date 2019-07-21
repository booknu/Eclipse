package Free;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemNotFoundException;

public class CountJavaFileLines {

	static String dirPath = "D:\\대학\\Java\\MainProject";
//	static String dirPath = "D:\\대학\\Java\\MainProject\\TumblrTest";
	public static void main(String[] args) throws Exception {
		// TODO Auto-gSenerated method stub
		File target = new File(dirPath);
		if(!target.exists()) {
			throw new FileNotFoundException("\n<ERROR: target directory not found>");
		}
		if(!target.isDirectory()) {
			throw new FileSystemNotFoundException("\n<ERROR: target directory is not a directory>");
		}
		
		int totalLine = allJavaFiles(target);
		System.out.println("===========================");
		System.out.println("Total Lines: " + totalLine);
	}
	
	/**
	 * print all of the file / directories inside currDir
	 * return how many java file is exists
	 * 
	 * @param currDir current directory
	 * @return java files line number
	 * @throws FileNotFoundException 
	 */
	public static int allJavaFiles(File currDir) throws IOException {
		System.out.println("---------------------------");
		System.out.println("< " + currDir.getAbsolutePath().replace(dirPath, "~") + " >\n");
		File[] sub = currDir.listFiles();
		int totalCount = 0;
		for(int i = 0; i < sub.length; i++) {
			String fileName = sub[i].getName();
			if(sub[i].isFile()) {
				if(fileName.substring(fileName.lastIndexOf(".") + 1
						, fileName.length()).equals("java")) {
					// count lines
					int count = 0;
					InputStream is = new BufferedInputStream(new FileInputStream(sub[i].getPath()));
				    try {
				        byte[] c = new byte[1024];
				        int readChars = 0;
				        while ((readChars = is.read(c)) != -1) {
				            for (int j = 0; j < readChars; ++j) {
				                if (c[j] == '\n') {
				                    ++count;
				                }
				            }
				        }
				        totalCount += count;
				        System.out.println(fileName + ": " + count);
				    } finally {
				        is.close();
				    }
					
				}
			}
//			System.out.println(fileName);
		}
		System.out.println("\n Total: " + totalCount);
		for(File next : sub) {
			if(next.isDirectory()) {
				totalCount += allJavaFiles(next);
			}
		}
		return totalCount;
	}
}
