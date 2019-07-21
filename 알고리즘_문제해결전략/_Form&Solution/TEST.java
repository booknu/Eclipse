import java.util.Arrays;

public class TEST {
	
	int size = 10;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thing[] t = {new Thing(3),new Thing(1),new Thing(2)};
		Arrays.sort(t, (o1, o2) -> {
				return o1.value - o2.value;
		});
		for(Thing a : t) {
			System.out.println(a.value);
		}
	}
	
	static class Thing {
		int value;
		public Thing(int v) {
			value = v;
		}
	}
	
	
	public static int setBit(int n, boolean value, int mask) {
		if(value) {
			// set 1
			return mask | (1 << n);
		} else {
			// set 0
			return mask & (~(1 << n));
		}
	}
	
	public static boolean getBit(int n, int mask) {
		return (mask & (1 << n)) > 0;
	}
	
	public static void printMask(int mask) {
		recurMask(mask);
		System.out.println();
	}
	
	public static void recurMask(int mask) {
		if(mask == 0) return;
		recurMask(mask >> 1);
		System.out.print((mask & 1) + " ");
	}
}
