package functions;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 시작시 초기화 단계
		CommonSources.initialize();	//	계획을 불러옴

		// 처음 화면
		CommonSources.makeMainFrame();
		CommonSources.mainFrame.refresh();
	}

}
