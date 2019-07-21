package TEST;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Scanner;

public class FontList {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Font[] fonts = e.getAllFonts(); // Get the fonts
	    
	    ArrayList<String> fontList = new ArrayList<String>();
	    
	    for (Font f : fonts) {
	      System.out.println(f.getFontName());
	      fontList.add(f.getFontName());
	    }
	    
	    System.out.print("찾으려는 폰트: ");
	    Scanner cs = new Scanner(System.in);
	    String search = cs.nextLine();
	    cs.close();
	    System.out.println();
	    for(String fontName : fontList)
	    {
	    	if(fontName.contains(search))
	    	{
	    		System.out.println(fontName);
	    	}
	    }
	}

}
