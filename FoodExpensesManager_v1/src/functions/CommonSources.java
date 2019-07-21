package functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import graphics.MainFrame;
import objects.Expenses;
import objects.ExpensesManager;
import objects.Log;
import objects.LogManager;
import objects.TargetAmount;
import objects.time.Time;

public class CommonSources {

	public static Integer default_targetAmount;

	public static ExpensesManager expManager;
	public static ArrayList<TargetAmount> targetAmount_list;

	public static MainFrame mainFrame;

	/* 기능 메소드 *********************************************************/
	public static boolean addExpenses(Expenses expenses)
	{
		return expManager.addPlan(expenses);
	}

	/**
	 * 테스트용
	 */
	public static void displayAllPlan()
	{
		List<Expenses> exp_list = expManager.getAllExpenses();
		long sum = 0;
		for(Expenses exp : exp_list)
		{
			mainFrame.logRefresh(exp.toString());
			sum += exp.getExpense();
		}
		mainFrame.logRefresh("Total: " + exp_list.size());
		mainFrame.logRefresh("Sum: " + NormalFunc.getCompartedInt(sum) + "원");
		mainFrame.logRefresh("Aver: " + NormalFunc.getCompartedInt((long)(1.0 * sum / exp_list.size())) + "원");
		mainFrame.logRefresh("------------END------------");
	}

	/* 초기화 및 저장 *********************************************************************/
	public static void initialize()
	{
		// expM 초기화
		File file = new File(Settings.user_dir + Settings.exp);
		if(file.exists())		// 세이브 파일이 있으면
			readExpensesManager();
		else
			expManager = new ExpensesManager();

		// ta 초기화
		file = new File(Settings.user_dir + Settings.ta);
		if(file.exists())		// 세이브 파일이 있으면
			readTargetAmounts();
		else
			targetAmount_list = new ArrayList<TargetAmount>();

		// d_ta 초기화
		file = new File(Settings.user_dir + Settings.default_ta);
		if(file.exists())		// 세이브 파일이 있으면
			readDefaultTargetAmounts();
		else
			default_targetAmount = 200000;

		// log 초기화
		file = new File(Settings.system_dir + Settings.log);
		if(file.exists())		// 세이브 파일이 있으면
			readLogs();
		else
			LogManager.log_list = new ArrayList<Log>();

		save(expManager, Settings.user_dir + Settings.exp);
		save(targetAmount_list, Settings.user_dir + Settings.ta);
		save(default_targetAmount, Settings.user_dir + Settings.default_ta);
		save(LogManager.log_list, Settings.system_dir + Settings.log);
	}

	public static void resetAllPlan()
	{
		int i = JOptionPane.showConfirmDialog(null, "초기화를 위해 현재 상태를 저장하고 자동으로 백업합니다."
				, "저장", JOptionPane.YES_NO_OPTION);

		if(i == JOptionPane.YES_OPTION)	// 저장 확인시 백업 프로세스 작동
		{
			save(expManager, Settings.user_dir + Settings.exp);
			save(targetAmount_list, Settings.user_dir + Settings.ta);
			save(default_targetAmount, Settings.user_dir + Settings.default_ta);

			// 백업과 로그 추가
			String dirName = "[fmt] " + makeDirName();
			if(backupUserSaveFile(dirName))
			{
				LogManager.addLog(1, "백업 파일: " + dirName);

				expManager = new ExpensesManager();
				targetAmount_list = new ArrayList<TargetAmount>();
				default_targetAmount = 200000;
				save(expManager, Settings.user_dir + Settings.exp);
				save(targetAmount_list, Settings.user_dir + Settings.ta);
				save(default_targetAmount, Settings.user_dir + Settings.default_ta);
			}
		}
	}

	public static void resetLog()
	{
		LogManager.log_list = new ArrayList<Log>();
		LogManager.addLog(5, "");
		save(LogManager.log_list, Settings.system_dir + Settings.log);
	}

	public static void backup(String name)
	{
		if(name.equals("") || name == null)
			name = "[sav] " + makeDirName();
		boolean done = backupUserSaveFile(name);
		if(done)
			LogManager.addLog(2, "백업 파일: " + name);
	}

	@SuppressWarnings("static-access")
	public static void restore()
	{
		boolean choosed = false;

		// 백업을 위해 저장
		int save = JOptionPane.showConfirmDialog(null, "복원을 위해 현재 상태를 저장하고 자동으로 백업합니다."
				, "저장", JOptionPane.YES_NO_OPTION);
		if(save == JOptionPane.YES_OPTION)	// 저장 확인시 백업 프로세스 작동
		{
			save(expManager, Settings.user_dir + Settings.exp);
			save(targetAmount_list, Settings.user_dir + Settings.ta);
			save(default_targetAmount, Settings.user_dir + Settings.default_ta);

			// 복원 하기 전 백업 파일 저장
			String dirName = "[rst] " + makeDirName();
			if(backupUserSaveFile(dirName))
			{
				// 복원할 파일을 선택
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(true);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				chooser.setCurrentDirectory(new File(Settings.backup_dir));
				int returnVal = chooser.showOpenDialog(null);
				chooser.setFileFilter (new javax.swing.filechooser.FileNameExtensionFilter("폴더", "."));

				String selected = null;
				// 파일을 제대로 골랐는지 알아봄
				if(returnVal != chooser.CANCEL_OPTION)
				{
					selected = chooser.getSelectedFile().getPath() + "\\";
					List<File> files = getDirFileList(selected);

					boolean d_ta = false;
					boolean ta = false;
					boolean exp = false;
					for(File f : files)
					{
						if(f.getName().equals(Settings.default_ta))
							d_ta = true;
						else if(f.getName().equals(Settings.ta))
							ta= true;
						else if(f.getName().equals(Settings.exp))
							exp = true;
					}

					if(d_ta && ta && exp)
						choosed = true;
					else
						JOptionPane.showMessageDialog(null, "제대로 된 백업 파일을 선택해주세요 !", 
								"잘못된 저장 파일", JOptionPane.WARNING_MESSAGE);
				}			

				if(choosed)
				{

					// 현재 save 폴더의 파일들 제거
					fileDelete(Settings.user_dir + Settings.exp);
					fileDelete(Settings.user_dir + Settings.ta);
					fileDelete(Settings.user_dir + Settings.default_ta);

					//  현재 save 폴더에 골라진 세이브 파일을 복사
					fileCopy(selected + Settings.exp, Settings.user_dir + Settings.exp);
					fileCopy(selected + Settings.ta, Settings.user_dir + Settings.ta);
					fileCopy(selected + Settings.default_ta, Settings.user_dir + Settings.default_ta);

					// 복원된 세이브 파일로 다시 데이터 로드
					readExpensesManager();
					readTargetAmounts();
					readDefaultTargetAmounts();

					LogManager.addLog(3, "백업 파일: " + dirName);
					mainFrame.refresh();
				}
			}
		}

	}

	public static void save(Object obj, String path)
	{
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
			out.writeObject(obj);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveAll()
	{
		save(expManager, Settings.user_dir + Settings.exp);
		save(targetAmount_list, Settings.user_dir + Settings.ta);
		save(default_targetAmount, Settings.user_dir + Settings.default_ta);
		LogManager.addLog(0, "");
	}

	public static void readExpensesManager()
	{
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(Settings.user_dir + Settings.exp));
			try {
				expManager = (ExpensesManager) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void readTargetAmounts()
	{
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(Settings.user_dir + Settings.ta));
			try {
				targetAmount_list = (ArrayList<TargetAmount>) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readDefaultTargetAmounts()
	{
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(Settings.user_dir + Settings.default_ta));
			try {
				default_targetAmount = (Integer) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void readLogs()
	{
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(Settings.system_dir + Settings.log));
			try {
				LogManager.log_list = (ArrayList<Log>) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean backupUserSaveFile(String backupName)
	{
		List<File> dirList = getDirFileList(Settings.user_dir);	//	user에 있는 모든 파일 가져옴
		File dir = new File(Settings.backup_dir, backupName);
		if(!dir.exists())
		{
			dir.mkdir();
			for(File f : dirList)
				fileCopy(f.getPath(), dir.getPath() + "\\" + f.getName());
			return true;
		}
		else
		{
			String btns[] = {"덮어쓰기", "취소"};
			int selection = JOptionPane.showOptionDialog(null, "백업 파일이 중복되었습니다.\n"
					+ "파일 이름: " + backupName,
					"덮어쓰기", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null,
					btns, "덮어쓰기");

			if(selection == JOptionPane.OK_OPTION) {
				deleteDirectory(dir);
				dir.mkdir();
				for(File f : dirList)
					fileCopy(f.getPath(), dir.getPath() + "\\" + f.getName());
				return true;
			}
			else
				return false;
		}
	}

	public static void makeMainFrame()
	{
		mainFrame = new MainFrame();
	}

	@SuppressWarnings("deprecation")
	public static void resizeMainFrame()
	{
		int w = CommonSources.mainFrame.window_width;
		int h = CommonSources.mainFrame.window_height;
		CommonSources.mainFrame.resize(w, h+1);
		CommonSources.mainFrame.resize(w, h);
	}

	public static List<File> getDirFileList(String dirPath)
	{
		// 디렉토리 파일 리스트
		List<File> dirFileList = null;

		// 파일 목록을 요청한 디렉토리를 가지고 파일 객체를 생성함
		File dir = new File(dirPath);

		// 디렉토리가 존재한다면
		if (dir.exists())
		{
			// 파일 목록을 구함
			File[] files = dir.listFiles();

			// 파일 배열을 파일 리스트로 변화함 
			dirFileList = Arrays.asList(files);
		}

		return dirFileList;
	}

	public static void fileCopy(String inFileName, String outFileName) {
		try {
			FileInputStream fis = new FileInputStream(inFileName);
			FileOutputStream fos = new FileOutputStream(outFileName);

			int data = 0;
			while((data=fis.read())!=-1) {
				fos.write(data);
			}
			fis.close();
			fos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String makeDirName()
	{
		return CalendarFunc.getCurrentYear() % 100 + "." 
				+ CalendarFunc.getTwoDigitMonth( CalendarFunc.getCurrentMonth() + 1) + "."
				+ CalendarFunc.getTwoDigitMonth(CalendarFunc.getCurrentDate()) + " "
				+ Time.getCurrentTimeForm().toStringNoAMPM();
	}

	//파일을 삭제하는 메소드
	public static void fileDelete(String deleteFileName) {
		File I = new File(deleteFileName);
		I.delete();
	}
	// 디렉토리 삭제
	public static boolean deleteDirectory(File path) {
		if(!path.exists()) {
			return false;
		}

		File[] files = path.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				deleteDirectory(file);
			} else {
				file.delete();
			}
		}

		return path.delete();
	}
}
