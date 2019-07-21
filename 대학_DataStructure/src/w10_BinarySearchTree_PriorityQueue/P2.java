package w10_BinarySearchTree_PriorityQueue;
public class P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinarySearchTree<String> tree = new BinarySearchTree<String>();
		set(tree);
		tree.delete("built");
		print(tree);
		System.out.println();
		
		tree = new BinarySearchTree<String>();
		set(tree);
		tree.delete("crumpled");
		print(tree);
		System.out.println();
		
		tree = new BinarySearchTree<String>();
		set(tree);
		tree.delete("malt");
		print(tree);
		System.out.println();
		
		tree = new BinarySearchTree<String>();
		set(tree);
		tree.delete("tossed");
		print(tree);
		System.out.println();
		
		tree = new BinarySearchTree<String>();
		set(tree);
		tree.delete("lay");
		tree.delete("house");
		tree.delete("cow");
		tree.delete("ate");
		tree.delete("all");
		tree.delete("and");
		tree.delete("cock");
		print(tree);
		System.out.println();
		tree.findLeastLargest("built");
		
	}
	
	public static void set(BinarySearchTree<String> t) {
		t.add("lay");
		
		t.add("house");
		t.add("rat");
		
		t.add("cow");
		t.add("jack");
		t.add("milked");
		t.add("that");
		
		t.add("built");
		t.add("dog");
		t.add("is");
		t.add("killed");
		t.add("malt");
		t.add("priest");
		t.add("shorn");
		t.add("tossed");
	
		t.add("and");
		t.add("cock");
		t.add("crumpled");
		t.add("forlorn");
		t.add("in");
		t.add("kept");
		t.add("kissed");
		t.add("maiden");
		t.add("man");
		t.add("morn");
		t.add("shaven");
		t.add("tattered");
		t.add("this");
		t.add("with");
		
		t.add("all");
		t.add("ate");
		t.add("cat");
		t.add("corn");
		t.add("crowed");
		t.add("farmer");
		t.add("horn");
		t.add("married");
		t.add("sowing");
		t.add("the");
		t.add("torn");
		t.add("waked");
		t.add("worried");
	}
	
	public static void print(BinarySearchTree<String> tree) {
		System.out.println("--- pre ---");
		tree.preorder();
		System.out.println();
		System.out.println("--- in ---");
		tree.inorder();
		System.out.println();
		System.out.println("--- post ---");
		tree.postorder();
		System.out.println();
	}

}
