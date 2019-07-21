package w2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

/**
 * 오답노트:
 * 
 * 입력이 다 끝났는지를 제대로 확인 못함
 * @author LENOVO
 *
 */
public class 습관_찾기 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		String read;
		char[] line;
		HashMap<String, Integer> map;
		while((read = sc.readLine()) != null) {
			if(read.length() == 0) {
				System.out.println(0);
				break;
			}
			line = read.toCharArray();
			map = new HashMap<String, Integer>();
			String word;
			
			int max = 0;
			String maxString = "";
			
			int bound = line.length - 2;
			for(int i = 0; i < bound; i++) {
				word = "";
				for(int j = 0; j < 3; j++) {
					word += line[i + j];
				}
				Integer exist = map.get(word);
				if(exist != null) {
					map.put(word, exist + 1);
					if(exist + 1 > max) {
						max = exist + 1;
						maxString = word;
					}
				} else {
					map.put(word, 1);
					if(1 > max) {
						max = 1;
						maxString = word;
					}
				}
//				System.out.println(word);
			}
			
			System.out.println(max + " " + maxString);
			
		}
	}

}