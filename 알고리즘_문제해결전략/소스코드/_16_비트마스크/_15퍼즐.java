package _16_비트마스크;
import java.util.Scanner;

/**
 * 4 X 4 퍼즐
 * index: 0 ~ 15
 * value: 0 ~ 15
 * @author 418-23
 *
 */
public class _15퍼즐 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cs = new Scanner(System.in);
		
		long arr = 0;
		
		boolean done = false;
		System.out.println("범위를 벗어나는 수 입력시 종료합니다.");
		while(!done)
		{
			System.out.print("index, value (띄어쓰기로 구분): ");
			int index = cs.nextInt();
			long val = cs.nextLong();
			
			if(index < 0 || index >= 16 || val < 0 || val >= 16)
				done = true;
			else
				arr = set(arr, index, val);
		}
		print(arr);
		
		cs.close();
	}

	/* & index는 맨 끝 4자리 bit만 보게 함
	 * index << 2는 index * 4
	 * 
	 * index * 4 만큼 arr을 오른쪽으로 움직임
	 * 그 중 맨 끝 4자리 bit만 봄
	 */
	public static long get(long arr, int index)
	{
		return (arr >>> (index << 2)) & 15;
	}
	
	/* ~(15L << (index << 2))은 값을 넣을 자리를 비우는데 이용된다
	 * => ex) index = 2: ... 1111 0000 1111 1111
	 * 
	 *  이걸 arr과의 & 연산을 통해 그 자리를 비움
	 *  
	 *  (value << (index << 2)) 는 value를 그 자리까지 이동
	 */
	public static long set(long arr, int index, long value)
	{
		return arr & ~(15L << (index << 2)) | (value << (index << 2));
	}
	
	public static void print(long arr)
	{
		for(int i = 0; i < 16; i++)
		{
			if(i % 4 == 0 && i != 0)
				System.out.println();
			System.out.print(get(arr, i) + "\t");
		}
	}
}
