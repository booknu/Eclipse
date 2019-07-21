package functions;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import objects.LogManager;

/**
 * 윈도우 창 닫기를 했을 때 실행되는 것
 * @author Administrator
 *
 */
public class MainFrameWindowAdapter extends WindowAdapter{
	public void windowClosing(WindowEvent e) {

		int exit = JOptionPane.showConfirmDialog(null, "변경사항을 저장하시겠습니까?", "Confirm Message", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(exit == JOptionPane.OK_OPTION || exit == JOptionPane.CLOSED_OPTION)
		{
			CommonSources.saveAll();
			JOptionPane.showMessageDialog(null, "저장되었습니다.");
		}
		else
		{
			LogManager.addLog(4, "");
		}
		CommonSources.save(LogManager.log_list, Settings.system_dir + Settings.log);
		System.exit(0);	

	}
}
