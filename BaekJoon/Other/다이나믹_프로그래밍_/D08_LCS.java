package 다이나믹_프로그래밍_;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/************
 * <주소>		: https://www.acmicpc.net/problem/9251
 * **********
 * <해결방안>	: 
 * 
 * http://twinw.tistory.com/126
 * 
 * dp[i][j] = A[i]번째, B[j]번째 까지의 최장 공통 부분 수열 길이
 * 
 * 만약 A[i] = B[j]라면
 * i, j번째가 같다면 공통 부분 수열에 추가 될 수 있는데,
 * 이 경우는
 * i와 j번째가 추가되기 전인
 * i-1, j-1번째까지의 공통 부분 수열에 i, j번째를 추가하면 된다.
 * 즉, dp[i][j] = dp[i-1][j-1] + 1이 된다.
 * 
 * 만약 A[i] != B[j]라면
 * i, j번째가 수열에 추가될 수 없고
 * 따라서 i, j번째까지의 최장 공통 부분 수열의 길이는
 * 그 전까지 구해진 것에 의해 종속되는데,
 * dp[i-1][j]까지 이거나 dp[i][j-1]까지가 된다.
 * 즉, dp[i][j] = max( dp[i-1][j], dp[i][j-1] )
 * 
 * 맨 처음에 0을 끼워넣는 이유는 i-1, j-1같은 연산이 필요한데 오류가 나지 않게 하기위해
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
public class D08_LCS {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		char[] A = ("0" + sc.readLine()).toCharArray();
		char[] B = ("0" + sc.readLine()).toCharArray();
		int sizeA = A.length;
		int sizeB = B.length;
		
		int[][] dp = new int[sizeA][sizeB];
		int max;
		int result = 0;
		for(int i = 1; i < sizeA; i++) {
			// 이 max는 dp[i][j]중 가장 큰 값을 찾기 위함.
			// +1이 되는 것만 신경쓰면 됨
			max = 0;
			for(int j = 1; j < sizeB; j++) {
				// 현재 A[i] B[j]가 같으면 dp[i-1][j-1]에 추가함
				if(A[i] == B[j]) {
					max = dp[i - 1][j - 1] + 1;
					dp[i][j] = max;
				} else {
					// 다르면 dp[i][j-1] dp[i-1][j] 중 최대값이 곧 dp[i][j]가 됨
					// 왜냐하면 추가할게 없기 때문
					dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
				}
			}
			result = Math.max(max, result);
		}
		System.out.println(result);
	}
	
}
