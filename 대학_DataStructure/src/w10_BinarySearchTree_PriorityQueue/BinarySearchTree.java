package w10_BinarySearchTree_PriorityQueue;
/**
 * <주의>
 * 1. Node의 parent를 조작하는 부분은 findLeastLargest를 구현하기 위해
 *    parent를 추가한 것이므로 없어도 됨.
 * 
 * <remove 메소드>
 * 1. 우선 삭제할 원소를 재귀적으로 찾음
 * 2. 만약 자식이 한 개(LT, RT 중 하나만 있음)면 그냥 삭제할 자리에 붙여넣음
 * 3. 만약 자식이 두 개라면,
 *    remove node보다는 작지만, 그 중에서 가장 큰 node를 찾아야함.
 *    biggest node를 찾아 그것을 remove node에 붙여넣고,
 *    biggest node자리에는 biggest node.left를 채워넣으면 됨
 *    (biggest node는 그것의 RT가 없으니 그냥 node.left를 붙여넣어도 됨)
 *    (biggest node는 node.right가 없을 때까지 찾아가면 됨)
 * 
 * @author LENOVO
 *
 * @param <E>
 */
public class BinarySearchTree<E extends Comparable> {
	
	protected Node<E> root; // tree의 root 노드
	protected boolean addReturn; // add 메소드 실행 후 추가하려는 값이 tree에 없어
								 // 제대로 삽입되었는지?
	protected E deleteReturn; // delete 메소드 실행 후 delete 된 값 (delete 실패시 null)
	
	/**
	 * 구성자
	 */
	public BinarySearchTree(){
		root = null;
	}
	
	/**
	 * 구성자
	 * 
	 * @param root root노드
	 */
	protected BinarySearchTree(Node<E> root){
		this.root = root;
	}	
	
	
	/**
	 * 구성자
	 * @param data root의 data
	 * @param leftTree 왼쪽 트리
	 * @param rightTree 오른쪽 트리
	 */
	public BinarySearchTree(E data, BinarySearchTree<E> leftTree, BinarySearchTree<E> rightTree){
		root = new Node<E>(data);
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
	
	/**
	 * root 노드를 반환
	 * 
	 * @return root
	 */
	public Node<E> getRoot() {
		return root;
	}
	
	/**
	 * 삽입
	 * 
	 * @param data 삽입할 data
	 */
	public void add(E data)
    {
        root = insert(root, data, null);
    }

	/**
	 * 재귀를 통해 삽입
	 * 
	 * @param node 재귀 중 현재 노드
	 * @param data 삽입할 data
	 * @return 현재 재귀 호출 전 node
	 */
    private Node<E> insert(Node<E> node, E data, Node<E> prev)
    {
    	if(node == null) {
    		addReturn = true;
    		Node<E> newNode = new Node<E>(data);
    		newNode.parent = prev;
    		return newNode;
    	} else if(data.compareTo(node.data) == 0) {
    		addReturn = false;
    		return node;
    	} else if(data.compareTo(node.data) < 0) {
    		node.left = insert(node.left, data, node);
    		return node;
    	} else {
    		node.right = insert(node.right, data, node);
    		return node;
    	}
    }
    
    /**
     * 삭제
     * 
     * @param data 삭제할 data
     */
    public void delete(E data) {
    	delete(root, data);
    }
    
    /**
     * 재귀를 통해 삭제
     * 
     * @param node 현재 node
     * @param item 삭제할 data
     * @return 현재 재귀 작업 후 이전 재귀 작업 때 node의 left나 right으로 사용될 node
     */
    private Node<E> delete(Node<E> node, E item) {
    	// 끝까지 찾아봤으나 없음
    	if(node == null) {
    		deleteReturn = null;
    		return node;
    	}
    	int comp = item.compareTo(node.data);
    	// 왼쪽에 있으면 왼쪽 트리에 재귀호출
    	if(comp < 0) {
    		node.left = delete(node.left, item);
    		return node;
    	} else if(comp > 0) {
    		node.right = delete(node.right, item);
    		return node;
    	} else {
    		// 지우려는 노드를 찾음
    		deleteReturn = node.data;
    		if(node.left == null) {
    			// 왼쪽 트리가 비어있으면 오른쪽 트리를 그냥 갖다붙이면 됨
    			if(node.right != null) {
    				node.right.parent = node.parent;
    			}
    			return node.right;
    		} else if(node.right == null) {
    			// 오른쪽 트리가 비어있으면 왼쪽 트리를 갖다붙이면 됨
    			if(node.left != null) {
    				node.left.parent = node.parent;
    			}
    			return node.left;
    		} else {
    			// findLargestChild 호출 시
    			// node.left.right == null일 경우 오류가 나니까 이건 따로 처리
    			if(node.left.right == null) {
    				node.data = node.left.data;
    				if(node.left.left != null) {
    					node.left.left.parent = node;
    				}
    				node.left = node.left.left;
    				return node;
    			} else {
    				node.data = findLargestChild(node.left);
    				return node;
    			}
    		}
    	}
    }
    
    /**
     * parent node에서 시작해 가장 큰 node의 값을 반환
     * 단, 최초 parent의 right 노드가 null이 아니어야 함
     * 
     * @param parent 현재 node
     * @return 최초 parent부터 시작해 가장 큰 값
     */
    private E findLargestChild(Node<E> parent) {
    	if(parent.right.right == null) {
    		// parent.right가 최고 큰 node인 경우
    		// return parent.right의 data
    		// parent.right는 없애고 그 자리에 right의 left를 붙임 
    		E returnVal = parent.right.data;
    		if(parent.right.left != null) {
    			parent.right.left.parent = parent;
    		}
    		parent.right = parent.right.left;
    		return returnVal;
    	} else {
    		return findLargestChild(parent.right);
    	}
    }
	
    /**
     * data보다 크지만, 그것들 중 가장 작은 값을 찾아 출력
     * 
     * @param data 비교대상 data
     */
    public void findLeastLargest(E data) {
    	System.out.println(data + ": " + findLeastLargest(root, data));
    }
    
    /**
     * 트리에서 item의 값보다는 크지만, 그 중 가장 작은 값을 반환 
     * 
     * item이 node에 있는지 재귀호출을 통해 찾은 후
     * 찾은 node의 right가 있으면 right의 자손 중 가장 작은 값을 반환하고
     * 아니라면 node의 조상 중 node보단 크지만 가장 작은 값을 반환한다.
     * 
     * @param node 현재 node
     * @param item 찾으려는 값
     * @return item의 값보다는 크지만, 그 중 가장 작은 값
     *         만약 data를 가진 node가 없거나, data가 tree 중 가장 큰 값이면 null 반환
     */
    private E findLeastLargest(Node<E> node, E item) {
    	// 찾아봤으나 data를 가진 node가 없다면 null 반환
    	if(node == null) {
    		return null;
    	}
    	
    	int comp = item.compareTo(node.data);
    	if(comp < 0) {
    		// 찾으려는 data가 left tree에 있으니까 left를 인자로 재귀호출
    		return findLeastLargest(node.left, item);
    	} else if(comp > 0) {
    		// 찾으려는 data가 right tree에 있으니까 right을 인자로 재귀호출
    		return findLeastLargest(node.right, item);
    	} else {
    		// item이 있는 node를 찾음
    		
    		if(node.right != null) {
    			// node.right이 있다면 node.right의 자손 중 가장 작은 값을 반환
    			return findLeast(node.right);
    		} else {
    			// 아니라면
    			// node의 조상들 중 node보다 크지만 가장 작은 값을 반환
    			return findLeastLargerAncestor(node);
    		}
    	}
    }
    
    /**
     * node의 자손들 중 가장 작은 자손의 값을 반환.
     * 
     * node.left가 있을 경우 findLeast(node.left)를 계속 재귀호출 하고
     * node.left가 없을 때 까지 내려온 다음 그 data를 반환
     * 
     * @param node 현재 노드
     * @return node의 자손 중 가장 작은 자손의 값
     */
    private E findLeast(Node<E> node) {
    	if(node == null) {
    		return null;
    	}
    	
    	// node.left가 있다면 (더 작은 값이 있다면)
    	if(node.left != null) {
    		// node.left를 인자로 재귀호출
    		return findLeast(node.left);
    	} else {
    		// 아니라면 node가 가장 작은 값을 가지고 있으므로 그 data를 반환
    		return node.data;
    	}
    }
    
    /**
     * node에서부터 거슬러 올라가며
     * 그것보다 큰 최초의 값을 반환
     * 
     * 현재 node가 parent의 오른쪽에 붙어있다면 node.parent를 인자로 계속 재귀호출하고
     * 왼쪽에 붙어있다면 parent.data를 반환한다.
     * 
     * @param node 현재 노드
     * @return node의 조상 중 node보다 큰 최초의 값
     */
    private E findLeastLargerAncestor(Node<E> node) {
    	// parent가 null이면 root까지 가봤는데 더 큰 data가 없으니까
    	// return null
    	if(node.parent == null) {
    		return null;
    	}
    	
    	if(node.parent.right.equals(node)) {
    		// node가 parent의 오른쪽에 붙어있다면 다시 재귀
    		return findLeastLargerAncestor(node.parent);
    	} else {
    		// node가 parent의 왼쪽에 붙어있다면 최초로 큰 값이 나온거니까
    		// return parent.data
    		return node.parent.data;
    	}
    }
    
    /**
     * root의 data를 반환
     * @return root의 data
     */
	public E getData(){
		return root.getData();
	}
	
	/**
	 * tree를 inorder 방식으로 순회하며 출력
	 */
	public void inorder()
    {
        inorder(root);
    }
	/**
	 * tree를 inorder 방식으로 재귀를 통해 순회하며 출력
	 * @param r
	 */
    private void inorder(Node r)
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
    private void preorder(Node r)
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
    private void postorder(Node r)
    {
    	if (r != null)
        {
            postorder(r.getLeft());
            postorder(r.getRight());
            System.out.print(r.getData() +" ");
        }
    } 
}