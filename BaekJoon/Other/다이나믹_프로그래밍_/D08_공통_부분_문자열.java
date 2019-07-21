package 다이나믹_프로그래밍_;
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
 * A[i]까지의 문자열에 대해
 * dp[j] = B[j]까지 같은 최대 공통 부분 문자열의 길이
 * 
 * A의 각 자리에 대해 loop를 돌며
 * 현재 A[i]에 대해 구할 때
 * A[i - 1]까지에 대한 dp[j]는 구해져 있으니
 * A[i]까지에 대한 dp[j]는
 * 
 * A[i], B[j]가 같을 경우
 * dp[j] = dp[j - 1] + 1 ==> dp[j - 1]은 A[i - 1]까지에 대한 dp
 * A[i], B[j]가 다를 경우 
 * dp[j] = 0
 * 
 * 
 * **********
 * <오답노트>	: 
 * 
 * 내 코드보다 훨씬 빠름
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
		int max = 0;
		int[] dp = new int[strB.length + 1];
		for(int i = 0; i < strA.length; i++) {
			for(int j = strB.length - 1; j >= 0; j--) {
				if(strA[i] == strB[j]) {
					dp[j + 1] = dp[j] + 1;
				} else {
					dp[j + 1] = 0;
				}
				max = Math.max(dp[j + 1], max);
			}
		}
		System.out.println(max);
	}
	
}
