package chap02_List;

public class DoubleLinkedList_오류수정_테스트 {
	public static void removeFirstTest() {
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();

		for(int i = 1; i <= 4; i++) {
			list.addFirst(i);
			list.traverse();
			list.traverse_r();
			System.out.println("size  : " + list.size());
			System.out.println("--------");
		}
		for(int i = 4; i > 0; i--) {
			list.remove(0);
			list.traverse();
			list.traverse_r();
			System.out.println("size  : " + list.size());
			System.out.println("--------");
		}
	}
	
	public static void removeLastTest() {
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
		
		for(int i = 1; i <= 4; i++) {
			list.addFirst(i);
			list.traverse();
			list.traverse_r();
			System.out.println("size  : " + list.size());
			System.out.println("--------");
		}
		for(int i = 3; i >= 0; i--) {
			list.remove(i);
			list.traverse();
			list.traverse_r();
			System.out.println("size  : " + list.size());
			System.out.println("--------");
		}
	}
	
	public static void removeMidTest() {
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
		
		for(int i = 1; i <= 4; i++) {
			list.addFirst(i);
			list.traverse();
			list.traverse_r();
			System.out.println("size  : " + list.size());
			System.out.println("--------");
		}
		for(int i = 2; i >= 0; i--) {
			list.remove(i);
			list.traverse();
			list.traverse_r();
			System.out.println("size  : " + list.size());
			System.out.println("--------");
		}
	}
	
	public static void addTest() {
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
		
		list.add(0, 0);
		for(int i = 0; i < 4; i++) {
			list.add(i, i + 1);
			list.traverse();
			list.traverse_r();
			System.out.println("size  : " + list.size());
			System.out.println("--------");
		}
	}
}
