package 그래프;

import java.util.Iterator;
import java.util.LinkedList;

/*******
 * 주소: 
 * *****
 * 
 * [[ 설명 ]]
 * 그래프에서 모든 정점을 포함하는
 * 비용이 최소인 최소 신장 트리 (minimum spanning tree)(MST)를
 * 구하는 알고리즘.
 * 
 * O(E + VlogV)
 * 따라서 E가 많을수록 좋음
 * 
 * [[ 코드 ]]
 * 최상위 loop는 모든 정점을 트리가 포함할 때까지 반복을 하는데,
 * 
 * 그 안에서 첫 번째 loop는
 * 현재 만들어진 tree의 각 정점들과 그것에 연결된 정점들 사이의
 * 거리를 구하는 것.
 * 만약 한 개의 정점이 tree의 여러개의 정점과 연결되면 그 중
 * 가장 작은 값을 dist에 기록함 (더 큰 edge는 사용할 필요가 없으니까)
 * 
 * 두 번째 loop는
 * 위에서 구한 dist를 바탕으로
 * 현재 tree에 연결된 정점들 중 거리가 가장 가까운 정점을 선택한다.
 * 만약 모든 정점이 연결되었으면, 다음 확인할 정점은 start가 되므로,
 * loop가 끝나는 것과 마찬가지다.
 * 
 * @author LENOVO
 *
 */
public class 프림알고리즘 {
	
	static final int INF = 87654321;
	
	public static void main(String args[]) {
		int N = 5; // number of vertex
		LinkedList<Edge>[] edges = new LinkedList[N]; // edges from i
		for(int i = 0; i < N; i++) {
			edges[i] = new LinkedList<Edge>();
		}
		setGraph(edges);
		
		System.out.println(prim(N, edges, 0));
	}
	
	/**
	 * return sum of weight of MST
	 * 
	 * @param N number of vertex
	 * @param edges graph's edges
	 * @param start start vertex
	 * @return sum of MST
	 */
	public static int prim(int N, LinkedList<Edge>[] edges, int start) {
		int ret = 0;
		
		boolean contained[] = new boolean[N]; // is vertex contained to tree
		int dist[] = new int[N]; // distance from start vertex
		int parent[] = new int[N];
		
		// initialize
		for(int i = 0; i < N; i++) {
			dist[i] = INF;
			parent[i] = -1;
		}
		
		dist[start] = 0;
		int curr = start;
		
		while(!contained[curr]) {
			contained[curr] = true;
			
			// iterate all of the edges connected with curr vertex
			// renew distance from made tree to linked vertex
			Iterator<Edge> edgeIter = edges[curr].iterator();
			while(edgeIter.hasNext()) {
				Edge edge = edgeIter.next();
				int next = edge.v;
				int weight = edge.w;
				// 
				if(!contained[next] && (weight < dist[next])) {
					dist[next] = weight;
					parent[next] = curr;
				}
			}
			
			// choose minimum weighted linked vertex to next vertex
			curr = start;
			int distance = INF;
			edgeIter = edges[curr].iterator();
			for(int next = 0; next < N; next++) {
				if(!contained[next] && dist[next] < distance) {
					distance = dist[next];
					curr = next;
				}
			}
			
			if(distance != INF) {
				ret += distance;
			}
		}
		
		return ret;
	}
	
	public static void setGraph(LinkedList<Edge>[] edges) {
		edges[0].add(new Edge(1, 4));
		edges[0].add(new Edge(3, 5));
		edges[0].add(new Edge(2, 6));
		
		edges[1].add(new Edge(0, 4));
		edges[1].add(new Edge(3, 3));
		edges[1].add(new Edge(4, 1));
		
		edges[2].add(new Edge(0, 6));
		edges[2].add(new Edge(3, 7));
		
		edges[3].add(new Edge(0, 5));
		edges[3].add(new Edge(1, 3));
		edges[3].add(new Edge(2, 7));
		edges[3].add(new Edge(4, 2));
		
		edges[4].add(new Edge(1, 1));
		edges[4].add(new Edge(3, 2));
	}
	
	static class Edge {
		int v, w; // vertex, weight
		public Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}
}
