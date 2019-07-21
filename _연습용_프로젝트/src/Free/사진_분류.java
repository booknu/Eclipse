package Free;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * 추천
 * 가로 길이 긴거 찾을 때
 * 2.0MP	1.5		U
 * 
 * 세로 길이 길거 찾을 때
 * 2.0MP	0.6		D
 * @author Administrator
 *
 */
public class 사진_분류 {

	static final File TARGET_DIR = new File("TARGET");
	static final File OUTPUT_DIR = new File("OUTPUT");
	
	////////GUI////////
	static JFrame frame;
	// 찾는중 GUI
	static JProgressBar searchProgBar;
	static JLabel imgLabel;
	static Image showingImg;
	static JPanel textPanel;
	static JLabel nameLabel, numLabel;
	// 복사중 GUI
	static JProgressBar copyProgBar;
	
	////////GUI////////
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if(TARGET_DIR.getPath().equals(OUTPUT_DIR.getPath()))
		{
			System.err.println("TARGET, OUTPUT 디렉토리가 같음");
			System.exit(1);
		}

		if(!OUTPUT_DIR.exists())
		{
			System.out.println("<OUTPUT_DIR이 존재하지 않아 새로 만듭니다.>");
			OUTPUT_DIR.mkdir();
		}

		if(!TARGET_DIR.isDirectory() || !OUTPUT_DIR.isDirectory())
		{
			System.err.println("TARGET, OUTPUT 디렉토리 오류");
			System.exit(1);
		}


		double resolution, ratio;
		boolean ratioUpper;
		Scanner sc = new Scanner(System.in);
		System.out.print("몇 MP 이상의 사진?: ");
		resolution = sc.nextDouble() * 1000000;
		System.out.print("종횡비 기준(음수면 제한 X): ");
		ratio = sc.nextDouble();
		System.out.print("종횡비 이상(U), 이하(D): ");
		ratioUpper = sc.next().equalsIgnoreCase("U");

		
		System.out.println("\n----------조건에 맞는 사진을 검색중입니다...----------");
		System.out.printf("%-6s   %-12s  %s\n", "<파일 번호>", "<해상도>", "<파일 이름>");
		File[] list = TARGET_DIR.listFiles();
		ArrayList<File> selected = new ArrayList<File>();
		Image img;
		int width, height;
		int imageNums = 0;	// 테스트용
		
		startGUI(ratioUpper);
		setSearchingGUI(list.length);
		
		for(int i = 0; i < list.length; i++)
		{
			if(list[i].isFile())
			{
				img = new ImageIcon(list[i].getPath()).getImage();
//				img = ImageIO.read(list[i]);
				width = img.getWidth(null);
				height = img.getHeight(null);
				searchProgBar.setValue(i + 1);
				searchProgBar.setString(i + 1 + " / " + list.length);
				if(width != -1 && height != -1)	// 이 파일이 이미지 파일인 경우
				{
					imageNums++;
					// 조건 만족
					if(width * height > resolution)
					{
						if(ratioUpper)
						{
							if(ratio <= 0 || (double)width / height > ratio)
							{
								System.out.printf("%-6s   %-15s  [%s]\n", "<" + i + ">", width + " x " + height, list[i].getName());
								selected.add(list[i]);
								refreshSearchSelected(img, list[i].getName(), selected.size(), width, height);
							}
						}	
						else
						{
							if(ratio <= 0 || (double)width / height < ratio)
							{
								System.out.printf("%-6s   %-15s  [%s]\n", "<" + i + ">", width + " x " + height, list[i].getName());
								selected.add(list[i]);
								refreshSearchSelected(img, list[i].getName(), selected.size(), width, height);
							}
						}
					}
				}
			}
		}

		
		System.out.println();
		System.out.println("검사된 사진 파일의 수: " + imageNums);
		System.out.print("선택된 사진을 저장하시겠습니까? (Y / N): ");
		frame.setTitle("search process complete!!");
		boolean save = JOptionPane.showConfirmDialog(null, "탐색 작업이 끝났습니다.\n선택된 사진을 저장하시겠습니까?") == JOptionPane.OK_OPTION;
//		boolean save = sc.next().equalsIgnoreCase("Y");
		if(save)
		{
			setCopyingFileGUI(selected.size());
			System.out.println("\n----------사진을 OUTPUT 폴더로 복사중입니다...----------");
			File f;
			for(int i = 0; i < selected.size(); i++){
				copyProgBar.setString(i + 1 + " / " + selected.size());
				f = selected.get(i);
				System.out.println("남은 파일: " + (selected.size() - i));
				copy(f.getPath(), OUTPUT_DIR.getPath() + "\\" + f.getName());
			}
		}
		
		JOptionPane.showMessageDialog(null, "모든 작업이 끝났습니다.\n프로그램을 종료합니다.");
		System.exit(0);
	}

	/**
	 * 
	 * @param ratio 비율
	 * @param ratioUpper 가로인지 세로인지 (비율 이상, 이하)
	 */
	public static void startGUI(boolean ratioUpper) {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		
		// 명백한 가로 그림
		int correction = 40;
		if(ratioUpper)
		{
			frame.setSize(1280, 720 + correction);
		}
		else
			frame.setSize(720, 1280 + correction);
		
	}
	
	public static void setSearchingGUI(int fileNum) {
		frame.setTitle("searching...");
		
		imgLabel = new JLabel();
		nameLabel = new JLabel();
		numLabel = new JLabel();
		textPanel = new JPanel();
		searchProgBar = new JProgressBar();
		
		searchProgBar.setForeground(new Color(49, 232, 17));
		searchProgBar.setSize(200, 20);
		searchProgBar.setMaximum(fileNum);
		searchProgBar.setString(1 + " / " + fileNum);
		searchProgBar.setValue(0);
		searchProgBar.setStringPainted(true);
		
		textPanel.setLayout(new GridLayout(2, 1));
		textPanel.add(nameLabel);
		textPanel.add(numLabel);
		
		frame.add(searchProgBar, BorderLayout.NORTH);
		frame.add(imgLabel, BorderLayout.CENTER);
		frame.add(textPanel, BorderLayout.SOUTH);
	}
	
	public static void refreshSearchSelected(Image showImg, String name, int selectedNum, int width, int height) {

		imgLabel.setIcon(new ImageIcon(showImg.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), java.awt.Image.SCALE_SMOOTH)));
		nameLabel.setText("File Name : " + name + " [ " + width + " X " + height + " ]");
		numLabel.setText("Selected : " + selectedNum + " 개");
	}
	
	public static void setCopyingFileGUI(int fileNum) {
		frame.setTitle("copying...");
		
		copyProgBar = new JProgressBar();
		
		copyProgBar.setForeground(new Color(49, 232, 17));
		copyProgBar.setSize(200, 20);
		copyProgBar.setMaximum(fileNum);
		copyProgBar.setString(1 + " / " + fileNum);
		copyProgBar.setValue(0);
		copyProgBar.setStringPainted(true);
		
		frame.removeAll();
		frame.add(copyProgBar);
		frame.pack();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void copy(String source, String target) {

		//복사 대상이 되는 파일 생성 
		File sourceFile = new File( source );

		//스트림, 채널 선언
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		FileChannel fcin = null;
		FileChannel fcout = null;
		try {

			//스트림 생성
			inputStream = new FileInputStream(sourceFile);
			outputStream = new FileOutputStream(target);

			//채널 생성
			fcin = inputStream.getChannel();
			fcout = outputStream.getChannel();


			//채널을 통한 스트림 전송
			long size = fcin.size();
			fcin.transferTo(0, size, fcout);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			//자원 해제
			try{
				fcout.close();
			}catch(IOException ioe){}
			try{
				fcin.close();
			}catch(IOException ioe){}
			try{
				outputStream.close();
			}catch(IOException ioe){}
			try{
				inputStream.close();
			}catch(IOException ioe){}
		}
	}
}
