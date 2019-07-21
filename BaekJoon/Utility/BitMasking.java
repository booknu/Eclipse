
public class BitMasking {
	/**
	 * set mask bit to value (true = 1, false = 0)
	 * 
	 * @param n n bit
	 * @param value true = 1, false = 0
	 * @param mask mask
	 * @return changed mask
	 */
	public static int setBit(int n, boolean value, int mask) {
		if(value) {
			// set 1
			return mask | (1 << n);
		} else {
			// set 0
			return mask & (~(1 << n));
		}
	}

	/**
	 * get mask bit
	 * 
	 * @param n n bit
	 * @param mask mask
	 * @return true / false
	 */
	public static boolean getBit(int n, int mask) {
		return (mask & (1 << n)) > 0;
	}
}
