import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		
		int n = sc.nextInt();
		Pair shop[] = new Pair[n];
		for(int i = 0; i < n; i++) {
			shop[i] = new Pair(i+1, sc.nextInt());
		}
		
		Arrays.sort(shop, new Comparator<Pair>() {
			@Override
			public int compare(Pair o1, Pair o2) {
				return o1.price - o2.price;
			}
		});
		
		int T = sc.nextInt();
		Quest quest[] = new Quest[T];
		int ans[] = new int[T];
		for(int i = 0; i < T; i++) {
			int l = sc.nextInt();
			int r = sc.nextInt();
			quest[i] = new Quest(i, l, r);
		}
		
		Arrays.sort(quest, new Comparator<Quest>() {
			@Override
			public int compare(Quest o1, Quest o2) {
				if(o1.l > o2.l) {
					return 1;
				} else if(o1.l < o2.l) {
					return -1;
				} else {
					return o1.r - o2.r;
				}
			}
		});
		
		int questIdx = 0;
		while(questIdx < quest.length) {
			int l = quest[questIdx].l;
			int r = quest[questIdx].r;
			int prev = -1;
			int curr = -1;
			int min = Integer.MAX_VALUE;
			for(int j = 0; j < n; j++) {
				if(shop[j].num >= l && shop[j].num <= r) {
					if(prev == -1) {
						prev = j;
					} else {
						curr = j;
						min = Math.min(min, Math.abs(shop[curr].price - shop[prev].price));
						prev = curr;
					}
				}
			}
		}
	}
	
	static class Pair {
		int num, price;
		public Pair(int _a, int _b) {
			num = _a;
			price = _b;
		}
	}
	
	static class Quest {
		int num;
		int l;
		int r;
		public Quest(int a, int b, int c) {
			num = a;
			l = b;
			r = c;
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
