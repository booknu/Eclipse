package main;

import java.util.ArrayList;

import objects.Problem;
import parsing.HTMLParser;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HTMLParser parser = new HTMLParser();
		while(parser.hasNextPage()) {
			System.out.println("!!! page " + parser.getCurrPage() + " !!!");
			ArrayList<Problem> probs = parser.getProblems();
			for(Problem prob : probs) {
				System.out.println(prob);
			}
			System.out.println(' ');
			parser.loadNextPage();
		}
		
	}

}
