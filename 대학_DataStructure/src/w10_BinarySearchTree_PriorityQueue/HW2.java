package w10_BinarySearchTree_PriorityQueue;
import java.util.Comparator;

public class HW2 {
	public static void main(String args[]) {
		// Cmp1을 사용하는 우선순위 큐 q1을 생성
		PriorityQueue<String> q1 = new PriorityQueue<String>(1, new Comparator<String>() {
			@Override
			public int compare(String a1, String a2) {
				return a1.compareTo(a2);
			}
		});
		
		// Cmp2를 사용하는 우선순위 큐 q2를 생성
		PriorityQueue<String> q2 = new PriorityQueue<String>(1, new Comparator<String>() {
			@Override
			public int compare(String a1, String a2) {
				return a2.compareTo(a1);
			}
		});
		
		// q1, q2에 a b c d e 문자열을 삽입
		q1.offer("a");
		q1.offer("c");
		q1.offer("d");
		q1.offer("b");
		q1.offer("e");
		
		q2.offer("a");
		q2.offer("c");
		q2.offer("d");
		q2.offer("b");
		q2.offer("e");
		
		// q1을 poll하며 출력
		System.out.print("q1: ");
		while(!q1.isEmpty()) {
			System.out.print(q1.poll() + " ");
		}
		System.out.println();
		
		// q2를 poll하며 출력
		System.out.print("q2: ");
		while(!q2.isEmpty()) {
			System.out.print(q2.poll() + " ");
		}
		System.out.println();
		
		// q1의 Comparator은 a, b를 비교할 때 a를 기준으로 b보다 큰지 작은지를 판단하고
		// q2의 Comparator은 a, b를 비교할 때 b를 기준으로 a보다 큰지 작은지를 판단하기 때문에
		// q1은 크기가 작은 원소가 우선순위가 큰 원소가 되게 되고 
		// q2는 크기가 큰 원소가 우선순위가 큰 원소가 되게 된다.
	}
}
