package _04_알고리즘의_시간복잡도_분석;
import java.util.ArrayList;

public class 이동_평균_구하기 {
	
	/**
	 * 실수 배열 A가 주어질 때, 각 위치에서의 M 이동 평균 값을 구한다.
	 * (M 간의 이동 평균 값)
	 * i 반복문: N - M + 1 번 반복
	 * j 반복문: M 번 반복
	 * 전체: NM - M^2 + M 번 반복	==> O(NM + M^2)
	 * @param A	배열
	 * @param M	
	 * @return	
	 */
	public static ArrayList<Double> movingAverage1(double[] A, int M){
		ArrayList<Double> ret = new ArrayList<Double>();
		int N = A.length;
		// N: 배열의 크기, M: 몇 이동 평균
		for(int i = M - 1; i < N; i++)
		{
			double partialSum = 0;
			for(int j = 0; j < M; j++)
				partialSum += A[i - j];
			ret.add(partialSum / M);
		}
		return ret;
	}
	
	/**
	 * 첫 번째 for문: M - 1 번 반복
	 * 두 번째 for문: N - M + 1 번 반복
	 * 전체: N 번 반복 (선형 시간)
	 * @param A
	 * @param M
	 * @return
	 */
	public static ArrayList<Double> movingAverage2(double[] A, int M){
		ArrayList<Double> ret = new ArrayList<Double>();
		int N = A.length;
		
		double partialSum = 0;
		// 초기 psum을 만듬
		for(int i = 0; i < M - 1; i++)
			partialSum += A[i];
		
		// psum에서 +, - 를 통해 각 이동 평균을 구함
		for(int i = M - 1; i < N; i++)
		{
			partialSum += A[i];
			ret.add(partialSum / M);
			partialSum -= A[i - M + 1];
		}
		
		return ret;
	}
	
	
}
