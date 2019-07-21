package w10_BinarySearchTree_PriorityQueue;
public class Node<E> {
	protected Node<E> left, right, parent; // node의 왼쪽, 오른쪽, 부모 노드
    protected E data; // node의 data

    /* Constructor */
    public Node()
    {
        left = null;
        right = null;
        data = null;
    }
    /* Constructor */
    public Node(E n)
    {
        left = null;
        right = null;
        this.data = n;
    }
    public Node(E data, E left, E right) {
    	this.left = new Node<E>(left);
        this.right = new Node<E>(right);
        this.data = data;
    }
    public E getData(){
    	return data;
    }
    
    public Node<E> getRight(){
    	return right;
    }
    
    public Node<E> getLeft(){
    	return left;
    }
}
