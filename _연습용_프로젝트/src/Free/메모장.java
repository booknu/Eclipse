package Free;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;

import org.mozilla.universalchardet.UniversalDetector;

public class 메모장 {

	static final File DEFAULT_DIR = new File(
			"FILEPATH");
	static File file = null;

	static final int MAX_NUM = 500;

	static ArrayList<String> logs = new ArrayList<String>();
	static boolean changed = false;

	static JFrame frame = new JFrame("새 파일");
	static JTextArea editor = new JTextArea();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JLabel log = new JLabel();
		changeLog(log, "새 파일 편집 시작");

		frame.setVisible(true);
		frame.setSize(700, 900);
		frame.setLayout(new BorderLayout());
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(e.WINDOW_CLOSING == e.getID()) {
					if(changed) {
						int option = JOptionPane.showConfirmDialog(null, "저장하시겠습니까?"
								, "Save", JOptionPane.OK_CANCEL_OPTION);
						if(option == JOptionPane.OK_OPTION) {
							save(editor, log);
						}
					}
					System.exit(0);
				}
			}
		});

		//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		///////// North
		JProgressBar progBar = new JProgressBar();
		frame.add(progBar, BorderLayout.NORTH);
		progBar.setForeground(new Color(49, 232, 17));
		progBar.setSize(200, 20);
		progBar.setMaximum(MAX_NUM);
		progBar.setString(0 + " / " + MAX_NUM);
		progBar.setValue(0);
		progBar.setStringPainted(true);

		///////// Center
		editor.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {				
				if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					save(editor, log);
				} else if((e.getKeyCode() == KeyEvent.VK_D) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)){
					try {
						int line = editor.getLineOfOffset(editor.getSelectionStart());
						editor.setSelectionStart(editor.getLineStartOffset(line) - 2);
						editor.setSelectionEnd(editor.getLineEndOffset(line));
						editor.replaceSelection("");
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if((e.getKeyCode() == KeyEvent.VK_L) && ((e.getModifiers() & KeyEvent.ALT_MASK) != 0)){
					showLogFrame();
				}
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					editor.insert(" ", editor.getSelectionStart());
				} 
			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyTyped(KeyEvent e) {
				countChar(editor, progBar);
				if(!e.isControlDown()) {
					changed = true;
				}
			}
		});
		DropTarget target = new DropTarget(editor,DnDConstants.ACTION_COPY_OR_MOVE,
				new DropTargetListener() {
			@Override
			public void dragEnter(DropTargetDragEvent dtde) {
				// TODO Auto-generated method stub

			}

			@Override
			public void dragExit(DropTargetEvent dte) {
				// TODO Auto-generated method stub

			}

			@Override
			public void dragOver(DropTargetDragEvent dtde) {
				// TODO Auto-generated method stub

			}

			@Override
			public void drop(DropTargetDropEvent dtde) {
				// TODO Auto-generated method stub
				if((dtde.getDropAction() &
						DnDConstants.ACTION_COPY_OR_MOVE)!=0){
					dtde.acceptDrop(dtde.getDropAction());
					Transferable tr = dtde.getTransferable();
					try{
						if(changed) {
							int option = JOptionPane.showConfirmDialog(null, "저장하시겠습니까?"
									, "Save", JOptionPane.OK_CANCEL_OPTION);
							if(option == JOptionPane.OK_OPTION) {
								save(editor, log);
							}
						}

						//전달되는 파일을 리스트형태로 변환
						//파일리스트의 DataFlavor를 이용하여 tr에 저장
						java.util.List list = (java.util.List)
								tr.getTransferData(DataFlavor.javaFileListFlavor);
						//리스트의 첫번째 원소를 파일로 읽어들인다.
						File file = (File)list.get(0);
						changeLog(log, "파일 열기: " + file.getPath());

						//						char buf[] = new char[1024];
						//						BufferedReader in = new BufferedReader(new  FileReader(file));
						//						
						//						int n = -1;
						//						editor.setText("");
						//						while((n=in.read(buf,0,1024))!=-1){
						//							editor.append(new String(buf,0,n));
						//						}
						//						in.close();

						String encoding = findFileEncoding(file);
						if(encoding != null) {
							Charset charset = Charset.forName(encoding);
							FileInputStream fis = new FileInputStream(file);
							ByteArrayOutputStream fbs = new ByteArrayOutputStream();

							byte[] buffer = new byte[4096];
							int n = 0;
							while((n = fis.read(buffer, 0, buffer.length)) > 0) {
								fbs.write(buffer, 0, n);
							}

							CharBuffer charBuffer = charset.decode(ByteBuffer.wrap(buffer));
							String str = charBuffer.toString();
							str = str.substring(0, str.lastIndexOf("\n"));
							editor.setText(str);

							메모장.file = file; 
							frame.setTitle(file.getPath());
							countChar(editor, progBar);
						} else {
							changeLog(log, "ERROR: 읽을 수 없는 인코딩입니다.");
						}


					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}

			@Override
			public void dropActionChanged(DropTargetDragEvent dtde) {

			}
		},true,null);

		JScrollPane scroll = new JScrollPane(editor);
		frame.add(scroll, BorderLayout.CENTER);

		///////// South
		JPanel bot = new JPanel();
		frame.add(bot, BorderLayout.SOUTH);
		bot.setLayout(new BorderLayout());

		bot.add(log, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		bot.add(buttons, BorderLayout.EAST);
		buttons.setLayout(new GridLayout(1, 2));

		JButton allLog = new JButton("로그");
		buttons.add(allLog);
		allLog.setBackground(Color.WHITE);
		allLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showLogFrame();
			}
		});

		JButton save = new JButton("저장");
		buttons.add(save);
		save.setBackground(Color.WHITE);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				save(editor, log);
			}
		});

		///////// Refresh
		frame.setSize(700, 901);
		frame.setSize(700, 900);
	}

	public static void save(JTextArea text, JLabel log) {
		if(file == null) {
			if(!DEFAULT_DIR.exists()) {
				changeLog(log, "ERROR: <저장 오류> 존재하지 않는 경로입니다.");
				return;
			} else if(!DEFAULT_DIR.isDirectory()) {
				changeLog(log, "ERROR: <저장 오류> 지정된 경로가 디렉토리가 아닙니다.");
				return;
			}
			String name = JOptionPane.showInputDialog("새 파일의 이름을 입력해주세요") + ".txt";
			file = new File(DEFAULT_DIR + "/" + name);
			try {
				file.createNewFile();
				changeLog(log, "새 파일이 생성되었습니다: " + name);
				frame.setTitle(file.getPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				changeLog(log, "ERROR: <저장 오류> 잘못된 이름으로 저장하셨습니다.");
				file = null;
			}
		}
		
		try {
			PrintWriter out;
			out = new PrintWriter(file);
			StringTokenizer st = new StringTokenizer(text.getText());
			while(st.hasMoreTokens()) {
				out.println(st.nextToken("\n"));
			}
			out.close();
			changed = false;
			changeLog(log, "저장되었습니다.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			changeLog(log, "ERROR: <저장 오류> 파일이 존재하지 않습니다.");
			file = null;
		}
	}

	public static void changeLog(JLabel log, String alert) {
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("hh:mm:ss");
		String timeStr = dayTime.format(new Date(time));
		String logText = "[" + timeStr + "] " + alert;
		log.setText(logText);
		logs.add(logText);
	}

	public static void countChar(JTextArea text, JProgressBar psBar) {
		int count = text.getText().length();
		psBar.setValue(count);
		psBar.setString(count + " / " + MAX_NUM);
		if(count > MAX_NUM) {
			psBar.setForeground(Color.RED);
		} else {
			psBar.setForeground(new Color(49, 232, 17));
		}
		
	}

	public static String findFileEncoding(File file) throws IOException {
		UniversalDetector detector = new UniversalDetector(null);
		FileInputStream fis = new FileInputStream(file);
		int nread;
		byte[] buf = new byte[4096];
		while((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}

		detector.dataEnd();
		String encoding = detector.getDetectedCharset();
		detector.reset();
		return encoding;
	}

	public static void showLogFrame() {
		JFrame logFrame = new JFrame("로그");
		logFrame.setVisible(true);
		logFrame.setSize(700, 900);
		logFrame.setLayout(new BorderLayout());

		JTextArea logArea = new JTextArea();
		JScrollPane logScroll = new JScrollPane(logArea);
		logFrame.add(logScroll);

		for(String logStr : logs) {
			logArea.setText(logArea.getText() + logStr + "\n");
		}

		logArea.setEditable(false);
		logArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					logFrame.dispose();
				}
			}
		});
	}
}
