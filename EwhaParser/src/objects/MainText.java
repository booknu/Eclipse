package objects;

/**
 * 게시판의 본문 객체
 * 
 * @author Administrator
 *
 */
public class MainText {
	private String subject;
	private String id;
	private String date;
	private int visitor;
	private String content;
	
	public MainText(String _subject, String _id, String _date, int _visitor, String _content) {
		subject = _subject;
		id = _id;
		date = _date;
		visitor = _visitor;
		content = _content;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getId() {
		return id;
	}
	
	public String getDate() {
		return date;
	}
	
	public int getVisitor() {
		return visitor;
	}
	
	public String getContent() {
		return content;
	}
	
	public String toString() {
		return "[ " + subject + "\n," + id + "\n," + date + "\n," + date + "\n," + visitor + "\n," + content + " ]\n";
	}
}
