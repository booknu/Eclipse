package 다이나믹_프로그래밍;
import java.util.Scanner;

/************
 * <주소>		: https://www.acmicpc.net/problem/2133
 * **********
 * <해결방안>	: 
 * 
 * (설명 사진 참조)
 * 
 * (아이디어)
 * 블록 타입은
 * 1. 길이 = 2인 3가지 경우 (기본 블럭)
 * 2. 길이 >= 4인 2가지 경우 (긴 블럭) ==> N = 4, 6, 8, 10... 처럼 여러개 나올 수 있음
 * 
 * (전개)
 * F(N)을 구하려면
 * 1. F(N - 2)에 기본 블럭 3가지의 경우를 추가한다.
 * 2. F(N - 4, 6, 8, 10...N)에 긴 블럭 2가지 경우를 추가한다. 
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 처음에는 1x2타일 (가로타일)이
 *   위, 아래, 目 형태로 있는 경우 3가지를 나눠서
 *   가능한 조합들을 구하여 (ex> 위2, 아래4, 目6, 위2)
 *   위, 아래인 경우에는 2xN타일처럼 구하고
 *   目 형태인 경우에는 1로
 *   구하려고 했는데 잘 되지 않았음
 *   
 * - 다른 사람의 소스를 참고해서 아이디어를 떠올림
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_타일_채우기 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();

		// 홀수이면 0가지
		if((N & 1) > 0) {
			System.out.println(0);
			System.exit(0);
		}

		int [] dp = new int [N + 1];

		dp[0] = 1;
		dp[2] = 3;
		
		for(int i = 2; i <= N; i += 2) {
			dp[i] = dp[i - 2] * 3; // 일단 N = 2인 블럭을 채워서 만들 수 있는 가지수를 세어봄
			// 길이가 4 이상인 긴 하나의 블럭을 채워서 만들 수 있는 가지수를 세어봄
			for(int j = 4; j <= i; j += 2) {
				dp[i] += dp[i - j] * 2;
			}
		}
		
		System.out.println(dp[N]);
	}

}
