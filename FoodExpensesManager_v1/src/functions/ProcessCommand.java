package functions;

import javax.swing.JOptionPane;

public class ProcessCommand {

	public static String [] commands = {"help",  "save", "backup", "restore", "format", "clear",  "display all"};
	enum Command {HELP, SAVE, BACKUP, RESTORE, FORMAT, CLEAR, SHOWALL
		, HELLOWORLD}
	
	public static boolean input(String in)
	{
		CommonSources.mainFrame.logRefresh("<명령어]> " + in);
//		if(commands[0].equals(in))	// help
//		{
//			
//			CommonSources.mainFrame.logRefresh("(안내) 사용 가능한 명령어들");
//			for(String cmd: commands)
//				CommonSources.mainFrame.logRefresh(cmd);
//			return true;
//		}
//		
//		else if(commands[1].equals(in)) // save
//		{
//			int i = JOptionPane.showConfirmDialog(null, "현재 상태를 저장합니다."
//					, "저장", JOptionPane.YES_NO_OPTION);
//			if(i == JOptionPane.OK_OPTION)
//				CommonSources.saveAll();
//			return true;
//		}
//		
//		else if(commands[2].equals(in))	// backup
//		{
//			int i = JOptionPane.showConfirmDialog(null, "백업을 위해 현재 상태를 저장합니다."
//					, "저장", JOptionPane.YES_NO_OPTION);
//			
//			if(i == JOptionPane.YES_OPTION)	// 저장 확인시 백업 프로세스 작동
//			{
//				CommonSources.save(CommonSources.expManager, Settings.user_dir + Settings.exp);
//				CommonSources.save(CommonSources.targetAmount_list, Settings.user_dir + Settings.ta);
//				CommonSources.save(CommonSources.default_targetAmount, Settings.user_dir + Settings.default_ta);
//				
//				String backupName = null;
//				backupName = JOptionPane.showInputDialog("백업 파일 이름을 입력하세요.\n"
//						+ "<주의>      \\   /   :   *   ?   \"   <   >   |      문자는 사용할 수 없습니다.");
//				CommonSources.backup(backupName);
//			}
//
//			return true;
//		}
//		
//		else if(commands[3].equals(in)) // restore
//		{
//			CommonSources.restore();
//			return true;
//		}
//		
//		else if(commands[4].equals(in)) // format
//		{
//			CommonSources.resetAllPlan();
//			return true;
//		}
//		
//		else if(commands[5].equals(in)) // clear log
//		{
//			CommonSources.mainFrame.terminateLog();
//			CommonSources.resetLog();
//			return true;
//		}
//
//		else if(commands[6].equals(in))	// display exp
//		{
//			CommonSources.mainFrame.logRefresh("(안내) 저장된 모든 지출 목록");
//			CommonSources.displayAllPlan();
//			return true;
//		}
//		
//		else
//		{
//			CommonSources.mainFrame.logRefresh("(경고) 존재하지 않는 명령어 입니다. (안내: help)");
//			return false;
//		}
	
		if(Command.HELP.toString().equalsIgnoreCase(in))	// help
		{
			
			CommonSources.mainFrame.logRefresh("(안내) 사용 가능한 명령어들");
			for(Command cmd: Command.values())
				CommonSources.mainFrame.logRefresh(cmd.toString());
			return true;
		}
		
		else if(Command.SAVE.toString().equalsIgnoreCase(in)) // save
		{
			int i = JOptionPane.showConfirmDialog(null, "현재 상태를 저장합니다."
					, "저장", JOptionPane.YES_NO_OPTION);
			if(i == JOptionPane.OK_OPTION)
				CommonSources.saveAll();
			return true;
		}
		
		else if(Command.BACKUP.toString().equalsIgnoreCase(in))	// backup
		{
			int i = JOptionPane.showConfirmDialog(null, "백업을 위해 현재 상태를 저장합니다."
					, "저장", JOptionPane.YES_NO_OPTION);
			
			if(i == JOptionPane.YES_OPTION)	// 저장 확인시 백업 프로세스 작동
			{
				CommonSources.save(CommonSources.expManager, Settings.user_dir + Settings.exp);
				CommonSources.save(CommonSources.targetAmount_list, Settings.user_dir + Settings.ta);
				CommonSources.save(CommonSources.default_targetAmount, Settings.user_dir + Settings.default_ta);
				
				String backupName = null;
				backupName = JOptionPane.showInputDialog("백업 파일 이름을 입력하세요.\n"
						+ "<주의>      \\   /   :   *   ?   \"   <   >   |      문자는 사용할 수 없습니다.");
				CommonSources.backup(backupName);
			}

			return true;
		}
		
		else if(Command.RESTORE.toString().equalsIgnoreCase(in)) // restore
		{
			CommonSources.restore();
			return true;
		}
		
		else if(Command.FORMAT.toString().equalsIgnoreCase(in)) // format
		{
			CommonSources.resetAllPlan();
			return true;
		}
		
		else if(Command.CLEAR.toString().equalsIgnoreCase(in)) // clear log
		{
			CommonSources.mainFrame.terminateLog();
			CommonSources.resetLog();
			return true;
		}

		else if(Command.SHOWALL.toString().equalsIgnoreCase(in))	// display exp
		{
			CommonSources.mainFrame.logRefresh("(안내) 저장된 모든 지출 목록");
			CommonSources.displayAllPlan();
			return true;
		}
		
		else if(Command.HELLOWORLD.toString().equalsIgnoreCase(in)) {
			CommonSources.mainFrame.logRefresh("<---- Hello World! ---->");
			return false;
		}
		
		else
		{
			CommonSources.mainFrame.logRefresh("(경고) 존재하지 않는 명령어 입니다. (안내: HELP)");
			return false;
		}
	}
}
