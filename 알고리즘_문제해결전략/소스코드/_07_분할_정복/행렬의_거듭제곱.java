package _07_분할_정복;


/************
 * <주소>		: p178
 * **********
 * <해결방안>	: 
 * 
 * A^m = A^(m/2) * A^(m/2)
 * A^0 = E 를 이용함
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 행렬의_거듭제곱 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
					ret[i][j] = A[k][j] * B[i][k];
				}
			}
		}
		
		return ret;
	}
}
