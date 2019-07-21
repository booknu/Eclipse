package _분류없음;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ListIterator;

/************
 * <주소>		: https://www.acmicpc.net/problem/1406
 * **********
 * <해결방안>	: 
 * 
 * **********
 * <오답노트>	: 
 * 1. 커서 위치가 맨 앞에 있는 경우를 생각 못함.
 * 2. ListIterator 사용하면 편함
 * 
 * **********
 * @author LENOVO
 *
 */
public class F00_에디터 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		String origin = sc.readLine();
		Node head = null;
		Node tail = null;
		Node pointer = head;
		for(int i = 0; i < origin.length(); i++) {
			// insert last
			if(i == 0) {
				head = new Node(origin.charAt(i));
				pointer = head;
				tail = pointer;
			} else {
				pointer.next = new Node(origin.charAt(i));
				pointer.next.prev = pointer;
				pointer = pointer.next;
				tail = pointer;
			}
		}
		
		int questNum = Integer.parseInt(sc.readLine());
		// 커서는 항상 pointer의 오른쪽에 위치
		// 커서가 맨 앞이면 null
		pointer = tail;
		for(int i = 0; i < questNum; i++) {
			String cmd = sc.readLine();
			switch(cmd.charAt(0)) {
				// move pointer to prev
				case 'L':
					if(pointer != null) {
						pointer = pointer.prev;
					}
					break;
				// move pointer to next
				case 'D':
					if(pointer != tail) {
						if(pointer == null) {
							pointer = head;
						} else {
							pointer = pointer.next;
						}
					}
					break;
				// delete previous character of the pointer
				case 'B':
					if(pointer == tail) {
						// remove last
						if(tail != null) {
							if(tail == head) {
								tail = head = null;
							} else {
								tail = tail.prev;
								tail.next = null;
							}
							pointer = tail;
						}
					} else if(pointer != null) {
						pointer.next.prev = pointer.prev;
						if(pointer.prev != null) {
							pointer.prev.next = pointer.next;
						} else {
							head = pointer.next;
						}
						pointer = pointer.prev;
					}
					break;
				// add a character after the pointer
				case 'P':
					if(head == null && tail == null) {
						head = tail = new Node(cmd.charAt(2), null, head);
						pointer = head;
					} else if(pointer == null) {
						// add first
						head = new Node(cmd.charAt(2), null, head);
						if(head.next != null) {
							head.next.prev = head;
						}
						pointer = head;
					} else if(pointer == tail) {
						// add last
						tail = new Node(cmd.charAt(2), tail, null);
						if(tail.prev != null) {
							tail.prev.next = tail;
						}
						pointer = tail;
					} else {
						Node added = new Node(cmd.charAt(2), pointer, pointer.next);
						pointer.next = added;
						pointer = pointer.next;
						if(added.next != null) {
							added.next.prev = added;
						}
					}
					break;
			}
			
//			print(head);
//			printNode(head, tail);
//			System.out.println("pointer: " + (pointer == null? null : pointer.data));
//			System.out.println("[" + (pointer == null? "first" : "") + "]");
//			System.out.println();
		}
		print(head);
	}
	
	public static void printNode(Node head, Node tail) {
		Node curr = head;
		while(curr != null) {
			System.out.print("(" + (curr.prev == null? null : curr.prev.data) + ", " + curr.data
					+ ", " + (curr.next == null? null : curr.next.data) + ")");
			curr = curr.next;
		}
		System.out.println();
		curr = tail;
		while(curr != null) {
			System.out.print("(" + (curr.prev == null? null : curr.prev.data) + ", " + curr.data
					+ ", " + (curr.next == null? null : curr.next.data) + ")");
			curr = curr.prev;
		}
		System.out.println();
	}
	
	public static void print(Node head) {
		Node curr = head;
		while(curr != null) {
			System.out.print(curr.data);
			curr = curr.next;
		}
		System.out.println();
	}
	
	static class Node {
		public char data;
		public Node prev;
		public Node next;
		public Node(char d) {
			data = d;
		}
		public Node(char d, Node p, Node n) {
			data = d;
			prev = p;
			next = n;
		}
	}
}
