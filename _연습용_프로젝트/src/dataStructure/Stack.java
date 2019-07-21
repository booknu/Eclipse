package dataStructure;

import java.util.EmptyStackException;

public class Stack<T> {

	static class Node<T>{
		T item;
		Node<T> next;
		public Node(T item){
			this(item, null);
		}
		public Node(T item, Node<T> next)
		{
			this.item = item;
			this.next = next;
		}
	}
	
	Node<T> head;
	int size;
	public Stack(){
		head = null;
		size = 0;
	}
	
	public boolean isEmpty(){
		return head == null;
	}
	public void push(T item){
		head = new Node<T>(item, head);
		size++;
	}
	public T pop() throws EmptyStackException{
		if(isEmpty())
			throw new EmptyStackException();
		T item = head.item;
		head = head.next;
		size--;
		return item;
	}
	public T peek() throws EmptyStackException{
		if(isEmpty())
			throw new EmptyStackException();
		return head.item;
	}
}
