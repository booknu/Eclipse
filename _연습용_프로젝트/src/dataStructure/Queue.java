package dataStructure;

import dataStructure.Stack.Node;

public class Queue<T> {
	T[] items;
	int maxSize;
	int size;
	int front;
	int rear;
	public Queue(){
		maxSize = 5;
		size = 0;
		front = 0;
		rear = 0;
		items = (T[]) new Object[maxSize];
	}
	public boolean isEmpty(){
		return front == rear;
	}
	private boolean isFull(){
		return size >= maxSize - 1;
	}
	private void resize(){
		T[] newItems = (T[]) new Object[maxSize * 2];
		for(int i = 0; i < size; i++)
			newItems[i] = items[(front + i) % maxSize];
		System.out.println("resized");
		items = newItems;
		front = 0;
		rear = size;
		
		maxSize *= 2;
	}
	public void enqueue(T item){
		if(isFull())
			resize();
		items[rear] = item;
		rear = (rear + 1) % maxSize;
		size++;
	}
	public T dequeue() throws Exception{
		if(isEmpty())
			throw new Exception();
		T item = items[front];
		front = (front + 1) % maxSize;
		size--;
		
		return item;
	}
	public void print(){
		System.out.println("size: " + size + "   maxSize: " + maxSize + "   front: " + front + "   rear: " + rear);
		for(int i = 0; i < maxSize; i++)
			System.out.print(items[i] + " ");
		System.out.println();
	}
}
