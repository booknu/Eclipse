package _07_분할_정복;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/************
 * <주소>		: p189
 * **********
 * <해결방안>	: 
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 쿼드_트리_뒤집기 {

	static String compressed;
	static char[][] decompressed;
	static int N;
	static int MAXSIZE = (int)Math.pow(2, 20);
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		int C = Integer.parseInt(sc.readLine());
		for(int testCase = 0; testCase < C; testCase++) {
			compressed = sc.readLine();
//			decompressed = new char[MAXSIZE][MAXSIZE];
			N = compressed.length();
			System.out.println(reverse(new Iterator()));
		}
	}
	
	/**
	 * 압축을 풀어 char[][] 형태로 보여줌
	 * 하지만 압축이 풀린 char[][]은 최대 2^20 * 2^20이 될 수 있으므로
	 * 메모리를 너무 많이 차지하여 현실적이지 않은 방법이다.
	 * 
	 * @param it 반복자 (현재 읽은 String의 index)
	 * @param y 
	 * @param x
	 * @param size 크기
	 */
	public static void decompress(Iterator it, int y, int x, int size) {
		if(it.index == N) {
			return;
		}
		char head = compressed.charAt(it.index);
		it.index++;
		
		if(head == 'b' || head == 'w') {
			for(int dy = 0; dy < size; dy++) {
				for(int dx = 0; dx < size; dx++) {
					decompressed[y + dy][x + dx] = head;
				}
			}
		} else {
			int half = size / 2;
			decompress(it, y, x, half);
			decompress(it, y, x + half, half);
			decompress(it, y + half, x, half);
			decompress(it, y + half, x + half, half);
		}
	}
	
	/**
	 * 압축을 풀자마자 다시 압축을하여 뒤집는 방식.
	 * 압축이 풀린 4지점을 각각 뒤집는데,
	 * 기저값으로 모든 점이 w, b인 것은 뒤집어도 같은 결과임.
	 * 따라서 p194쪽에 있는 것처럼 각각을 뒤집은 다음 다시
	 * 재조합 하면 됨
	 * 
	 * @param it 반복자
	 * @return 뒤집힌걸 압축한 결과
	 */
	public static String reverse(Iterator it) {
		if(it.index == N) {
			return "";
		}
		char head = compressed.charAt(it.index);
		it.index++;
		// 전체가 b, w인 경우에는 뒤집어도 b, w
		if(head == 'b' || head == 'w') {
			return head + "";
		}
		String upperLeft = reverse(it);
		String upperRight = reverse(it);
		String lowerLeft = reverse(it);
		String lowerRight = reverse(it);
		// 각각을 재조합함
		return "x" + lowerLeft + lowerRight + upperLeft + upperRight;
	}
	
	/**
	 * C++에 있는 String iterator 기능이 없어서 비슷한걸 직접 만듬
	 * @author LENOVO
	 *
	 */
	static class Iterator {
		int index = 0;
	}
}
