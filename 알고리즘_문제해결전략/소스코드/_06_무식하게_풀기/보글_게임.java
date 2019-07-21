package _06_무식하게_풀기;

import java.util.Scanner;

/**
 * --------해결 방안--------
 * 완전 탐색
 * 탐색 수: 8^(N-1)
 * 시간 복잡도: O(8^N)
 * 
 * @author Administrator
 *
 */

/**
 * --------오답 노트--------
 * - 다음에 갈 좌표 목록을 dx, dy 배열로 정리함으로서 코드와 변수로 분리함
 * 
 * @author Administrator
 *
 */
public class 보글_게임 {
	
	static char board[][] = new char[5][5];
	// 이것을 통해 코드가 간결해짐 (p50)
	static final int dx[] = {-1, -1, -1, 1, 1, 1, 0, 0};
	static final int dy[] = {-1, 0, 1, -1, 0, 1, -1, 1};
	
	static boolean hasWord(int y, int x, String word) {
		// 기저 사례들
		if(y < 0 || x < 0) return false;	// 범위에서 벗어난 경우 false (코드가 간결해짐)
		if(word.charAt(0) != board[y][x]) return false;
		if(word.length() == 1) return true;

		// 인접한 8개의 칸을 검사한다.
		for(int direction = 0; direction < 8; direction++) {
			int nextY = y + dy[direction], nextX = x + dx[direction];
			if(hasWord(nextY, nextX, word.substring(1)))
				return true;
		}
		return false;
	}

}
