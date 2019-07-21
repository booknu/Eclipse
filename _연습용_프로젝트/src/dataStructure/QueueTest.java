package dataStructure;

public class QueueTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Queue<Integer> q = new Queue<Integer>();
		en(q, 1);
		en(q, 2);
		en(q, 3);
		en(q, 4);
		de(q);
		de(q);
		de(q);
		en(q, 5);
		en(q, 6);
		en(q, 7);
		q.print();
		en(q, 8);
		q.print();
		while(!q.isEmpty())
			de(q);
	}

	public static void de(Queue<Integer> q) throws Exception{
		System.out.println("dequeue: " + q.dequeue());
	}
	public static void en(Queue<Integer> q, int item){
		q.enqueue(item);
		System.out.println("enqueue: " + item);
	}
}
