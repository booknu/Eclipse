package objects;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 문제에 대한 정보
 * 
 * 생성자에서 Element 정보를 인자로 받는데, 
 * 이것은 문제들의 table 중 해당 문제의 <tr>이라고 가정
 * 
 * tr 안에 정보들이
 * 
 * tr
 * 		td (id)
 * 			a (id)
 * 		td (name, classify)
 * 			div (name)
 * 				a(name)
 * 			div (classify)
 * 				a (classify들)
 * 				a (classify들)
 * 				...
 * 		td (무쓸모)
 * 		td (solved)	
 * 			a(solved): "&nbsp;푼사람수" 형태로 저장
 * 
 * 와 같은 구조로 들어가 있음
 * 
 * @author Administrator
 *
 */
public class Problem {
	private String id;
	private String name;
	private ArrayList<String> classify;
	private int solved;
	
	/**
	 * Constructor
	 *  
	 * @param tr 문제에 대한 정보를 담고 있는 Element (<tr> 요소)
	 */
	public Problem(Element tr) {
		// 모든 td 정보 가져오기
		Elements tdList = tr.getElementsByTag("td");
		
		// id
		Element td = tdList.get(0);
		id = td.child(0).html();
		
		// name, classify
		td = tdList.get(1);
		Elements divs = td.getElementsByTag("div");
		name = divs.get(0).child(0).html();
		
		Elements ahref = divs.get(1).getElementsByTag("a");
		classify = new ArrayList<String>();
		for(int i = 0; i < ahref.size(); i++) {
			classify.add(ahref.get(i).html());
		}
		
		// solved
		td = tdList.get(3);
		String solvedStr = td.child(0).text().trim().replace(" x", "");
		solved = Integer.parseInt(solvedStr);
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<String> getClassify() {
		return classify;
	}
	
	public int getSolved() {
		return solved;
	}
	
	public String toString() {
		return "[ " + id + ", " + name + ", " + solved + ", " + classify +  " ]";
	}
}
