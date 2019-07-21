
public class 비트연산 {

	/**
	 * bitmask의 n 비트를 무조건 1로 만듬
	 * @param bitmask
	 * @param n
	 * @return
	 */
	public static int setTrue(int bitmask, int n) {
		return (bitmask | (1 << n));
	}
	
	/**
	 * bitmask의 n 비트를 무조건 0으로 만듬
	 * @param bitmask
	 * @param n
	 * @return
	 */
	public static int setFalse(int bitmask, int n) {
		return (bitmask & (~(1 << n)));
	}

	/**
	 * bitmask의 n 비트가 1인지 확인
	 * @param bitmask
	 * @param n
	 * @return
	 */
	public static boolean isTrue(int bitmask, int n) {
		return (bitmask & (1 << n)) > 0;
	}
}
