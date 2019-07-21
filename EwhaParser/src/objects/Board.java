package objects;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Board {
	private String htmlName;
	private MainText mainText;
	private ArrayList<Comment> comments;
	
	public Board(String _htmlName, MainText _mainText, ArrayList<Comment> _comments) {
		htmlName = _htmlName;
		mainText = _mainText;
		comments = _comments;
	}
	
	public Board(String _htmlName, MainText _mainText) {
		this(_htmlName, _mainText, new ArrayList<Comment>());
	}
	
	public Board(String _htmlName) {
		this(_htmlName, null);
	}
	
	/**
	 * set main text object
	 * 
	 * @param _mainText
	 */
	public void setMainText(MainText _mainText) {
		mainText = _mainText;
	}
	
	/**
	 * change existing comments list to new comments list
	 * 
	 * @param _comments new comments list
	 */
	public void setComments(ArrayList<Comment> _comments) {
		comments = _comments;
	}
	
	/**
	 * add comment to existing comments list
	 * 
	 * @param comment new comment
	 */
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	/**
	 * get board's html name
	 * 
	 * @return
	 */
	public String getHtmlName() {
		return htmlName;
	}
	
	/**
	 * get main text
	 * 
	 * @return
	 */
	public MainText getMainText() {
		return mainText;
	}
	
	/**
	 * get Iterator of comments
	 * @return
	 */
	public Iterator<Comment> commentIterator() {
		return comments.iterator();
	}
	
	/**
	 * make board to excel
	 * 
	 * @return
	 */
	public Workbook toExcel() {
		// create Workbook
		Workbook xls = new XSSFWorkbook(); // Excel 2007 or later
		
		////// Sheet //////
		Sheet sheet1 = xls.createSheet("first");
		
		////// Style //////
		
		////// fill data //////
		Row row = null;
		Cell cell = null;
		
		// MainText
		row = sheet1.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue(mainText.getSubject());
		cell = row.createCell(1);
		cell.setCellValue(mainText.getId());
		cell = row.createCell(2);
		cell.setCellValue(mainText.getDate());
		cell = row.createCell(3);
		cell.setCellValue(mainText.getVisitor());
		cell = row.createCell(4);
		cell.setCellValue(mainText.getContent());
		
		// Comments
		int rowNum = 2;
		for(Comment comment : comments) {
			row = sheet1.createRow(rowNum++);
			cell = row.createCell(0);
			cell.setCellValue(comment.getId());
			cell = row.createCell(1);
			cell.setCellValue(comment.getDate());
			cell = row.createCell(2);
			cell.setCellValue(comment.getContent());
		}
		
		return xls;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(mainText);
		for(int i = 0; i < comments.size(); i++) {
			sb.append(comments);
		}
		return sb.toString();
	}
}
