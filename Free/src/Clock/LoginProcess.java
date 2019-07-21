package Clock;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LoginProcess {

	StringBuilder inputID = new StringBuilder();
	StringBuilder inputPWD = new StringBuilder();
	boolean loginSuccess = false;
	
	public LoginProcess(String id, char[] pwd) throws IOException
	{
		File inputFile = new File("passwd.pwd");
		Scanner in = new Scanner(inputFile);
		in.nextLine();
		String pw = "";
		for(int i=0; i<pwd.length; i++)
			pw += pwd[i];
		
		int count = 0;
		boolean correctID = false;
		boolean correctPWD = false;
		while(in.hasNext())
		{		
			if(count == 0)
			{
				inputID.append(in.next());
				if(inputID.toString().equals(id))
					correctID = true;
				else
					correctID = false;
				
				inputID.delete(0, inputID.length());
				count++;
			}
			
			else if(count == 1)
			{
				inputPWD.append(in.next());
				if(inputPWD.toString().equals(pw))
					correctPWD = true;
				else
					correctPWD = false;
				
				inputPWD.delete(0, inputPWD.length());
				count++;
			}
			
			else
			{
				in.nextLine();
				count = 0;
			}
			
			if(correctID && correctPWD)
			{
				loginSuccess = true;
				break;
			}
		}
		
		in.close();
	}
	/* 구성자 완료 */
	
	public boolean getResult()
	{
		return loginSuccess;
	}

}
