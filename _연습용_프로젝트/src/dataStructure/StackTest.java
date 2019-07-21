package dataStructure;

public class StackTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Stack<Integer> s = new Stack<Integer>();
		
		s.push(1);
		s.push(2);
		size(s);
		pop(s);
		pop(s);
		size(s);
	}
	
	public static void pop(Stack<Integer> s){
		int poped = s.pop();
		System.out.println("pop: " + poped);
	}
	
	public static void size(Stack<Integer> s){
		System.out.println("size: " + s.size);
	}

}
