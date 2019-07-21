package chap06_BinaryTree;
public class BinaryTreeOrigin<E> {
	
	protected BTNode<E> root;
	
	public BinaryTreeOrigin(){
		root = null;
	}
	
	protected BinaryTreeOrigin(BTNode<E> root){
		this.root = root;
	}	
	
	
	/**
	 * 생성자
	 * @param data root의 data
	 * @param leftTree 왼쪽 트리
	 * @param rightTree 오른쪽 트리
	 */
	public BinaryTreeOrigin(E data, BinaryTreeOrigin<E> leftTree, BinaryTreeOrigin<E> rightTree){
		root = new BTNode<E>(data);
		if(leftTree!=null){
			root.left = leftTree.root;
		}else{
			root.left = null;
		}
		if(rightTree!=null){
			root.right = rightTree.root;
		}
		else{
			root.right = null;
		}
	}
	
	public BTNode<E> getRoot() {
		return root;
	}
	
	/**
	 * 삽입
	 * @param data
	 */
	public void insert(E data)
    {
        root = insert(root, data);
    }

	public void insert(BinaryTreeOrigin<E> another) {
		root = insert(root, another);
	}
	/**
	 * 
	 * @param node
	 * @param data
	 * @return
	 */
    public BTNode insert(BTNode node, BinaryTreeOrigin<E> another)
    {
    	if (node == null)
            node = another.root;
        else
        {
            if (node.getRight() == null)
                node.right = insert(node.right, another);
            else
                node.left = insert(node.left, another);             
        }
        return node;
    }
	/**
	 * 
	 * @param node
	 * @param data
	 * @return
	 */
    private BTNode insert(BTNode node, E data)
    {
        if (node == null)
            node = new BTNode(data);
        else
        {
            if (node.getRight() == null)
                node.right = insert(node.right, data);
            else
                node.left = insert(node.left, data);             
        }
        return node;
    }
	
	public E getData(){
		return root.getData();
	}
	public void inorder()
    {
        inorder(root);
    }
    private void inorder(BTNode r)
    {
        if (r != null)
        {
            inorder(r.getLeft());
            System.out.print(r.getData() +" ");
            inorder(r.getRight());
        }
    }
    /* Function for preorder traversal */
    public void preorder()
    {
        preorder(root);
    }
    private void preorder(BTNode r)
    {
    	if (r != null)
        {
    		System.out.print(r.getData() +" ");
            preorder(r.getLeft());
            preorder(r.getRight());
        }
    }
    /* Function for postorder traversal */
    public void postorder()
    {
        postorder(root);
    }
    private void postorder(BTNode r)
    {
    	if (r != null)
        {
            postorder(r.getLeft());
            postorder(r.getRight());
            System.out.print(r.getData() +" ");
        }
    } 
    
    public void testRight(BTNode<E> node) {
    	if(node == null) {
    		System.out.println();
    		return;
    	}
    	System.out.print(node.data + " ");
    	testRight(node.right);
    }
    public void testLeft(BTNode<E> node) {
    	if(node == null) {
    		System.out.println();
    		return;
    	}
    	System.out.print(node.data + " ");
    	testLeft(node.left);
    }
	
}