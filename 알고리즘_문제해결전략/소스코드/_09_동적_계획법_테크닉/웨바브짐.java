package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		: p320
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 문제 조건 ]]
 *    - prev < curr
 *    - prev % m = 0
 *    - prev, curr의 각 자리를 구성하는 수의 숫자는 같다.
 * 
 * 
 * 2. [[ 아이디어 ]]
 *    현재까지 조합한 숫자를 함수의 인자로 줘버리면 메모이제이션을 하기가 힘들다.
 *    따라서 범위가 작은 숫자를 인자로 넘겨줘야 하는데, 
 *    책 p325처럼 상위자리에서 M으로 나눈 나머지를 인자로 넘기면
 *    그 바로 아래자리에서 (prevMod * 10 + 현재숫자) % M을 통하여
 *    currMod를 구할 수 있고, 그것을 다시 인자로 넘길 수 있게 된다.
 *    그리고 M의 범위는 20까지이니 메모이제이션을 하기에도 충분하다.
 *    
 *    또한 사용하지 않은 숫자들인 boolean 배열을 bitmask로 표현한 remain을 인자로 주고,
 *    현재 자리수 digit도 인자로 주고,
 *    
 *    마지막으로 이전까지의 자리가 E와 같은지, 아니면 더 작은지 여부를 알려주는 less를 인자로 준다.
 *    만약 더 작다면 남아있는 숫자 중 어떤 숫자를 사용해서 조합하든 모두 가능하고,
 *    만약 같다면 남아있는 숫자 중 현재 자리에 대해 같거나 더 작은 숫자를 사용해서 조합해야한다.
 *    
 *    digit = 0, 즉 모든 조합을 마친 경우 prevMod % M = 0이어야 그 조합이 prevPrice가 될 수 있다.
 *    또한 그 이전에, less = true이지 않으면, prevPrice = currPrice 이기 때문에 조합 자체가 불가능하다.
 *    
 *    digit != 0이라면 (남아있는 자리가 있다면)
 *    다음 자리를 선택하는데, 같은 숫자가 여러번 남아있으면 중복해서 사용할 우려가 있기 때문에
 *    미리 sort를 통해 중복을 피하는게 중요하다.
 *    따라서 0 ~ 9까지의 숫자를 시도해보는데, i가 남았는지 remain 배열을 돌며 확인하고,
 *    만약에 남았다면 less = true인 경우 그 숫자로 다음 조합을 만들어도 좋고,
 *    less = false라면 i가 E의 현재 자리보다 작거나 같아야 다음 조합을 만들 수 있다.
 *    
 *   
 * **********
 * <오답노트>	: 
 * 
 * 1. 처음에는 주어진 E를 구성하고 있는 숫자들의 개수를
 *    15진수의 number에 저장해놓고 ( 15진수의 각 자리는 0 ~ 9까지의 숫자가 몇 개 있는지 )
 *    최상위 비트부터 시작해 number에 남아있는 숫자로 만들 수 있는
 *    모든 조합을 만들어 재귀의 마지막(모든 bit를 채운 뒤)에서 만든 조합이
 *    M으로 나누어 떨어지는지 검사하여 1 or 0을 반환하는 재귀 함수를 만들었는데,
 *    
 *    현재까지 만든 조합을 메모이제이션 하기도 까다롭지만,
 *    number의 범위가 최대 5000억까지 가능해서 배열로 표현하기가 불가능했다.
 *    그 이유는 9만 14번 나오는 숫자 같은 경우 0 ~ 8은 모두 0임에도 불구하고
 *    9 하나를 위해 5000억이라는 공간을 써야하는 경우가 나오기 때문이다.
 *    (공간이 많이 낭비된다.)
 *    
 *    따라서 E의 각 자리에 대해 그 숫자가 남아있는지에 대한 정보를 bitmask로 표현하면
 *    2^14 = 16000의 공간이 필요하여 현실적인 배열 범위를 가질 수 있게 만들었다.
 * 
 * **********
 * @author LENOVO
 *
 */
public class 웨바브짐 {

	public static long E; // curr price
	public static int M; // candy's price
	public static int[] origin; // express E as array
	public static int[] number; // price's consist number
	public static int totalDigit; // E's digit
	
	public static int[][][] memo;
	
	public static final int DIV = 1000000007;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			E = sc.nextLong(); // current egg price
			M = sc.nextInt(); // prev is multiple of M
			totalDigit = digit(E); // digit of E
			
			// parse E to array
			origin = new int[totalDigit];
			for(int i = 0; i < totalDigit; i++) {
				origin[i] = (int)((E % Math.pow(10, i + 1)) / Math.pow(10, i));
			}
			// sorted origin (matched to bitmask that express number[i] has been remained)
			number = Arrays.copyOf(origin, origin.length);
			Arrays.sort(number);
			
			// initialize memo
			memo = new int[1 << totalDigit][M][2];
			for(int i = 0; i < memo.length; i++) {
				for(int j = 0; j < M; j++) {
					Arrays.fill(memo[i][j], -1);
				}
			}
			
			System.out.println(generate(totalDigit, (1 << totalDigit) - 1, 0, false));
		}
	}

	/**
	 * 
	 * @param digit
	 * @param remain
	 * @param mod
	 * @param less
	 * @return
	 */
	public static int generate(int digit, int remain, int mod, boolean less) {
		// combination complete
		if(digit == 0) {
			return mod % M == 0 && less? 1 : 0;
		}
		
		// memoization
		int lessIndex = less? 1 : 0;
		if(memo[remain][mod][lessIndex] != -1) {
			return memo[remain][mod][lessIndex];
		}
		
		int ret = 0;
		// select 0 ~ 9 if it exists
		for(int select = 0; select < 10; select++) {
			for(int i = 0; i < totalDigit; i++) {
				// selected number is remained
				if(number[i] == select && getBit(i, remain)) {
					// make all of the combination
					// can make combination (less than E)
					if(less || select <= origin[digit - 1]) {
						remain = setBit(i, false, remain);
						ret += generate(digit - 1, remain
								, (mod * 10 + select) % M
								, less? true : select < origin[digit - 1]);
						remain = setBit(i, true, remain);
						ret %= DIV;
					}
					break;
				}
			}
		}
		memo[remain][mod][lessIndex] = ret;
		return ret;
	}
	
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

	/**
	 * get digit of number 
	 * 
	 * @param n number
	 * @return digit of n
	 */
	public static int digit(long n) {
		int ret = 0;
		while(n > 0) {
			ret++;
			n /= 10;
		}
		return ret;
	}
	
	static class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner(String s) {
			try {
				br = new BufferedReader(new FileReader(s));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		public FastScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String nextToken() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(nextToken());
		}

		long nextLong() { 
			return Long.parseLong(nextToken());
		}

		double nextDouble() {
			return Double.parseDouble(nextToken());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
