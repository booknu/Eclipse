package 그래프;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import 그래프.다익스트라_우선순위큐.Edge;

/**
 * [[ 설명 ]]
 * 프림의 알고리즘과 굉장히 유사함.
 * 특정 정점에서 시작하는 최단 거리를 구할 수 있음
 * 
 * O(V^2)
 * 
 * @author LENOVO
 *
 */
public class 다익스트라_책버전 {

static final int INF = 87654321;
	
	public static void main(String args[]) {
		int N = 5; // number of vertex
		LinkedList<Edge>[] edges = new LinkedList[N]; // edges from i
		for(int i = 0; i < N; i++) {
			edges[i] = new LinkedList<Edge>();
		}
//		setGraph(edges);
		inputGraph(edges, N);
		for(int from = 0; from < N; from++) {
			System.out.println("from " + from);
			int[] dist = dijkstra(edges, N, from);
			for(int to = 0; to < N; to++) {
				System.out.print(dist[to] + " ");
			}
			System.out.println("\n");
		}
		
	}
	
	public static int[] dijkstra(LinkedList<Edge>[] edges, int N, int start) {
		boolean[] contained = new boolean[N];
		int[] dist = new int[N];
		int[] parent = new int[N];
		
		for(int i = 0; i < N; i++) {
			dist[i] = INF;
			parent[i] = -1;
		}
		
		int curr = start;
		dist[start] = 0;
		while(!contained[curr]) {
			contained[curr] = true;
			
			// iterate all of the edges connected with curr vertex
			Iterator<Edge> edgeIter = edges[curr].iterator();
			while(edgeIter.hasNext()) {
				Edge edge = edgeIter.next();
				int next = edge.v;
				int weight = edge.w;
				int nextDist = dist[curr] + weight;
				// found shorter path
				if(!contained[next] && nextDist < dist[next]) {
					dist[next] = nextDist;
				}
			}
			
			// choose minimum weighted linked vertex to next vertex
			// if using priority queue, to find least weighted edge process will take O(lgN)
			curr = start;
			int distance = INF;
			for(int next = 0; next < N; next++) {
				if(!contained[next] && (distance > dist[next])) {
					distance = dist[next];
					curr = next;
				}
			}
		}
		
		return dist;
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
	
	public static void inputGraph(LinkedList<Edge>[] edges, int N) {
		// result of start: 0, end: 3 is 8
		Scanner sc = new Scanner("0 5 8 -1 -1\n"
				+ "-1 0 1 4 2\n"
				+ "-1 -1 0 3 2\n"
				+ "4 4 -1 0 1\n"
				+ "-1 -1 2 1 0\n");
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				int weight = sc.nextInt();
				if(weight > 0) {
					edges[i].add(new Edge(j, weight));
				}
			}
		}
		
	}
	
	static class Edge {
		int v, w; // vertex, weight
		public Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}
}
