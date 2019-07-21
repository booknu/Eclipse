package _04_알고리즘의_시간복잡도_분석;

import java.util.ArrayList;

/**
 * --------해결 방안--------
 * 가능한 모든 메뉴의 조합을 만들어 일일이 확인
 * 실행시간: 2^M * canEverybodyEat()		(지수 시간)
 * 
 * @author Administrator
 *
 */
public class 알러지가_심한_친구들 {
	
	static final int INF = 987654321;
	static int M; // 요리할 수 있는 음식의 종류 수
	
	/**
	 * 이 메뉴로 모두가 식사할 수 있는지 여부를 판단한다.
	 * @param menu	결정된 메뉴
	 * @return 식사할 수 있나?
	 */
	public static boolean canEverybodyEat(ArrayList<Integer> menu)
	{
		// 미구현
		return true;
	}
	
	/**
	 * food 번째 음식을 만들지 여부를 결정한다.
	 * @param menu 현재까지 만들어진 메뉴
	 * @param food 결정할 음식 번째
	 * @return 최종 결정된 메뉴로 만들 수 있다면: 메뉴의 수
	 * 		   없다면: INF
	 */
	public static int selectMenu(ArrayList<Integer> menu, int food){
		// 기저 사례: 끝번째 음식까지 고려했을 때
		if(food == M)
			if(canEverybodyEat(menu)) return menu.size();
			else return INF;
		
		int notAdded = selectMenu(menu, food + 1);
		menu.add(food);
		int added = selectMenu(menu, food + 1);
		
		return Math.min(notAdded, added);
	}

}
