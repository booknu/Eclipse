package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/************
 * <주소>		: https://www.acmicpc.net/problem/1958
 * **********
 * <해결방안>	: 
 * 
 * http://twinw.tistory.com/126
 * 
 * LCS와 원리는 같음. 단지 3개가 됐을 뿐임
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. substring과 subsequence는 차이가 있는데, (부분 문자열, 부분 수열)
 *    123456789 에서
 *    substring은 123, 567 같은 연속된 것이고
 *    subsequence는 149, 2349 같이 연속되지 않아도 된다.
 *    ==> https://en.wikipedia.org/wiki/Substring
 *    
 * **********
 * @author LENOVO
 *
 */
public class D10_LCS_3 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		char[][] str = new char[3][];
		int[] size = new int[3];
		for(int i = 0; i < 3; i++) {
			str[i] = ("0" + sc.readLine()).toCharArray();
			size[i] = str[i].length;
		}
		
		int result = 0;
		int dp[][][] = new int[size[0]][size[1]][size[2]];
		for(int i = 1; i < size[0]; i++) {
			for(int j = 1; j < size[1]; j++) {
				for(int k = 1; k < size[2]; k++) {
					if(str[0][i] == str[1][j] && str[1][j] == str[2][k]) {
						dp[i][j][k] = dp[i - 1][j - 1][k - 1] + 1;
						result = Math.max(dp[i][j][k], result);
					} else {
						dp[i][j][k] = Math.max(dp[i-1][j][k], dp[i][j-1][k]);
						dp[i][j][k] = Math.max(dp[i][j][k-1], dp[i][j][k]);
						dp[i][j][k] = Math.max(dp[i-1][j-1][k], dp[i][j][k]);
						dp[i][j][k] = Math.max(dp[i-1][j][k-1], dp[i][j][k]);
						dp[i][j][k] = Math.max(dp[i][j-1][k-1], dp[i][j][k]);
					}
				}
			}
		}
		System.out.println(result);
	}
	
}