package chap02_List;

/**
 * 1. 맨 앞 삽입
 *    맨 뒤 삽입
 *    리스트가 비어있으면
 *    ==> 이것을 처리해줘야 함
 * @author LENOVO
 *
 */
public class DoubleLinkedList_오류수정 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("======= remove first test =======");
		DoubleLinkedList_오류수정_테스트.removeFirstTest();
		
		System.out.println("======= remove last test =======");
		DoubleLinkedList_오류수정_테스트.removeLastTest();

		System.out.println("======= remove mid test =======");
		DoubleLinkedList_오류수정_테스트.removeMidTest();
		
		System.out.println("======= add test =======");
		DoubleLinkedList_오류수정_테스트.addTest();
	}
}
class DoubleLinkedList<E> {
	private static class Node<E> {
		private E data;
		private Node<E> prev;
		private Node<E> next;

		private Node(E dataItem) {
			data = dataItem;
			prev = null;
			next = null;
		}

		private Node(E dataItem, Node<E> nodePrev, Node<E> nodeNext) {
			data = dataItem;
			prev = nodePrev;
			next = nodeNext;
		}

		private Node<E> getNext(){ return next; }
		private Node<E> getPrev(){ return prev; }

		private void setNext(Node<E> nextNode) {
			this.next = nextNode;
		}

		private void setPrev(Node<E> prevNode) {
			this.prev = prevNode;
		}
	}

	private Node<E> head;
	private Node<E> tail;
	private int size;
	public DoubleLinkedList(){
		head = null;
		tail = null;
		size = 0;
	}
	public boolean isEmpty(){return size==0;}
	public int size(){return size;}

	public void traverse(){
		System.out.print("trav  : ");
		Node<E> nodeRef = head;
		while(nodeRef!=null){
			if(nodeRef.next!=null){
				System.out.print(nodeRef.data);
				System.out.print(" ==> ");
			}
			else{ System.out.println(nodeRef.data); }
			nodeRef = nodeRef.next;
		}
	}

	public void traverse_r(){
		System.out.print("trav_r: ");
		Node<E> nodeRef = tail;
		while(nodeRef!=null){
			if(nodeRef.prev!=null){
				System.out.print(nodeRef.data);
				System.out.print(" ==> ");
			}
			else{ System.out.println(nodeRef.data);}
			nodeRef = nodeRef.prev;
		}
	}

	private Node<E> getNode(int index){
		Node<E> node;
		node = head;
		for(int i=0;i<index&&node!=null;i++) node = node.next;
		return node;
	}

	/**
	 * 1. 첫, 뒤 원소 삭제시 size--를 안 함.
	 * 2. 첫 번째 원소 삭제시 원소가 1개일 때 오류 발생
	 *    ==> head에서 두 번째 원소를 가져와 그것을 head로 삼고
	 *        head.prev를 null로 지정하는 방식을 선택했는데,
	 *        원소가 1개일 때는 head에서 두 번째 원소 = null 이기 때문
	 * @param index
	 * @return
	 */
	public boolean remove(int index){
		Node<E> curr = head;
		// 맨 첫 번째 원소 삭제
		if(index==0){
			if(size == 1) {
				head = null;
				tail = null;
			} else {
				head = getNode(1);
				head.prev=null;
			}
			size--;
			return true;
		}
		// 맨 뒤 원소 삭제
		else if(index==size()-1){
			tail = getNode(index-1);
			tail.next=null;
			tail.prev = getNode(index-1).getPrev();
			size--;
			return true;
		}
		else{
			for(int i=1;i<index;i++){
				if(curr.getNext() == null)
					return false;
				curr = curr.getNext();
			}
			curr.setNext(curr.getNext().getNext());
			curr.getNext().setPrev(curr);
			size--; // decrement the number of elements variable
			return true;
		}
	}

	public E get(int index){
		Node<E> node = getNode(index);
		return node.data;
	}

	public E set(int index, E newValue){
		Node<E> node = getNode(index);
		E result = node.data;
		node.data = newValue;
		return result;
	}

	/**
	 * 1. index에 따라 맨 처음 삽입, 맨 마지막 삽입을 처리해주면 오류 없어짐
	 * 2. addFirst의 기능도 포함함
	 * 
	 * @param index
	 * @param item
	 */
	public void add(int index, E item){
		// index 0번에 삽입 = 맨 처음 삽입
		if(index == 0) {
			addFirst(item);
			return;
		}
		// 맨 마지막에 삽입
		if(index >= size) {
			Node<E> node = new Node<E>(item, tail, null);
			tail.next = node;
			tail = node;
			size++;
			return;
		}

		Node<E> node;
		if(size >= 2){
			node = getNode(index-1);
			addAfter(node,item);
		} else{
			node = getNode(0);
			tail = new Node<E>(item,node,null);
			node.next = tail;
			size++;
		}
	}

	/**
	 * 1. 리스트가 비었을 때는 head와 tail에 새로운 노드를 연결
	 * 2. 아니면 새로운 노드가 prev는 null, next는 head를 가리키게 만들고,
	 *    head의 prev는 새로운 노드를 가리키게 만들어 연결관계 수정 후
	 *    head를 새로운 노드로 지정
	 * @param item
	 */
	public void addFirst(E item){
		Node<E> newNode;
		// 리스트가 비어있으면
		if(head == null) {
			// 그냥 tail과 head에 연결
			newNode = new Node<E>(item);
			tail = newNode;
			head = newNode;
			size++;
			return;
		}
		// 하나라도 있으면
		newNode = new Node<E>(item, null, head);
		head.prev = newNode;
		head = newNode;
		size++;
	}

	private void addAfter(Node<E> node, E item){
		Node<E> t = new Node<E>(item, node, node.next);
		node.next.prev = t;
		node.next = t;
		size++;
	}
}
