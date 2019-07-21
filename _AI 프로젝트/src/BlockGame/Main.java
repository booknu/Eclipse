package BlockGame;

import javax.swing.JOptionPane;

public class Main {

	public static Game game;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Function.initialize();
		game = new Game();
		System.out.println("<조작법>");
		System.out.println("D: 블록 바꾸기");
		System.out.println("F: 블록 회전 (시계방향)");
		System.out.println("S: 블록 회전 (반시계방향)");
		System.out.println("Back Space: 물리기");
		System.out.println("----------------------------------");
		System.out.println("<콘솔 정보>");
		System.out.println("WIN: 이기는 수의 경우의 수");
		System.out.println("LOSE: 최선의 수의 경우의 수, 그 때의 상대방이 이길 수 있는 수");
		System.out.println("----------------------------------");
		JOptionPane.showMessageDialog(null, "AI가 빈 보드에서 첫 수를 둘 때 시간이 오래 걸릴 수 있으니 주의해주세요. (13초)\n\n"
				+ "(수정하고 싶다면 Function.bestChoice에서 bestWin을 찾으면 바로 탐색이 멈추게 바꿔주세요.\n"
				+ "단, 이 경우 AI가 지고 있을 때 상대방의 다음 수에서 이기는 최소의 수를 찾지 못해 최적의 수를\n"
				+ "찾지 못할 수도 있습니다.)");
	}

}
