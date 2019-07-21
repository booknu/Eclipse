package 그래프;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 
 * [[ 설명 ]]
 * priority queue를 사용하여 현재까지 방문한 node에 연결된
 * edge들 중 dist가 가장 작은 edge를 찾는 작업을 O(logN)으로 줄여
 * O(NlogN)의 시간이 걸린다.
 * 
 * [[ 코드 ]]
 * priority queue에는 다음에 방문해야할 edge들이 저장되어있다.
 * 
 * [[ 오류 ]]
 * pq에 그냥 weight을 기준으로 넣어짐
 * start ~ v까지의 거리를 기준으로 넣어야해서
 * 첫 방문일때도 pq에 넣고
 * 한 번 방문했을 때도 pq에 넣음
 * 이 때 한 정점에 대해 여러개가 pq에 들어갈 수 있으니
 * visited를 추가해서 이미 방문한 정점이면 skip하게 만듬
 * 
 * @author LENOVO
 *
 */
public class 다익스트라_우선순위큐_오류수정 {

static final int INF = 87654321;
	
	public static void main(String args[]) {
		int N = 5; // number of vertex
		LinkedList<Edge>[] edges = new LinkedList[N]; // edges from i
		for(int i = 0; i < N; i++) {
			edges[i] = new LinkedList<Edge>();
		}
		setGraph(edges);
//		inputGraph(edges, N);
		print(edges, N);
		test(edges);
	}
	
	public static int[] dijkstra(LinkedList<Edge>[] edges, int N, int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
		boolean[] visited = new boolean[N];
		int[] dist = new int[N];
		int[] parent = new int[N];
		Arrays.fill(dist, -1); // -1 is unvisited node
		dist[start] = 0;
		Arrays.fill(parent, -1);
		
		pq.offer(new Edge(start, 0));
		
		while(!pq.isEmpty()) {
			int curr = pq.poll().v;
			if(visited[curr]) {
				continue;
			}
			visited[curr] = true;
			// iterate all of the edges connected with curr vertex
			Iterator<Edge> edgeIter = edges[curr].iterator();
			while(edgeIter.hasNext()) {
				Edge edge = edgeIter.next();
				int next = edge.v;
				int weight = edge.w;
				int nextDist = dist[curr] + weight;
				if(dist[next] == -1 || nextDist < dist[next]) {
					dist[next] = nextDist;
					parent[next] = curr;
					pq.offer(new Edge(next, nextDist));
				}
			}
		}
		return dist;
	}
	
	public static void print(LinkedList<Edge>[] edges, int N) {
		for(int from = 0; from < N; from++) {
			System.out.println("from " + from);
			int[] dist = dijkstra(edges, N, from);
			for(int to = 0; to < N; to++) {
				System.out.print(dist[to] + " ");
			}
			System.out.println("\n");
		}
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
	
	public static void test(LinkedList<Edge>[] edges) {
		// result of start: 0, end: 3 is 8
		Scanner sc = new Scanner("5 8\n"
				+ "5\n"
				+ "5 2 4\n"
				+ "5 4 2\n"
				+ "4 2 1\n"
				+ "4 3 1\n"
				+ "2 1 3\n"
				+ "1 4 3\n"
				+ "1 3 6\n"
				+ "3 4 2\n");
		
		int N = sc.nextInt(); // vertex
		int M = sc.nextInt(); // edge
		int start = sc.nextInt();
		edges = new LinkedList[N];
		for(int i = 0; i < N; i++) {
			edges[i] = new LinkedList<Edge>();
		}
		for(int i = 0; i < M; i++) {
			int s = sc.nextInt()-1;
			int e = sc.nextInt()-1;
			int w = sc.nextInt();
			edges[s].addLast(new Edge(e, w));			
		}
		
		print(edges, N);
	}
	
	static class Edge implements Comparable<Edge>{
		int v, w; // vertex, weight
		public Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}
		@Override
		public int compareTo(Edge arg0) {
			// TODO Auto-generated method stub
			return this.w - arg0.w;
		}
	}
}
