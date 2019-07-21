package w2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 졸리_점퍼_변형 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while((line = sc.readLine()) != null && line.length() != 0) {
			StringTokenizer st = new StringTokenizer(line);
			st.nextToken();
			int prev = Integer.parseInt(st.nextToken());
			int curr = prev;
			int diffMax = 0;
			int count = 1;			
			boolean sorted = true;
			
			while(st.hasMoreTokens()) {
				prev = curr;
				curr = Integer.parseInt(st.nextToken());
				if(curr < prev) {
					sorted = false;
				}
				diffMax = Math.max(Math.abs(curr - prev), diffMax);
				count++;
			}
			
			// jolly
			if(diffMax <= count) {
				if(sorted) {
					System.out.println("Jolly");
				} else {
					System.out.println("Little Jolly");
				}
			} else {
				System.out.println("Not Jolly");
			}
		}
	}

}