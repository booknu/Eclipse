package parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import objects.Board;
import objects.Comment;
import objects.MainText;

public class Parser extends ArrayList<File> {
	private Iterator<File> iterator;
	
	public Parser(File[] docs) {
		super(Arrays.asList(docs));
	}

	/**
	 * does the iterator has next element?
	 * 
	 * @return
	 */
	public boolean hasNext() {
		if(iterator == null) {
			resetIterator();
		}
		return iterator.hasNext();
	}
	
	/**
	 * parse current html file
	 * 
	 */
	public Board parseNext() {
		if(iterator == null) {
			resetIterator();
		}
		
		try {
			File currHTML = iterator.next();
			
			String htmlName = currHTML.getName();
			Board board = new Board(htmlName.substring(0, htmlName.lastIndexOf('.')));
			Document doc = Jsoup.parse(currHTML, null);
			
			////// parse main text //////
			// subject
			String main_subject = doc.select("div.subject h3").first().text();
			
			// id, date, visitors
			Elements main_info = doc.select("div.info");
			String main_id = main_info.select("div.writer").first().ownText().substring(2);
			String main_date = main_info.select("div.date").first().ownText().substring(2);
			String main_visitors = main_info.select("div.hit").first().ownText().substring(2);
			
			// content
			String main_content = doc.select("div.content div.text_to_html").first().text();
			
			// create MainText object and set to board object
			MainText mainText = new MainText(main_subject, main_id, main_date
					, Integer.parseInt(main_visitors), main_content);
			board.setMainText(mainText);
			
			////// parse comments //////
			// comment divs
			Elements divs_comment = doc.select("div.comment_list");
			
			for(Element div_comment : divs_comment) {
				Elements divs_inline = div_comment.select("div.inline");
				
				// id
				String comment_id = divs_inline.first().text();
				
				// date
				String comment_date = divs_inline.last().text();
				
				// content
				String comment_content = div_comment.select("div.text_to_html").first().text();
				
				// create Comment object and add to board object
				Comment comment = new Comment(comment_id, comment_date, comment_content);
				board.addComment(comment);
			}
			
			return board;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * reset iterator to init state
	 * 
	 */
	public void resetIterator() {
		iterator = this.iterator();
	}
}
