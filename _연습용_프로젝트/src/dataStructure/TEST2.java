package dataStructure;

import java.util.HashSet;
import java.util.Scanner;

public class TEST2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String inp = "";
		HashSet<String> set = new HashSet<>();
		int count = 0;
		int total = 0;
		while(!(inp = sc.nextLine()).equals("END")) {
			if(set.contains(inp)) {
				count++;
			} else {
				total++;
			}
		}
		System.out.println("중복: " + count);
		System.out.println("총: " + total);
	}

}
