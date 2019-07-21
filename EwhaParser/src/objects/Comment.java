package objects;

public class Comment {
	private String id;
	private String date;
	private String content;
	
	public Comment(String _id, String _date, String _content) {
		id = _id;
		date = _date;
		content = _content;
	}
	
	public String getId() {
		return id;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getContent() {
		return content;
	}
	
	public String toString() {
		return "[ " + id + "\n," + date + "\n," + content + " ]\n";
	}
}
