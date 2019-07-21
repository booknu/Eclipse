package _________TEST;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Workbook;

import constants.Const;
import objects.Board;
import parser.Parser;

public class Main {
	public static void main(String[] args) {
		// get html files from dir
		File[] htmlFiles = Const.DIR_HTML_FILES.listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if(!f.isFile()) {
					return false;
				}
				String fileName = f.getName();
				int lastIndexOfPoint = fileName.lastIndexOf('.');
				if(lastIndexOfPoint == -1) {
					return false;
				}
				String extension = fileName.substring(lastIndexOfPoint + 1);
				return extension.equals("html") || extension.equals("htm");
			}
		});

		// create output dir if it doesn't exists
		if(!Const.DIR_OUTPUT_FILES.isDirectory()) {
			Const.DIR_OUTPUT_FILES.mkdir();
		}

		int parseNum = 1;
		Parser parser = new Parser(htmlFiles);
		while(parser.hasNext()) {
			Board board = parser.parseNext();
			Workbook xls = board.toExcel();
			
			// excel 파일 저장
	        try {
	            File xlsFile = new File(Const.DIR_OUTPUT_FILES.getName() + "/" + board.getHtmlName() + ".xlsx");
	            FileOutputStream fileOut = new FileOutputStream(xlsFile);
	            xls.write(fileOut);
	            fileOut.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        System.out.println(parseNum++ + ": [" + board.getHtmlName() + "] 작업 완료.");
		}
		
		Scanner sc = new Scanner(System.in);
		System.out.println("작업이 끝났습니다. 아무 키나 눌러주세요.");
		sc.nextLine();
		sc.close();
	}
}
