package _19_큐_스택_데크;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class 짝이_맞지_않는_괄호 {

	static String line;
	static final String OPENING = "({[";
	static final String CLOSING = ")}]";
	static Stack<Character> openStack;
	
	public static void main(String[] args) throws FileNotFoundException {

		Scanner cs = new Scanner(new File("rsc/19-큐 스택 데크/짝이_맞지_않는_괄호.txt"));
		
		int testCase = cs.nextInt(); cs.nextLine();
		String[] line_arr = new String[testCase];
		
		for(int i = 0; i < testCase; i++)
			line_arr[i] = cs.nextLine();
		
		for(int i = 0; i < testCase; i++)
		{
			openStack = new Stack<Character>();
			line = line_arr[i];
			System.out.println(line);
			System.out.println(wellMatched(line)? "YES":"NO");
		}
	}
	
	public static boolean wellMatched(String line)
	{
		for(int j = 0; j < line.length(); j++)
		{
			char temp = line.charAt(j);
			// 여는 괄호
			if(OPENING.contains(temp+""))
				openStack.push(temp);
			// 닫는 괄호
			else
			{
				// 스택이 비어있다면 false
				if(openStack.empty())
					return false;
				// 괄호 짝이 안 맞다면 false
				if(CLOSING.indexOf(temp) != OPENING.indexOf(openStack.peek()))
					return false;
				openStack.pop();
			}
		}
		return openStack.empty();
	}

}
