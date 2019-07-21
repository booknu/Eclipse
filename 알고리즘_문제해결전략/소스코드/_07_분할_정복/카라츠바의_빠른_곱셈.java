package _07_분할_정복;
import java.util.ArrayList;
import java.util.Vector;

/************
 * <주소>		: p183
 * **********
 * <해결방안>	:
 * 
 * - 매우 큰 수 (수만자리)끼리의 곱셈을 수행할 때 사용된다.
 * 
 * - 매우 큰 수는 int[] 형태로 저장되어 있는데,
 *   [아랫자리][그다음]...[최상위자리] 같이 아래자리부터 저장
 *   이것은 A[i]에 주어진 자릿수 크기를 10^i로 쉽게 구할수 있기 때문
 * 
 * 
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 카라츠바의_빠른_곱셈 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	public static void normalize(Vector<Integer> num) {
		num.add(0);
		
		for(int i = 0; i < num.size(); i++) {
			// 자릿수를 빌려야 한다면
			if(num.get(i) < 0) {
				int borrow = (Math.abs(num.get(i)) + 9) / 10;	// -3412 같이 여러자리 숫자일 수도 있음
				num.set(i + 1, num.get(i + 1) - borrow); // 윗 자리에서 빌림
				num.set(i, num.get(i) + borrow * 10); // 아랫 자리에 추가
			} else {
				// 올림수가 있을 경우
				num.set(i + 1, num.get(i) / 10); 
				num.set(i, num.get(i) % 10);
			}
		} 
		
		// 최상위 숫자들이 0이면 당연히 지워야 됨
		while(num.size() > 1 && num.get(num.size() - 1) == 0) num.remove(num.size());
	}
	
	/**
	 * 초등학교 곱셈 방식으로 곱셈
	 * @param a
	 * @param b
	 * @return
	 */
	public static Vector<Integer> multiply(Vector<Integer> a,
			Vector<Integer> b) {
		Vector<Integer> ret = new Vector<Integer>(a.size() + b.size() + 1);
		for(int i = 0; i < a.size(); i++) {
			for(int j = 0; j < a.size(); j++) {
				ret.set(i + j, ret.get(i + j) + a.get(i) * b.get(j)); 
			}
		}
		
		normalize(ret);
		return ret;
	}

	/**
	 * a += b * (10^k)
	 * @param a
	 * @param b
	 * @param k
	 */
	public static void addTo(Vector<Integer> a, Vector<Integer> b, int k) {
		
	}
	
	/**
	 * a -= b
	 * @param a
	 * @param b
	 */
	public static void subFrom(Vector<Integer> a, Vector<Integer> b) {
		
	}

	/**
	 * 카라츠바의 곱셈 방식으로 a * b를 구한다.
	 * 분할정복으로 
	 * a = a1 * 10^128 + a0
	 * b = b1 * 10^128 + b0
	 * a * b
	 * = a1*b1*10^256 + (a0*b1 + a1*b0)*10^128 + a0*b0
	 * ==> 이 방법은 곱셈을 4번 한다.
	 * 
	 * z2 = a1 * b1
	 * z0 = a0 * b0
	 * z1 = (a0 + a1) * (b0 + b1) - z0 - z2
	 * 
	 * 선형시간인 덧셈을 제외하고 곱셈을 3번 밖에 하지 않는다.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Vector<Integer> karatsuba(Vector<Integer> a, Vector<Integer> b) {
		int an = a.size();
		int bn = b.size();
		// a가 항상 b보다 길어야 한다.
		if(an < bn) {
			return karatsuba(b, a);
		}
		// 기저 사례: a나 b가 비어있음
		if(an == 0 || bn == 0) {
			return new Vector<Integer>();
		}
		// 기저 사례: a가 비교적 짧은 경우 O(n^2) 곱셈으로 변경
		if(an <= 50) {
			return multiply(a, b);
		}
		int half = an / 2;
		// a와 b를 반반씩 분리한다.
		Vector<Integer> a0 = new Vector<Integer>(); // 정확한 구현 안 함
		Vector<Integer> a1 = new Vector<Integer>();
		Vector<Integer> b0 = new Vector<Integer>();
		Vector<Integer> b1 = new Vector<Integer>();
		
		// z2 = a1 * b1
		Vector<Integer> z2 = karatsuba(a1, b1);
		// z0 = a0 * b0
		Vector<Integer> z0 = karatsuba(a0, a0);
		// a0 = a0 + a1, b0 = b0 + b1
		addTo(a0, a1, 0);
		addTo(b0, b1, 0);
		// z1 = a0 * b0 - z0 - z2
		Vector<Integer> z1 = karatsuba(a0, b0);
		subFrom(z1, z0);
		subFrom(z1, z2);
		// ret = z0 + z1 * 10^half + z2 * 10^full
		Vector<Integer> ret = new Vector<Integer>();
		addTo(ret, z0, 0);
		addTo(ret, z1, half);
		addTo(ret, z2, half + half);
		return ret;
	}
}
