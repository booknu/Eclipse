package w1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class _10진수_대_16진수 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		String line;
		
		while((line = sc.readLine()) != null && line.length() != 0) {
			int num;
			if(line.charAt(0) == '0') {
				num = Integer.parseInt(line.substring(2), 16);						
				System.out.println(num);
			} else {
				num = Integer.parseInt(line);
				System.out.println("0x" + Integer.toHexString(num).toUpperCase());
			}
		}
	}

}
