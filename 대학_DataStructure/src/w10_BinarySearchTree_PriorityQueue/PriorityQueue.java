package w10_BinarySearchTree_PriorityQueue;import java.util.*;

/**
 * <remove 메소드>
 * 1. leaf를 remove 할 곳에 붙여넣고 heap 규칙대로 조절하면 됨.
 * 2. 아래로 내려가면서 heap 규칙대로 조정을 하는데,
 *    가능한 child의 경우는 (none, L, LR) 이 세가지 뿐이므로
 *    none인 경우는 그냥 return을 하면 되고,
 *    L이 존재할 경우에는 L과 R 중 최소값을 취해 그 부분으로 swap하면 됨
 * @author LENOVO
 *
 * @param <E>
 */
public class PriorityQueue<E> extends AbstractQueue<E>
        implements Queue<E> {

    private ArrayList<E> theData; // 배열로 구현한 heap을 통해 우선순위큐를 구현
    Comparator<E> comparator = null; // 비교자

    public PriorityQueue() {
        theData = new ArrayList<E>();
    }

    public PriorityQueue(int cap, Comparator<E> comp) {
        if (cap < 1) {
            throw new IllegalArgumentException();
        }
        theData = new ArrayList<E>(cap + 1);
        comparator = comp;
    }

    @Override
    public boolean offer(E item) {
    	// heap에 데이터를 추가하는 방식과 같은데,
    	// heap의 leaf에 데이터를 추가한 뒤
        theData.add(item);
        // 추가된 leaf의 index를 찾아놓고
        int child = theData.size() - 1;
        // 그것의 parent의 index도 배열로 구현된 heap에서 parent를 찾는 공식에 따라 찾음
        int parent = (child - 1) / 2;
        // parent가 존재하고 (root가 아니고), parent가 child보다 클 경우
        while (parent >= 0 && compare(theData.get(parent),
                theData.get(child)) > 0) {
        	// parent와 child의 데이터를 바꾸고
            swap(parent, child);
            // child(현재 노드)를 parent로 바꾼 뒤
            child = parent;
            // 그것에 해당하는 parent index 를 찾고 다시 루프를 돔
            parent = (child - 1) / 2;
        }
        return true;
    }

    @Override
    public E poll() {
    	// heap에서 root를 제거하고 그 데이터를 반환하는 것과 같은 방식
    	// heap이 비어있으면
        if (isEmpty()) {
        	// null 반환
            return null;
        }
        // 반환 데이터는 root의 데이터이고
        E result = theData.get(0);
        // heap에 root 밖에 없다면
        if (theData.size() == 1) {
        	// 배열로 구현된 heap에서 단순히 root를 제거함으로서 root를 제거 
            theData.remove(0);
            // root의 값을 반환하고 메소드 종료
            return result;
        }
        // heap에서 원소를 제거하듯이
        // root를 제거하려고 하니까 마지막 leaf를 root에 붙여넣고 heap의 규칙에 맞게 조정해주면 됨
        theData.set(0, theData.remove(theData.size() - 1));
        
        // parent는 root부터 시작해
        int parent = 0;
        while (true) {
        	// left의 index를 배열로 구현된 heap에서 left를 찾는 공식에 따라 찾음
            int leftChild = 2 * parent + 1;
            // left가 없으면 
            if (leftChild >= theData.size()) {
            	// 루프를 멈춤
                break;
            }
            // right의 index를 찾고
            int rightChild = leftChild + 1;
            // min을 left로 가정해놓고
            int minChild = leftChild;
            // right가 존재하고, left가 right보다 크다면
            if (rightChild < theData.size() && compare(theData.get(leftChild),
                    theData.get(rightChild)) > 0) {
            	// min은 right인 것이고 아니라면 min은 left이다.
                minChild = rightChild;
            }
            // parent가 child중 가장 작은 것보다 크다면
            if (compare(theData.get(parent),
                    theData.get(minChild)) > 0) {
            	// parent와 minChild를 바꾸고
                swap(parent, minChild);
                // parent(현재 노드)를 minChild로 바꾼 뒤 루프를 다시 돈다
                parent = minChild;
            } else {
            	// parent가 child보다 크다면 heap의 규칙에 맞게 조절된 것이니까
            	// loop를 멈춤
                break;
            }
        }
        // root의 값을 반환
        return result;
    }

    @SuppressWarnings("unchecked")
    private int compare(E left, E right) {
    	// comparator가 있으면
        if (comparator != null) {
        	// comparator을 통해 비교
            return comparator.compare(left, right);
        } else {
        	// 없으면 원소를 comparable 인터페이스를 구현한 객체로 보고 compareTo를 사용해서 비교
        	// 만약 comparable 인터페이스를 구현하지 않은 객체면 ClassCastException이 발생함
            return ((Comparable<E>) left).compareTo(right);
        }
    }
    
    public void swap(int a, int b) {
        E temp = theData.get(a);
        theData.set(a, theData.get(b));
        theData.set(b, temp);
    }

    @Override
    public int size() {
        return theData.size();
    }

    @Override
    public Iterator<E> iterator() {
        return theData.iterator();
    }

    @Override
    public E peek() {
        return theData.get(0);
    }    
}