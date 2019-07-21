package 다이나믹_프로그래밍_;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/************
 * <주소>		: https://www.acmicpc.net/problem/11052
 * **********
 * <해결방안>	: 
 * 
 * - 이 알고리즘은 내가 한 loop버전보다 빠름
 * - F(n)은 n개의 붕어빵을 팔 때 남길 수 있는 최대 이익
 *   
 *   F(n)이 최대인 경우는
 *   1. 1세트로 모두 판 경우
 *   2. 이전에 구했던 값들을 이용해 두 개씩 조합하는 경우
 *   
 *   k = 0 ~ n/2
 *   F(n) = bread[n] 이라고 가정한 뒤
 *   F(n) = F(n - k) + F(k) 중 최대값
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class D04_붕어빵_판매하기_loop {

	public static void main(String[] args) throws IOException {
        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
         
        int [] dp = new int [N+1];
         
        String [] str = br.readLine().split(" ");
        int [] bread = new int [N];
        for(int i = 0; i < N; i++){
            bread[i] = Integer.parseInt(str[i]);            
        }
        dp[1] = bread[0];
         
        for(int i = 2; i <= N; i++){
        	// 일단 max는 빵을 1세트만 파는 것으로 가정
            int max = bread[i-1];
            // 반만 검사해도 됨 (dp를 또 한번 이용하니까)
            for(int j = 1; j <= i/2; j++){
                max = dp[i-j] + dp[j] > max ? dp[i-j] + dp[j] : max;
            }
            dp[i] = max;
        }
        System.out.println(dp[N]);
    }

}
