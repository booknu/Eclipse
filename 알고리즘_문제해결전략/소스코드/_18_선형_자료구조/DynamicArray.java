package _18_선형_자료구조;

/**
 * 동적 배열.
 * 자바의 ArrayList가 이것에 해당됨
 * 지금은 배열의 원소를 int로 해놨지만, 다른 타입으로 수정 가능
 * @author Administrator
 *
 */
public class DynamicArray {

	private int[] arr;		// 실질적으로 원소가 들어가는 배열
	private int size;		// 동적 배열의 크기 (size)
	private int maxSize;	// 동적 배열의 용량 (capacity)
	
	public DynamicArray()
	{
		size = 0;
		maxSize = 1;
		arr = new int[maxSize];
	}
	
	/**
	 * 동적 배열의 용량을 늘린다.
	 * 새 배열을 만들고, 원래 배열의 원소를 복사하는 방식
	 * 선형 시간이 걸린다.
	 * @param n
	 */
	public void resize(int n)
	{
		int[] newArr = new int[n];
		maxSize = n;
		for(int i = 0; i < size; i++)
			newArr[i] = arr[i];
		arr = newArr;
	}
	
	/**
	 * 동적 배열에 원소를 추가한다.
	 * 용량만큼 꽉 찬 경우 재할당을 하는데,
	 * append 연산을 상수 시간으로 맞추기 위해
	 * 재할당시 size에 비례하도록 한다.
	 * @param data 넣을 데이터
	 */
	public void append(int data)
	{
		if(size == maxSize)
			resize(size * size);	// size에 비례해서 용량을 수정하면
		arr[size++] = data;
	}
}
