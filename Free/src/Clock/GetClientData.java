package Clock;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GetClientData {

	private String id;
	private String pw;
	private String author;
	private String clientCode;
	private String name;
	private String birth;
	private String phone;
	private String email;
	private String pwdQ;
	private String pwdA;
	private int row;
	private boolean error;
	
	/**
	 * 유저코드로 어떤 유저인지 알아낸다
	 * @param clientCode 유저 코드
	 * @throws IOException
	 */
	public GetClientData(int clientCode) throws IOException
	{
		// clientCode를 String 형태로 변경
		this.clientCode = ""+clientCode;
		// clientCode가 들어 있는 행을 찾음
		row = searchColumn(this.clientCode, "passwd.pwd", 1, 3);
		
		// clientCode가 들어 있을 때
		if(row != -1)
		{
			error = false;
			id = getText("passwd.pwd", row, 0);
			pw = getText("passwd.pwd", row, 1);
			author = getText("passwd.pwd", row, 2);
			name = getText("userInformation", row, 1);
			birth = getText("userInformation", row, 2);
			phone = getText("userInformation", row, 3);
			email = getText("userInformation", row, 4);
			pwdQ = getTextAfter("userInformation", row, 6).trim();
			pwdA = getText("userInformation", row, 5);
		}
		
		else
			error = true;
	}
	
	/**
	 * 특정 타입의 정보로 어떤 유저인지 알아낸다
	 * @param type 타입 (id, phone, email)
	 * @param information	
	 */
	public GetClientData(String type, String information) throws IOException
	{
		String file = "";
		int col = -1;
		if(type.equals("id"))
		{
			file = "passwd.pwd";
			col = 0;
		}
		
		else if(type.equals("phone") || type.equals("email"))
		{
			file = "userInformation";
			if(type.equals("phone"))
				col = 3;
			else if(type.equals("email"))
				col = 4;
		}
		
		else
			error = true;
			
		if(!error)
		{
			row = searchColumn(information, file, 1, col);
			if(row != -1)
			{
				error = false;
				id = getText("passwd.pwd", row, 0);
				pw = getText("passwd.pwd", row, 1);
				author = getText("passwd.pwd", row, 2);
				clientCode = getText("passwd.pwd", row, 3);
				name = getText("userInformation", row, 1);
				birth = getText("userInformation", row, 2);
				phone = getText("userInformation", row, 3);
				email = getText("userInformation", row, 4);
				pwdQ = getTextAfter("userInformation", row, 6).trim();
				pwdA = getText("userInformation", row, 5);
			}
			
			else
				error = true;
		}
	}
	/* 구성자 끝 **/
	
	/**
	 * 이 객체의 클라이언트의 정보를 얻어온다.
	 * 옵션 ***********************
	 * id pw author name birth
	 * phone email pwdQ pwdA
	 * **************************
	 * @param which 옵션
	 * @return 클라이언트의 정보
	 */
	public String getData(String which)
	{
		if(which.equals("id"))
			return id;
		else if(which.equals("pw"))
			return pw;
		else if(which.equals("author"))
			return author;
		else if(which.equals("name"))
			return name;
		else if(which.equals("birth"))
			return birth;
		else if(which.equals("phone"))
			return phone;
		else if(which.equals("email"))
			return email;
		else if(which.equals("pwdQ"))
			return pwdQ;
		else if(which.equals("pwdA"))
			return pwdA;
		else
			return "WrongType";
	}
	
	/**
	 * 파일의 열을 검사해 해당 문자열이 있는지 찾는다.
	 * 있으면 문자열이 있는 행을 반환하고
	 * 없으면 -1을 반환한다
	 * 시작 지점을 정할 수 있다
	 * @param word 찾을 단어
	 * @param file 찾을 파일 이름
	 * @param from 몇 행 부터 찾을지 (0행부터 시작)
	 * @param where 몇 열을 검사할지 (0열부터 시작)
	 * @return 문자열이 들어있는 행
	 * 			없을 시에 -1을 반환
	 */
	private int searchColumn(String word, String file, int from,int where) throws IOException
	{
		Scanner in = new Scanner(new File(file));
		
		// 시작할 곳 까지 이동한다
		for(int i=0; i<from; i++)
			in.nextLine();
		
		// 입력받은 문자열을 임시 저장할 공간
		StringBuilder sb = new StringBuilder("");
		// 몇 행에 있는지
		int line = from;
		
		/* 원하는 문자를 정해진 열에서 검색 *********************************/
		while(in.hasNextLine())
		{
			for(int i=0; i<where; i++)
				in.next();
			if(in.hasNext())
				sb.append(in.next());
			
			// 찾았으면 그 행을 반환하고 메소드 끝
			if(sb.toString().equals(word))
			{
				in.close();
				return line;
			}
				
			// 못 찾았으면 다음 행을 찾을 준비하고 sb를 초기화. line 번호를 하나 올림
			if(in.hasNextLine())
				in.nextLine();
			sb.delete(0, sb.length());
			line ++;
		}
		/* 루프 끝 *************************************************/
		
		in.close();
		// 위 과정에서 못 찾았으면 -1 반환
		return -1;
	}
	
	/**
	 * 파일의 행, 열에 들어있는 단어를 반환한다
	 * @param file 파일 이름
	 * @param row 행
	 * @param col 열
	 * @return 단어
	 * @throws IOException
	 */
	private String getText(String file, int row, int col) throws IOException
	{
		if(error)
			return "There are no such word";
		else
		{
			Scanner in = new Scanner(new File(file));
			// 해당 row까지 이동
			for(int i=0; i<row; i++)
				in.nextLine();
			// 해당 col까지 이동
			for(int i=0; i<col; i++)
				in.next();
			String a = in.next();
			in.close();
			return a;
		}
		
	}
	
	/**
	 * 파일의 행, 열에 들어있는 단어를 반환한다
	 * 입력받은 열 이후에 있는 문장을 반환한다.
	 * @param file 파일 이름
	 * @param row 행
	 * @param colAfter 열
	 * @return 단어
	 * @throws IOException
	 */
	private String getTextAfter(String file, int row, int colAfter) throws IOException
	{
		Scanner in = new Scanner(new File(file));
		// 해당 row까지 이동
		for(int i=0; i<row; i++)
			in.nextLine();
		// 해당 colAfter까지 이동
		for(int i=0; i<colAfter; i++)
			in.next();
		String a = in.nextLine();
		in.close();
		return a;
	}
	
	/**
	 * 에러가 있는지 
	 * @return true 에러 false 에러 없음
	 */
	public boolean isError()
	{
		return error;
	}

}
