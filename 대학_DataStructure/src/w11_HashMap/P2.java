package w11_HashMap;

import java.util.HashMap;
import java.util.Set;

public class P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		
		map.put(201513432, "Lim");
		map.put(201311111, "Kim");
		map.put(201212345, "Lee");
		map.put(201622222, "Park");
		map.put(201054321, "Park");
		
		Set<Integer> keys = map.keySet();
		for(Integer key : keys) {
			System.out.println(key + " " + map.get(key));
		}
	}

}
