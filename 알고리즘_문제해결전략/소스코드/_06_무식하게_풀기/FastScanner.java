package _06_무식하게_풀기;
 

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class FastScanner {
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
