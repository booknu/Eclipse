package chap06_BinaryTree;
public class HW {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		// p1
		System.out.println("------ case 1 ------");
		BinaryTree<String> expr = p1();
		test(expr);
		
		System.out.println("\n------ case 2 ------");
		expr = p2();
		test(expr);
		
		System.out.println("\n------ case 3 ------");
		expr = p3();
		test(expr);
		
		System.out.println("\n------ case 4 ------");
		expr = p4();
		test(expr);
	}

	/**
	 * 정리된 Tree로 연산을 수행
	 * 
	 * @param expr
	 * @return
	 * @throws Exception
	 */
	public static int calc(BinaryTree<String> expr)
	{
		return calcRecur(expr.root);
	}
	
	/**
	 * 연산을 재귀적으로 수행
	 * 
	 * @param node 연산을 시작할 node
	 * @return 연산 결과 (오류가 발생하면 Integer.MIN_VALUE 반환
	 * @throws Exception
	 */
	private static int calcRecur(BTNode<String> node)
	{
		// 현재 node가 숫자면 그대로 숫자 반환
		if(isNumber(node.data)) return Integer.parseInt(node.data);
		int left = 0, right = 0;
		char operator = node.data.charAt(0);
		// left가 숫자가 아니면 그 밑 연산을 함
		if(node.left != null) {
			left = calcRecur(node.left);
		}
		// right가 숫자가 아니면 그 밑 연산을 함
		if(node.right != null) {
			right = calcRecur(node.right);
		}
		// left와 right을 연산하고 반환
		try{
			return binaryOperation(left, right, operator);
		} catch(DividedByZeroException e) {
			System.out.println("<Error: divided by zero exception>");
			return Integer.MIN_VALUE;
		} catch(UnsupportedOperationException e) {
			System.out.println("<Error: unsupported operation exception>");
			return Integer.MIN_VALUE;
		}
		
	}

	/**
	 * (4*3)-(5*(20/10))을 Tree형태로 만들어 반환
	 * @return
	 */
	public static BinaryTree<String> p1() {
		BTNode<String> root = new BTNode<>("-");
		root.set("*", "*");
		root.left.set("4", "3");
		root.right.set("5", "/");
		root.right.right.set("20", "10");
		BinaryTree<String> expr = new BinaryTree<String>(root);
		return expr;
	}
	
	/**
	 * ((17-5)*3)-(5*(20/(4-2)))을 Tree형태로 만들어 반환
	 * @return
	 */
	public static BinaryTree<String> p2() {
		BTNode<String> root = new BTNode<>("-");
		root.set("*", "*");
		root.left.set("-", "3");
		root.left.left.set("17", "5");
		root.right.set("5", "/");
		root.right.right.set("20", "-");
		root.right.right.right.set("4", "2");
		BinaryTree<String> expr = new BinaryTree<String>(root);
		return expr;
	}
	
	/**
	 * ((17-5)*3)-(5*(20/(4-4)))을 Tree형태로 만들어 반환
	 * @return
	 */
	public static BinaryTree<String> p3() {
		BTNode<String> root = new BTNode<>("-");
		root.set("*", "*");
		root.left.set("-", "3");
		root.left.left.set("17", "5");
		root.right.set("5", "/");
		root.right.right.set("20", "-");
		root.right.right.right.set("4", "4");
		BinaryTree<String> expr = new BinaryTree<String>(root);
		return expr;
	}
	
	/**
	 * (3*7)/(5+2)을 Tree형태로 만들어 반환
	 * @return
	 */
	public static BinaryTree<String> p4() {
		BTNode<String> root = new BTNode<>("/");
		root.set("*", "+");
		root.left.set("3", "7");
		root.right.set("5", "2");
		BinaryTree<String> expr = new BinaryTree<String>(root);
		return expr;
	}
	
	/**
	 * Tree를 prefix, infix, postfix 방식으로 각각 출력
	 * @param expr
	 */
	public static void printTree(BinaryTree<String> expr) {
		System.out.print("infix: ");
		expr.inorder();
		System.out.println();
		System.out.print("prefix: ");
		expr.preorder();
		System.out.println();
		System.out.print("postfix: ");
		expr.postorder();
		System.out.println("\n");
	}
	
	/**
	 * TEST
	 * @param expr
	 * @throws Exception 
	 */
	public static void test(BinaryTree<String> expr) {
		printTree(expr);
		System.out.println("result: " + calc(expr));
	}
	
	/**
	 * str이 숫자인지 확인
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
        boolean result = false; 
        try{
            Double.parseDouble(str) ;
            result = true ;
        }catch(Exception e){}
        return result ;
    }
	
	/**
	 * 이항 연산을 수햄
	 * @param L 
	 * @param R
	 * @param operator
	 * @return
	 * @throws DividedByZeroException 
	 * @throws UnsupportedOperationException 
	 */
	public static int binaryOperation(int L, int R, char operator) throws DividedByZeroException, UnsupportedOperationException {
		switch(operator) {
		case '+': 
			return L + R;
		case '-': 
			return L - R;
		case '*':
			return L * R;
		case '/':
			if(R == 0) {
				throw new DividedByZeroException("Error: divided by zero exception");
			}
			return L / R;
		default:
			throw new UnsupportedOperationException("Error: wrong operator exception");
		}
	}
}
class DividedByZeroException extends Exception{
	public DividedByZeroException() {
		super("divided by zero exception");
	}
	public DividedByZeroException(String msg) {
		super(msg);
	}
}
class UnsupportedOperationException extends Exception{
	public UnsupportedOperationException() {
		super("unsupported operation exception");
	}
	public UnsupportedOperationException(String msg) {
		super(msg);
	}
}
