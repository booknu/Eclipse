package chap06_BinaryTree;
public class BinaryTree<E> {
	
	protected BTNode<E> root;
	
	public BinaryTree(){
		root = null;
	}
	
	protected BinaryTree(BTNode<E> root){
		this.root = root;
	}	
	
	
	/**
	 * 생성자
	 * @param data root의 data
	 * @param leftTree 왼쪽 트리
	 * @param rightTree 오른쪽 트리
	 */
	public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree){
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
	
	public BTNode<E> getRootNode() {
		return root;
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