package Free;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class 줄맞춤 {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner cs = new Scanner(System.in);
		PrintWriter out = new PrintWriter(new File("output.txt"));
		
		boolean done = false;
		while(!done)
		{
			System.out.print("input data: ");
			String line = cs.nextLine();
			String[] words = line.split("\t");
			if(words.length == 1 && words[0].equals("exit"))
			{
				System.out.println("\n\nSystem Exit. Goodbye");
				break;
			}
			else if(words.length != 2)
			{
				System.out.println("Wrong input. Please retry.\n");
				continue;
			}
			
			out.printf("%-50s", words[0]);
			out.println(words[1]);
		}
		
		cs.close();
		out.close();
	}

}
