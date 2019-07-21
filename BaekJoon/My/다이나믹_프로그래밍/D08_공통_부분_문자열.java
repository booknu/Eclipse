package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/5582
 * **********
 * <해결방안>	: 
 * 
 * 문자열 A, B가 있다고 할 때
 * dp[i][j]란, A의 i, B의 j에서 시작하는 문자열 중 가장 큰 공통 문자열의 길이.
 * 
 * dp[i][j]를 계산할 때 i, j에서 시작해 다른 문자가 나올 때까지 검사를 하는데,
 * 이 때 만약 i + 1, j + 1의 문자도 같다면 dp[i + 1][j + 1]은 또 계산할 필요가 없다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * 처음에는 무식하게 A의 i, B의 j에서 시작하는 경우를 메모리에 저장 없이
 * 하나하나 세었더니 시간 초과가 남.
 * 이것 때문에 중복된 연산이 무엇이 있을지 생각하게 됨
 * 
 * **********
 * @author LENOVO
 *
 */
public class D08_공통_부분_문자열 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		char[] strA = sc.readLine().toCharArray();
		char[] strB = sc.readLine().toCharArray();
		
		int[][] dp = new int[strA.length][strB.length];	// A의 i에서 시작하고, B의 j에서 시작하는
														// 같은 문자열의 최대값
		int max = 0;
		for(int i = 0; i < strA.length; i++) {
			for(int j = 0; j < strB.length; j++) {
				// 이미 계산 됐으면
				if(dp[i][j] != 0) {
					continue;
				}
				int k; // i, j + (k - 1)까지는 같은 문자열임
				for(k = 0; k < strA.length - i && k < strB.length - j; k++) {
					if(strA[i + k] != strB[j + k]) {
						break;
					}
				}
				for(int index = 0; index < k; index++) {
					dp[i + index][j + index] = k - index;
					max = Math.max(max, dp[i + index][j + index]);
				}
			}
		}
		System.out.println(max);
	}	
}
