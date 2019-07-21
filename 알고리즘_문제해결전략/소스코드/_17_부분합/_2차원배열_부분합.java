package _17_부분합;

public class _2차원배열_부분합 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [][] rectangle = {
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5},
				{1, 2, 3, 4, 5}
		};
	}
	
	public static int gridSum(int[][] psum, int y1, int x1, int y2, int x2)
	{
		int sum = psum[y2][x2];	//	0 에서부터 직사각형
		if(y1 > 0) sum -= psum[y1 - 1][x2]; // sum 에서 빼야할 위쪽 직사각형
		if(x1 > 0) sum -= psum[y2][x1 - 1]; // sum 에서 빼야할 왼쪽 직사각형
		if(y1 > 0 && x1 > 0) sum += psum[y1 - 1][x1 - 1]; // 위 두 개가 겹치는 부분은 다시 더함
		return sum;
	}

}
