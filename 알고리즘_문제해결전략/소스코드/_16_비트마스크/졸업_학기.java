package _16_비트마스크;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class 졸업_학기 {

	public static final int INF = 1000000;

	public static int subj, goal, semester, maxSubj;  // 정수 정보
	public static int[] preSubj, openSubj;	// 비트마스크 배열 정보
	// i 과목의 선수 과목 (각 배열의 원소는 비트마스크)
	// i 학기의 개설 과목

	public static void main(String[] args) throws FileNotFoundException {

		int[] subj_arr, goal_arr, semester_arr, maxSubj_arr;
		int[][] preSubj_arr, openSubj_arr;

		Scanner cs = new Scanner(new File("rsc/16-비트마스크/졸업_학기_input.txt"));
		int testCase = cs.nextInt();

		// testCase 별로 입력 정보를 저장할 임시 공간들
		subj_arr = new int[testCase];
		goal_arr = new int[testCase];
		semester_arr = new int[testCase];
		maxSubj_arr = new int[testCase];
		preSubj_arr = new int[testCase][];
		openSubj_arr = new int[testCase][];

		// 값 입력 받기
		for(int i = 0; i < testCase; i++)
		{
			subj_arr[i] = cs.nextInt();
			goal_arr[i] = cs.nextInt();
			semester_arr[i] = cs.nextInt();
			maxSubj_arr[i] = cs.nextInt();
			preSubj_arr[i] = new int[subj_arr[i]];
			openSubj_arr[i] = new int[semester_arr[i]];

			// preSubj[testCase] 배열의 원소에
			// j 과목에 대한 선수 과목의 집합을
			// 비트마스크 형태로 저장
			for(int j = 0; j < preSubj_arr[i].length; j++)
			{
				preSubj_arr[i][j] = 0;
				int num = cs.nextInt();
				for(int k = 0; k < num; k++)
					preSubj_arr[i][j] = preSubj_arr[i][j] | (1 << cs.nextInt());
			}
			// openSubj[testCase] 배열의 원소에
			// j 학기에 대한 개설 과목의 집합을
			// 비트마스크 형태로 저장
			for(int j = 0; j < openSubj_arr[i].length; j++)
			{
				openSubj_arr[i][j] = 0;
				int num = cs.nextInt();
				for(int k = 0; k < num; k++)
					openSubj_arr[i][j] = openSubj_arr[i][j] | (1 << cs.nextInt());
			}
		}
		cs.close();

		// TestCase 만큼 작업을 수행
		for(int i = 0; i < testCase; i++)
		{
			initializeVariables(subj_arr[i], goal_arr[i], semester_arr[i], maxSubj_arr[i],
					preSubj_arr[i], openSubj_arr[i]);
			int num = graduate(0, 0);

			if(num >= INF)
				System.out.println("IMPOSSIBLE");
			else
				System.out.println(num);
		}
	}

	/**
	 * 변수를 설정
	 * @param subj
	 * @param goal
	 * @param semester
	 * @param maxSubj
	 * @param preSubj
	 * @param openSubj
	 */
	public static void initializeVariables(int subj, int goal, int semester, int maxSubj,
			int[] preSubj, int[] openSubj)
	{
		졸업_학기.subj = subj;
		졸업_학기.goal = goal;
		졸업_학기.semester = semester;
		졸업_학기.maxSubj = maxSubj;
		졸업_학기.preSubj = preSubj;
		졸업_학기.openSubj = openSubj;
	}
	
	public static void printAll()
	{
		System.out.println(subj + " " + goal + " " + semester + " " + maxSubj);
		for(int a : preSubj)
			System.out.print(a + " ");
		System.out.println();
		
		for(int a : openSubj)
			System.out.print(a + " ");
		System.out.println("\n");
	}

	public static int graduate(int cur_seme, int taken)
	{
		if(Integer.bitCount(taken) >= goal)	return 0;	// 기저 사례: 이미 목표 과목을 들은 경우
		if(cur_seme == semester) return INF;	// 기저 사례: 학기가 모두 끝난 경우

		int canTake = (openSubj[cur_seme] & (~taken)); // 열려 있는 과목 중 듣지 않은 과목

		for(int i = 0; i < subj; i++)
			if((canTake & (1 << i)) != 0 && (preSubj[i] & taken) != preSubj[i])	// 들을 수 있는 과목 중 선수 과목을 듣지 않은 과목은 제외한다.
				canTake &= ~(1 << i);

		int semester_num = INF;
		for(int take = canTake; take > 0; take = (canTake & (take - 1)))
			if(!(Integer.bitCount(take) > maxSubj))	// take가 한 학기당 들을 수 있는 과목 수를 넘어선다면 무시
				semester_num = Math.min(semester_num, graduate(cur_seme + 1, taken | take) + 1); // 아니면 그 경우를 순회

		semester_num = Math.min(semester_num, graduate(cur_seme + 1, taken));
		return semester_num;
	}
}
