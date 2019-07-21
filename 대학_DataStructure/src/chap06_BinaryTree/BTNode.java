package chap06_BinaryTree;
public class BTNode<E> {
	protected BTNode<E> left, right;
    protected E data;	    

    /* Constructor */
    public BTNode()
    {
        left = null;
        right = null;
        data = null;
    }
    /* Constructor */
    public BTNode(E n)
    {
        left = null;
        right = null;
        this.data = n;
    }
    public BTNode(E data, E left, E right) {
    	this.left = new BTNode<E>(left);
        this.right = new BTNode<E>(right);
        this.data = data;
    }
    public E getData(){
    	return data;
    }
    
    public BTNode<E> getRight(){
    	return right;
    }
    
    public BTNode<E> getLeft(){
    	return left;
    }
    /**
     * Node의 오른쪽, 왼쪽 노드를 생성
     * @param left
     * @param right
     */
    public void set(E left, E right) {
    	this.left = new BTNode<E>(left);
    	this.right = new BTNode<E>(right);
    }
}
