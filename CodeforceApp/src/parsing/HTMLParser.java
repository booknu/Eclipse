package parsing;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import constants.General;
import objects.Problem;

/**
 * http://codeforces.com/problemset 전용 파서
 * @author Administrator
 *
 */
public class HTMLParser {
	// 현재 페이지 요소를 가져오는데 사용되는 선택자
	public static final String SELECTOR_CURR_PAGE = "span.page-index.active";
	// 페이지들에 대한 정보를 가져오는 선택자
	public static final String SELECTOR_PAGES = "span.page-index";
	// problem들을 포함한 table을 가져오는 선택자
	public static final String SELECTOR_PROB_TABLE = "table.problems";
	
	// 페이지 요소를 가져왔을 때, 해당 페이지를 알려주는 속성 이름
	public static final String ATTR_CURR_PAGE = "pageIndex";
	
	private Document html;
	private String url;
	
	/**
	 * Constructor
	 * HTMLParser 객체에서 분석할 html 문서를 서버에서 가져옴
	 * (대상 html 파일은 codeforces.com/problemset 이라고 가정)
	 * 
	 * default: General.problemURL
	 */
	public HTMLParser() {
		this(General.problemURL);
	}
	
	/**
	 * Constructor
	 * HTMLParser 객체에서 분석할 html 문서를 서버에서 가져옴
	 * (대상 html 파일은 codeforces.com/problemset 이라고 가정)
	 * 
	 * @param url
	 */
	public HTMLParser(String url) {
		try {
			changeUrl(url);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error: Cannot get HTML From: " + url);
		}
	}
	
	/////// HTML 불러오기 관련 ///////
	/**
	 * 다음 페이지의 html을 불러온다.
	 * 
	 * @throws Exception
	 */
	public void loadNextPage() throws Exception {
		loadPage(getCurrPage() + 1);
	}
	
	/**
	 * 이전 페이지의 html을 불러온다.
	 * @throws Exception
	 */
	public void loadPrevPage() throws Exception {
		loadPage(getCurrPage() - 1);
	}
	
	/**
	 * 해당 페이지의 html을 불러온다.
	 * 
	 * @param page
	 * @throws Exception
	 */
	public void loadPage(int page) throws Exception {
		int totalPage = getTotalPage();
		if(page >= totalPage || page < 1) {
			throw new Exception("Error: 해당 페이지가 존재하지 않습니다. (" + page + ", " + totalPage + ")");
		}
		
		String htmlRequest = url.substring(url.lastIndexOf('?'), url.length()); // url에서 ? 이후의 htmlRequest 정보를 가져옴
		String newUrl = General.baseUrl + page + htmlRequest;
		changeUrl(newUrl);
	}
	
	/**
	 * 해당 url 정보로 html을 다시 불러온다.
	 * 
	 * @param url
	 * @throws IOException
	 */
	private void changeUrl(String url) throws IOException {
		while(true) {
			try {
				html = Jsoup.connect(url).get();
				break;
			} catch (IOException e) {
				// 서버 오류로 html을 가져오지 못하면 재시도
				System.out.println("Cannot get HTML From: " + url + ", retry...");
			}
		}
		
		this.url = url;
	}
	
	/////// HTML 파싱 관련 ///////
	/**
	 * problemset의 총 페이지 개수
	 * 
	 * @return problemset의 총 페이지 개수
	 */
	public int getTotalPage() {
		Element lastPageEl = html.select(SELECTOR_PAGES).last();
		String lastPage = lastPageEl.attr(ATTR_CURR_PAGE);
		return Integer.parseInt(lastPage);
	}
	
	/**
	 * 현재 problemset의 페이지
	 * 
	 * @return 현재 problemset의 페이지
	 */
	public int getCurrPage() {
		Element currPageEl = html.select(SELECTOR_CURR_PAGE).first();
		String currPage = currPageEl.attr(ATTR_CURR_PAGE);
		return Integer.parseInt(currPage);
	}
	
	/**
	 * 다음 페이지가 있는지?
	 * 
	 * @return 
	 */
	public boolean hasNextPage() {
		return getCurrPage() < getTotalPage();
	}
	
	/**
	 * 이 페이지에서 가지고 있는 문제들에 대해 ArrayList<Problem> 형태로 반환
	 * @return 문제들의 List
	 */
	public ArrayList<Problem> getProblems() {
		ArrayList<Problem> probs = new ArrayList<Problem>();
		Elements trList = html.select(SELECTOR_PROB_TABLE + " tr");
		// i = 1부터 시작인 이유는 첫 tr에는 #, Name, Solved 같은 정보 제공용만이 들어가있기 때문
		for(int i = 1; i < trList.size(); i++) {
			probs.add(new Problem(trList.get(i)));
		}
		return probs;
	}
	
}
