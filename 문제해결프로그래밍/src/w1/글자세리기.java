package w1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class 글자세리기 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		
		String line;
		int word, alphabet;
		
		while((line=sc.readLine()) != null && line.length() != 0) {
			word = 1;
			alphabet = 0;
			
			for(int i = 0; i < line.length(); i++) {
				if(line.charAt(i) == ' ') {
					word++;
				} else {
					alphabet++;
				}
			}
			
			System.out.println(word + " " + alphabet);
		}
	}

}
