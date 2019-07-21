package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		: p283 여행 짐 싸기
 * **********
 * <해결방안>	: 
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 여행_짐_싸기_책버전 {

	static int n, capacity;
	static int vol[], need[];
	static String[] name;
	static int cache[][];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int n = sc.nextInt();
			int cap = sc.nextInt();
			int vol[] = new int[n];
			int need[] = new int[n];
			String name[] = new String[n];
			for(int i = 0; i < n; i++) {
				name[i] = sc.nextToken();
				vol[i] = sc.nextInt();
				need[i] = sc.nextInt();
			}
			
			ArrayList<Integer> list = solve(n, cap, vol, need, name);
			int sum = 0;
			int size = list.size();
			for(int i = 0; i < size; i++) {
				sum += need[list.get(i)];
			}
			System.out.println(sum + " " + size);
			for(int i = 0; i < size; i++) {
				System.out.println(name[list.get(i)]);
			}
			
		}
	}
	
	public static ArrayList<Integer> solve(int N, int cap, int[] v, int[] desire, String[] thing) {
		n = N;
		capacity = cap;
		vol = v;
		need = desire;
		name = thing;
		cache = new int[capacity + 1][n];
		for(int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], -1);
		}
		ArrayList<Integer> ret = new ArrayList<Integer>();
		reconstruct(cap, 0, ret);
		return ret;
	}
	
	public static int pack(int cap, int item) {
		if(item == n) return 0;
		if(cache[cap][item] != -1) return cache[cap][item];
		int ret = pack(cap, item + 1);
		if(cap >= vol[item]) {
			ret = Math.max(ret, pack(cap - vol[item], item + 1) + need[item]);
		}
		cache[cap][item] = ret;
		return ret;
	}
	
	public static void reconstruct(int cap, int item, ArrayList<Integer> picked) {
		if(item == n) return;
		if(pack(cap, item) == pack(cap, item + 1)) {
			reconstruct(cap, item + 1, picked);
		} else {
			picked.add(item);
			reconstruct(cap - vol[item], item + 1, picked);
		}
	}
	
	static class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner(String s) {
			try {
				br = new BufferedReader(new FileReader(s));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		public FastScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String nextToken() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(nextToken());
		}

		long nextLong() {
			return Long.parseLong(nextToken());
		}

		double nextDouble() {
			return Double.parseDouble(nextToken());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
