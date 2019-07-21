package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		:
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 조건 ]]
 *    - 점화식이 선형 변환 형태일 때 사용 가능
 *    - f: V -> W가 선형변환이려면
 *      a가 F에 포함되고  u, v가 V에 포함될 때
 *      f(au + v) = af(u) + f(v)를 만족함
 *      ( https://namu.wiki/w/선형%20변환 )
 *      
 *      
 * 2. [[ 방법 ]]
 *    C(i) = fib(i-1)
 *           fib(i)      의 2x1행렬
 *    
 *    C(i+1) = fib(i)
 *             fib(i+1)
 *           
 *           = fib(i)
 *             fib(i-1) + fib(i)
 *             
 *           = C(i)[1]
 *             C(i)[0] + C(i)[1]
 *           
 *           ==> C(i)에 어떤 행렬을 곱한 것으로 표현 가능
 *           
 *           = 0 1   fib(i-1)
 *             1 1   fib(i)
 *             
 *           = 0 1   C(i) 
 *             1 1   
 *             
 *           = W * C(i)
 *           = W^2 * C(i-1)
 *           ...
 *           = W^i * C(1)
 *     
 *     C(n) = W^(n-1) * C(1)
 *     
 *  
 *  3. [[ 시간 복잡도 ]]
 *     행렬의 제곱 연산은 분할정복을 통해
 *     O(lgN)의 시간 안에 가능하다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 행렬의_거듭제곱을_이용한_Fibo {

	public static final int DIV = 100000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 40;
		System.out.println(fibByMatrix(n));
		System.out.println(fibNormal(n));
	}
	
	/**
	 * return nth fibo's last 5 numbers
	 * get fibo by using matrix
	 * 
	 * @param n
	 * @return
	 */
	public static int fibByMatrix(int n) {
		if(n == 0) {
			return 0;
		}
		// C(i+1) = W^n-1 * C(1)
		int[][] W = new int[2][2];
		W[0][0] = 0;
		W[0][1] = W[1][0] = W[1][1] = 1;
		int[][] ret = matrixPow(W, n - 1);
		System.out.println("---- W^(n-1) ----");
		for(int i = 0; i < ret.length; i++) {
			for(int j = 0; j < ret[i].length; j++) {
				System.out.print(ret[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("-----------------");
		return ret[1][1];
	}
	
	/**
	 * use normal dynamic programming
	 * use sliding window to save memory
	 * 
	 * @param n
	 * @return
	 */
	public static int fibNormal(int n) {
		if(n <= 1) return n;
		int[] seq = new int[3]; // i starts with 2, so for convenience set array's length to 3
		seq[0] = 0;
		seq[1] = 1;
		for(int i = 2; i <= n; i++) {
			seq[i % 3] = (seq[(i-1) % 3] + seq[(i-2) % 3]) % DIV;
		}
		return seq[n % 3];
	}
	
	/**
	 * n크기의 항등 행렬을 반환한다.
	 * @param n 크기
	 * @return 항등행렬
	 */
	public static int[][] identity(int n) {
		int[][] ret = new int[n][n];
		for(int i = 0; i < n; i++) {
			ret[i][i] = 1;
		}
		return ret;
	}
	
	/**
	 * 행렬A를 m제곱 한다.
	 * 
	 * @param A 행렬
	 * @param m power
	 * @return 제곱된 행렬
	 */
	public static int[][] matrixPow(int[][] A, int m) {
		if(m == 0) return identity(A.length);
		if((m & 1) == 1) return matrixMultiply(matrixPow(A, m - 1), A);
		int[][] half = matrixPow(A, m/2);
		return matrixMultiply(half, half);
	}
	
	/**
	 * 행렬 A와 B를 곱한다.
	 * @param A 행렬A
	 * @param B 행렬B
	 * @return A * B
	 */
	public static int[][] matrixMultiply(int[][] A, int[][] B) {
		
		int[][] ret = new int[A.length][A.length];
		
		for(int i = 0; i < ret.length; i++) {
			for(int j = 0; j < ret.length; j++) {
				for(int k = 0; k < ret.length; k++) {
					ret[i][j] += (A[k][j] * B[i][k]) % DIV;
					ret[i][j] %= DIV;
				}
			}
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
