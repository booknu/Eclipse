package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p306 드래곤 커브
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 드래곤 커브 수열 ]]
 *    0: FX
 *    1: FX + YF
 *    2: FX + YF + FX - YF
 *    3: FX + YF + FX - YF + FX + YF - FX - YF
 *    4: FX + YF + FX - YF + FX + YF - FX - YF + FX + YF + FX - YF - FX + YF - FX - YF
 *    
 *    1 = +FX, 2 = -FX
 *    a = +YF, b = -YF 로 치환하면
 *    
 *    1 -> 1a, 2 -> 2a
 *    a -> 1b, b -> 2b 과 같이 각 세대당 변화시킬 수 있다.
 *    
 *    또한 각 드래곤 커브 수열은 "+FX"로 시작한다고 가정하면
 *    수열의 각 항은 "3개"가 되고, n 세대의 항은 2^n 만큼 생기니까
 *    n 세대의 수열의 길이 = 3 * 2^n 이 된다는 것을 알 수 있다.
 *    
 *    0: 1
 *    1: 1a
 *    2: 1a1b
 *    3: 1a1b 1a2b
 *    4: 1a1b 1a2b 1a1b 2a2b
 *    5: 1a1b 1a2b 1a1b 2a2b 1a1b 1a2b 2a1b 2a2b
 *    
 *    와 같이 쓸 수 있다.
 *    
 *    
 * 2. [[ 기본 아이디어 ]]
 *    위와 같이 항을 치환하여 적었을 때 F(n) = n세대 수열 이라고 하면
 *    F(n)의 s ~ e 항은
 *    F(n-1)의 s/2 ~ e/2항의 영향을 받는다.
 *    
 *    {예시}
 *    0: (1)
 *    1: 1(a)
 *    2: 1a(1)b
 *    3: 1a1b (1)a2b
 *    4: 1a1b 1a2b (1a)1b 2a2b
 *    5: 1a1b 1a2b 1a1b 2a2b (1a1)b 1a2b 2a1b 2a2b
 *    이와 같이 이전 항에 영향을 받는 것을이 연쇄적인 작용이 된다.
 *    
 * 3. [[ getKthGenerationCode ]] 메소드
 *    k 번째 세대를 위와같이 코드화 한 것 중 일부를 반환하는 메소드이다.
 *    
 *    generation: 현재 세대
 *    s: 현재 세대를 코드화 했을 때 반환할 start index
 *    e: 현재 세대를 코드화 했을 때 반환할 end index
 *    
 *    - 기저사례: generation이 0세대이면, "1" 이라는 코드를 반환한다.
 *    
 *    - 0세대가 아닌 경우: s = s / 2, e = e / 2로 재귀호출을 해서
 *                 현재 세대의 코드 범위에 영향을 주는 이전 세대 코드를 가져온다.
 *                 그 코드를 통해 위에서 서술한 세대 변환 규칙에 의해 현재 세대 코드를 구한다.
 *    
 *    - 현재 세대 코드를 구하고 나서:
 *                 구해진 코드는 우리가 원하는 범위의 코드보다 클 수가 있으므로
 *                 그 일부를 사용해야 하는데, 일단 구해진 코드가 어느 범위를 표현하는 것인지
 *                 [a, b]로 알아내고, (a = s/2 * 2, b = e/2 * 2 + 1)
 *                 구해진 코드는 [0, b-a]의 길이를 가지고 있는 문자열이므로
 *                 [s, e]의 코드를 알고싶다면 코드의 [s-a, e-a+1)의 부분문자열을
 *                 알면 된다.
 *    
 *     
 * 4. [[ code를 구하고 난 뒤 ]]
 *    - 구해진 code를 decoding하여 원하는 수열로 만든다.
 *    
 *    - 만들어진 수열의 일부만을 사용하기 때문에(처음, 끝 term은 일부만 사용)
 *      substring을 해야하는데,
 *      우리가 만든 수열은 맨 처음에 "+"를 추가한 것으로 가정하기 때문에
 *      1번째 글자를 알아내려면 seq[1]을 구해야한다. (P = 1)
 *      2번째 글자를 알아내려면 seq[2]를 구해야한다. (P = 2)
 *      3번쨰 글자를 알아내려면 첫 번째 term은 skip하고 두 번째 term부터
 *        알아낸 sequence가 있으니까 seq[3 % 3]을 구해야 한다. (P = 3)
 *      ...
 *      
 *      따라서 substring(P % 3, P % 3 + length)를 하면 된다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * 이전 세대에서 구해진 코드에서 현재 사용할 범위에 맞게 substring
 * 하는 일이 조금 까다로웠음.
 * 
 * **********
 * @author LENOVO
 *
 */
public class 드래곤_커브 {

	public static String term[] = {"+FX", "-FX", "+YF", "-YF"};
	public static String generation0 = "1";
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt(); // n generation
			int P = sc.nextInt(); // n generation string's Pth character 
			int L = sc.nextInt(); // from Pth character, length of substring
			
			// assume each generation of dragon curve's start is "+FX"
			// by doing so, we can accept each term's length as 3
			// so P should be P + 1 (because we added "+" to first of sequence)
			// term = 항
			int startTerm = P / 3; // start term is ((P + 1) - 1) / 3
			int endTerm = (P + L - 1) / 3; // end term is ((P + 1) + L - 2) / 3
			
			String code = getKthGenerationCode(N, startTerm, endTerm);
			int codeLen = code.length();
			
			// decoding code to sequence
			String decode = "";
			for(int i = 0; i < codeLen; i++) {
				switch(code.charAt(i)) {
					case '1': decode += term[0]; break;
					case '2': decode += term[1]; break;
					case 'a': decode += term[2]; break;
					case 'b': decode += term[3]; break;
					default: throw new Exception("알 수 없는 코드");
				}
			}
			// decoded sequence contains larger part than [p, p + l)
			// because it contains full term
			// so we should use part of it
			int startIndex = P % 3;
			decode = decode.substring(startIndex, startIndex + L);
			System.out.println(decode);
		}
	}
	
	/**
	 * get coded substring of F(generation),
	 * which start index = s, end index = e
	 * 
	 * @param generation current generation
	 * @param s F(generation)'s substring start index
	 * @param e F(generation)'s substring end index
	 * @return substring of F(generation)
	 * @throws Exception inner method error (wrong code)
	 */
	public static String getKthGenerationCode(int generation, int s, int e) throws Exception {
		// base case: generation is 0
		if(generation == 0) {
			return generation0;
		}
		
		// current substring code based on part of prev generation's code [prevS, prevE] 
		int prevS = s / 2; 
		int prevE = e / 2;
		// generated code's start index that based on prev substring [prevS, prevE]
		// 이전 수열을 연산을 통해 현재 수열을 구하면
		// [s, e] 범위보다 넓을 수 있음.
		// 구해진 수열의 시작 index를 알아야 그 중 원하는 일부 [s, e]를 return할 수 있음
		int rangeS = prevS * 2;
		String prevCode = getKthGenerationCode(generation - 1, prevS, prevE);
		int len = prevCode.length();
		
		// generate code based on part of prev generation code
		String ret = "";
		for(int i = 0; i < len; i++) {
			switch(prevCode.charAt(i)) {
				case '1': ret += "1a"; break;
				case '2': ret += "2a"; break;
				case 'a': ret += "1b"; break;
				case 'b': ret += "2b"; break;
				default: throw new Exception("알 수 없는 prev");
			}
		}
		
		// use part of generated code [s, e]
		int subS = s - rangeS;
		int subE = e - rangeS + 1;
		return ret.substring(subS, subE);
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
