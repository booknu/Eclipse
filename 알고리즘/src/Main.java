
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Int a = new Int(10);
		Int b = a;
		a.val = 20;
		System.out.println(b.val);
	}
	static class Int {
		int val;
		public Int(int v) {
			val = v;
		}
	}
}
