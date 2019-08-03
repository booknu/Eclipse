import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
		static int a,b,c, n ;
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		String t = br.readLine();
		StringTokenizer st  = new StringTokenizer(t, " ");
		a= Integer.parseInt(st.nextToken());
		b= Integer.parseInt(st.nextToken());
		c= Integer.parseInt(st.nextToken());
		n = Integer.parseInt(br.readLine());
		dest allDest[] = new dest [n];
		for(int i=0; i<n; i++) {
			String info = br.readLine();
			String arr[] = info.split(" ");
			String temp = "";
			for(int j=0; j<arr.length-2; j++) {
				temp += arr[j]+" ";
			}
			int x = Integer.parseInt(arr[arr.length-2]);
			int y = Integer.parseInt(arr[arr.length-1]);
			allDest[i] = new dest(temp.trim(), x, y, false, -1);
		}
		for(int i =0; i<3; i++) {
			String info = br.readLine();
			String arr[] = info.split(" ");
			String temp = "";
			for(int j=0; j<arr.length-2; j++) {
				temp += arr[j]+" ";
			}
			int x = Integer.parseInt(arr[arr.length-2]);
			
		}
		
		
		
		bw.close();
	}
	
}


class dest{
	String name; 
	int x,y; 
	boolean isFuel = false; // 주유소인지?
	int galen = -1; //주유소일 경우 기름
	
	public dest(String name, int x, int y, boolean f, int galen) {
		this.name = name;
		this.x = x;
		this.y =y;
		this.galen = galen;
		this.isFuel = f;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFuel() {
		return isFuel;
	}

	public void setFuel(boolean isFuel) {
		this.isFuel = isFuel;
	}

	public int getGalen() {
		return galen;
	}

	public void setGalen(int galen) {
		this.galen = galen;
	}
	
	
}

class people{
	int cost;//요금
	dest pos;
	
	public people(int cost, dest pos) {
		this.cost =cost;
		this.pos = pos;
	}
}


class taxi{
	int prex, prey;
	int x, y; //현위치
	double fuel;
	int wallet;
	List<people> list;
	
	public taxi(int px, int py, int x, int y, double f, int w, List l) {
		this.prex = x;
		this.prey = y;
		this.x = x;
		this.y = y;
		this.fuel = f;
		this.wallet = w;
		this.list = l;
	}
}
