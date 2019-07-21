package Free;

import java.text.ParseException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class PremiumCalculater {

	public static HashMap<Character, Integer> oper = new HashMap<Character, Integer>();	// <연산자, 우선순위>
	public static char[] opening = {'(', '{', '['};
	public static char[] closing = {')', '}', ']'};
	public static final char NOTMATCH = '!';
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init();
//		String expr = "1 * 2 + 3 + 4 * 5 * 6 + 7";
		String expr = "1 * 2 + ( 3 / 4 ) * 5";
		String[] split = expr.split(" ");
		char[] exp = new char[split.length];
		for(int i = 0; i < split.length; i++)
			exp[i] = split[i].charAt(0);
		
		System.out.print("infix: ");
		print(exp);
		char[] post = infixToPostfix(exp);
		System.out.print("post: ");
		print(post);
		System.out.println("result: " + calculate(post));
		
	}
	
	/**
	 * infix 표기에서 postfix 표기로 바꿈
	 * @param exp 식
	 * @return 바뀐 식
	 */
	public static char[] infixToPostfix(char[] exp){
		Stack<Character> s = new Stack<Character>();
		char[] ret = new char[exp.length];
		int ind = 0;
		for(int i = 0; i < exp.length; i++)
		{
			char closingMatch = closingMatch(exp[i]);
			// 여는 괄호면 무조건 push
			if(isOpening(exp[i]))
				s.push(exp[i]);
			// 닫는 괄호면 여는 괄호가 나올 때까지 pop print
			else if(closingMatch != NOTMATCH){
				
				char poped = s.pop();
				while(poped != closingMatch)
				{
					if(s.isEmpty())
					{
						System.err.println("ERROR: 괄호 짝이 맞지 않습니다.");
						return null;
					}
					ret[ind++] = poped;
					poped = s.pop();
				}
			}
			// 연산자가 아니면 무조건 print
			else if(!isOper(exp[i]))
			{
				ret[ind++] = exp[i];
			}
			// 연산자면
			else
			{
				// 현재 들어온 연산자가 스택의 TOP 보다 우선순위가 높으면 push
				if(s.isEmpty() || comparePriority(exp[i], s.peek()) > 0)
					s.push(exp[i]);
				// 아니면 pop print 하고 현재 연산자 push
				else
				{
					ret[ind++] = s.pop();
					s.push(exp[i]);
				}
					
			}
		}
		// 스택에 남은 모든 연산자 pop print
		while(!s.isEmpty())
			ret[ind++] = s.pop();
		char[] copied = resize(ret, ind);
		return copied;
	}
	
	/**
	 * 연산을 수행
	 * @param exp	post 형식이어야함
	 * @return 연산 결과
	 */
	public static double calculate(char[] exp){
		try{
			double result = 0;
			Stack<Double> s = new Stack<Double>();
			for(int i = 0; i < exp.length; i++)
			{
				if(!isOper(exp[i]))
					s.push((double)parseInt(exp[i]));
				else
				{
					try{
						double b = s.pop();
						double a = s.pop();
						result = calc(a, b, exp[i]);
						s.push(result);
					} catch(EmptyStackException e){
						System.err.println("연산자가 잘못되었습니다.");
						e.printStackTrace();
					}
				}
			}
			return s.pop();
			
		} catch(Exception e){
			System.err.println("잘못된 식입니다.");
			e.printStackTrace();
			return -1234;
		}
		
	}

	/**
	 * 출력
	 * @param exp
	 */
	public static void print(char[] exp){
		for(int i = 0; i < exp.length; i++)
			System.out.print(exp[i] + " ");
		System.out.println();
	}
	
	/**
	 * 연산자인지?
	 * @param c 문자
	 * @return 문자가 연산자?
	 */
	public static boolean isOper(char c){
		Set<Character> keys = oper.keySet();
		for(char key : keys)
			if(key == c)
				return true;
		return false;
	}
	
	/**
	 * 연산자 우선순위 비교
	 * @param a
	 * @param b
	 * @return
	 */
	public static int comparePriority(char a, char b){
		return oper.get(a) - oper.get(b);
	}
	
	/**
	 * 여는 괄호와 매치되는 닫는 괄호를 가져옴
	 * @param c 문자
	 * @return 매치되는 괄호 or '!' (매치되는게 없음)
	 */
	public static boolean isOpening(char c){
		for(int i = 0; i < opening.length; i++)
			if(opening[i] == c)
				return true;
		return false;
	}
	
	/**
	 * 닫는 괄호와 매치되는 여는 괄호를 가져옴
	 * @param c 문자
	 * @return 매치되는 괄호 or NOTMATCH (매치되는게 없음)
	 */
	public static char closingMatch(char c){
		for(int i = 0; i < closing.length; i++)
			if(closing[i] == c)
				return opening[i];
		return NOTMATCH;
	}
	
	private static void init(){
		oper.put('+', 0);
		oper.put('-', 0);
		oper.put('*', 1);
		oper.put('/', 1);
		for(int i = 0; i < opening.length; i++)
			oper.put(opening[i], -1);
	}
	
	private static int parseInt(char num) throws ParseException{
		int ret = (int)num - 48;
		if(ret < 0 || ret > 9)
			throw new ParseException("char -> int 변환 작업 오류\n", 1);
		return ret;
	}
	
	private static double calc(double a, double b, char oper){
		switch(oper)
		{
		case '+': return a + b;
		case '-': return a - b;
		case '*': return a * b;
		case '/': return 1.0 * a / b;
		default: return 0;
		}
	}
	private static char[] resize(char[] arr, int size)
	{
		char[] ret = new char[size];
		for(int i = 0; i < size; i++)
			ret[i] = arr[i];
		return ret;
	}
}
