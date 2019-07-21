package Function;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class 줄_앞글자_지우기 {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File inFile = new File("result/in.txt");
		File outFile = new File("result/out.txt");
		int begin = 4;
		
		Scanner cs = new Scanner(inFile);
		PrintWriter out = new PrintWriter(outFile);
		
		while(cs.hasNextLine())
		{
			String line = cs.nextLine();
			line = line.substring(begin);
			out.println(line);
		}
		cs.close();
		out.close();
		
	}

}
