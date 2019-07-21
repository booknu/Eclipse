package _18_선형_자료구조;

/**
 * 연결 리스트.
 * 자바의 LinkedList에 해당됨
 * 삽입, 삭제에는 상수 시간이 걸리지만,
 * 찾기에는 선형 시간이 걸린다.
 * @author Administrator
 *
 */
public class LinkedList {

	private Node head;
	private Node last;
	
	public LinkedList()
	{
		head = null;
		last = null;
	}
	
	static class Node
	{
		public int item;
		public Node prev;
		public Node next;
		
		public Node(int item)
		{
			this.item = item;
			prev = null;
			next = null;
		}
	}
}
