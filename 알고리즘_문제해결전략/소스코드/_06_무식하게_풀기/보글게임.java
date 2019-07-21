package _06_무식하게_풀기;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class 보글게임 {

	static char[][] board;
	static char[] word;
	static int N;
	
	static int memo[][][];
	
	static int[] dx = {-1, 0, 1, 1, 1, 0, -1, -1};
	static int[] dy = {1, 1, 1, 0, -1, -1, -1, 0};
	
	public static void main(String[] args) throws NumberFormatException, IOException {
	
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		int C = Integer.parseInt(sc.readLine());
		for(int testCase = 0; testCase < C; testCase++) {
			board = new char[5][5];
			for(int i = 0; i < 5; i++) {
				String str = sc.readLine();
				for(int j = 0; j < 5; j++) {					
					board[i][j] = str.charAt(j);
				}
			}
			N = Integer.parseInt(sc.readLine());
			for(int loop = 0; loop < N; loop++) {
				word = sc.readLine().toCharArray();
				memo = new int[5][5][word.length];
				boolean exist = false;
				for(int i = 0; i < 5; i++) {
					for(int j = 0; j < 5; j++) {
						if(checkWord(i, j, 0)) {
							exist = true;
							break;
						}
					}
				}
				System.out.println(String.copyValueOf(word) + " " + (exist? "YES" : "NO"));
			}
			
				
		}
	}
	
	public static boolean checkWord(int y, int x, int index) {
		
		if(y < 0 || x < 0 || y >= 5 || x >= 5) {
			return false;
		}
		if(index == word.length) {
			return true;
		}
		if(board[y][x] != word[index]) {
			memo[y][x][index] = 2;
			return false;
		}
		if(memo[y][x][index] != 0)
			return memo[y][x][index] == 1;
		
		for(int i = 0; i < 8; i++) {
			int nextY = y + dy[i], nextX = x + dx[i];
			if(checkWord(nextY, nextX, index + 1)) {
				memo[y][x][index] = 1;
				return true;
			}
				
		}
		memo[y][x][index] = 2;
		return false;
	}

}
