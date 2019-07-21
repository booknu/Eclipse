package ShootingGame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class ShootingGame extends JFrame implements ActionListener{
	
	private static final int WINDOWWIDTH = 700;
	private static final int WINDOWHEIGHT = 1000;
	
	public static final int DELAY = 15;
	public static final int ORIGINALSPEED = 10;
	
	public static final int NORMAL_ENEMY_ATK_SPEED = 6;
	public static final int NORMAL_ENEMY_ATK_DELAY = 20;
	public static final int FAST_ENEMY_ATK_SPEED = 7;
	public static final int FAST_ENEMY_ATK_DELAY = 17;
	
	public static int shipSpeed;	// shipSpeed를 임시저장
	private int shipHP = 3;
	private int bombs = 3;
	private boolean shipInf = false;
	private int infCount;	// 무적이 풀릴 count
	private int infTime = 120;	// 무적 시간 < 1000
	
	public static int enemySpeed = 1;
	private int enemyHP = 400;
	
	private int count = 0; // 시간을 맞추기 위함
	private Timer timer;
	private boolean enemyMoveLeft = false;
	private boolean randomMove = false;
	
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean spacePressed = false;
	
	ImageIcon m_ship = new ImageIcon(ImageIO.read(new File("ship1.png")));
	ImageIcon m_shipInf = new ImageIcon(ImageIO.read(new File("shipInf.png")));
	JLabel enemy = new JLabel(new ImageIcon(ImageIO.read(new File("enemy.gif"))));
	JLabel ship = new JLabel(m_ship);
     	
	ObjectATKList enemyATK1[] = new ObjectATKList[WINDOWWIDTH / 100];
	ObjectATKList enemyATK2 = new ObjectATKList(enemy, ship, 12, 30, "Tracking", "★");
	ObjectATKList shipATK = new ObjectATKList(ship, -15, 5, "Normal", "lll");
	
	JLabel enemyHPLabel = new JLabel("ENEMY HP : ");
	JProgressBar enemyHPBar = new JProgressBar();
	JLabel shipHPLabel = new JLabel("MY HP : " + shipHP);
	JLabel bomb = new JLabel("BOMB(A) : " + bombs);
	
	JPanel panel = new JPanel();
	public ShootingGame() throws IOException
	{
		
		
		for(int i=0; i<enemyATK1.length; i++)
			enemyATK1[i] = new ObjectATKList(enemy, 100*i, WINDOWHEIGHT
					, NORMAL_ENEMY_ATK_SPEED, NORMAL_ENEMY_ATK_DELAY
					, "CertainLocation", "●");
		
		timer = new Timer(DELAY, this);
        timer.start();
		
		setTitle("SHOOTING GAME");
		setContentPane(panel);
		panel.setLayout(null);
		panel.addKeyListener(new MyKeyListener());
		
		shipHPLabel.setLocation(WINDOWWIDTH - 100 ,10);
		shipHPLabel.setSize(100, 10);
		bomb.setLocation(WINDOWWIDTH/2 - 50 ,10);
		bomb.setSize(100, 10);
		ship.setLocation(WINDOWWIDTH/2 - 15,WINDOWHEIGHT - 100);
		ship.setSize(20,30);
		
		enemyHPLabel.setLocation(0, 10);
		enemyHPLabel.setSize(80, 10);
		enemyHPBar.setMaximum(enemyHP);
		enemyHPBar.setValue(enemyHP);
		enemyHPBar.setLocation(80, 10);
		enemyHPBar.setSize(200,10);
		enemy.setLocation(WINDOWWIDTH - 200, 50);
		enemy.setSize(217,181);
		
		panel.add(ship);
		panel.add(enemy);
		panel.add(shipHPLabel);
		panel.add(bomb);
		panel.add(enemyHPBar);
		panel.add(enemyHPLabel);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WINDOWWIDTH, WINDOWHEIGHT);
		panel.requestFocus();
		
	}
	
	/**
	 * 물체의 hitPoint를 알려준다.
	 * 
	 * @param obj 물체
	 * @param where 어디의 좌표를 알려줄지
	 * 				(LeftX, RightX, UpY, DownY)
	 * @return hitPoint의 좌표
	 */
	private int hitPoint(JLabel obj, String where)
	{
		if(where.equals("LeftX"))
			return obj.getX();
		else if(where.equals("RightX"))
			return obj.getX() + obj.getWidth();
		else if(where.equals("UpY"))
			return obj.getY() + obj.getHeight();
		else if(where.equals("DownY"))
			return obj.getY();
		return -1;
	}
	
	/**
	 * 공격 객체를 생성한다
	 * @param which 어떤 공격을 생성할지
	 * @param Xplus 객체의 x축 어디에 생성할지
	 */
	private void makeAttack(ObjectATKList which, int xPlus)
	{
		which.add(which.getWhose().getX() + xPlus, which.getWhose().getY());
		which.list.get(which.size() - 1).getLabel().setLocation(which.list.get(which.size() - 1).getFirstX() + xPlus, which.list.get(which.size() - 1).getFirstY());
		which.list.get(which.size() - 1).getLabel().setSize(15,10);
		panel.add(which.list.get(which.size() - 1).getLabel());
	}
	
	/**
	 * 공격 객체를 움직인다
	 * @param attackObj 공격 객체 종류
	 */
	public void moveAttack(ObjectATKList which)
	{
		Iterator<ObjectATK> it = which.list.iterator();
		while(it.hasNext())
		{
			ObjectATK atk = it.next();
			atk.getLabel().setLocation(atk.getLabel().getX() + atk.getSpeedX(), atk.getLabel().getY() + atk.getSpeedY());
			if(atk.getLabel().getY() > WINDOWHEIGHT || atk.getLabel().getY() < 50 || atk.getLabel().getX() > WINDOWWIDTH || atk.getLabel().getX() < 0)
			{
				atk.getLabel().setVisible(false);
				it.remove();
			}
		}	
	}
	
	/**
	 * 적을 움직인다
	 * 적은 가로로만 움직이며
	 * 양 끝에 도달하면 방향을 바꾼다.
	 * 한 번에 1픽셀 움직인다.
	 */
	private void enemyMove(String option)
	{
			// 적 객체가 테두리 밖으로 나가려고 하면 방향을 바꿈
			if(enemy.getX() >= WINDOWWIDTH - 200)
				enemyMoveLeft = true;
			else if(enemy.getX() <= 0)
				enemyMoveLeft = false;
			
			if(enemyMoveLeft)
				enemy.setLocation(enemy.getX() - enemySpeed, enemy.getY());
			else
				enemy.setLocation(enemy.getX() + enemySpeed, enemy.getY());
	}
	
	/**
	 * obj가 attackObj에게 맞았는지 확인한다.
	 * @param obj 대상 객체
	 * @param attackObj 공격 객체의 ArrayList
	 * @return 맞았는지?
	 *			맞았으면 맞은 공격 객체의 index를 반환
	 *			안 맞았으면 -1 반환
	 */
	public int isHit(JLabel obj, ArrayList<ObjectATK> attackObj)
	{
		Iterator<ObjectATK> it = attackObj.iterator();
		int index = 0;
		while(it.hasNext())
		{
			ObjectATK atk = it.next();
			if(atk.getLabel().getX() >= hitPoint(obj, "LeftX") && atk.getLabel().getX() <= hitPoint(obj, "RightX") && atk.getLabel().getY() <= hitPoint(obj, "UpY") && atk.getLabel().getY() >= hitPoint(obj, "DownY"))
				return index;
			index++;
		}
		return -1;
	}
	
	class ShootSound extends Thread
	{
		String fileName;
		public void setFileName(String fileName)
		{
			this.fileName = fileName;
		}
		
		public void run()
		{
			Player shoot = null;
			try {
				try {
					shoot = new Player(new FileInputStream(fileName));
				} catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			try {
				shoot.play();
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			shoot.close();
		}
	}
	/* 지역 메소드 완료 ********************************************************************************************/
	
	class MyKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch(keyCode)
			{
			case KeyEvent.VK_UP:
				upPressed = true;
				break;
			case KeyEvent.VK_DOWN:
				downPressed = true;
				break;
			case KeyEvent.VK_LEFT:
				leftPressed = true;
				break;
			case KeyEvent.VK_RIGHT:
				rightPressed = true;
				break;
			case KeyEvent.VK_SPACE:
				spacePressed = true;
				break;
			case KeyEvent.VK_A:
				if(bombs>0)
				{
					ShootSound bombS = new ShootSound();
					bombS.setFileName("bomb.mp3");
					bombS.start();
					enemyHP -= 20;
					bombs--;
					enemyHPBar.setValue(enemyHP);
					bomb.setText("BOMB(A) : " + bombs);
					
					for(int i=0; i<enemyATK1.length; i++)
						enemyATK1[i].removeAll();
					enemyATK2.removeAll();
					break;
				}
			}
		}
		
		public void keyReleased(KeyEvent e)
		{
			int keyCode = e.getKeyCode();
			switch(keyCode)
			{
			case KeyEvent.VK_UP:
				upPressed = false;
				break;
			case KeyEvent.VK_DOWN:
				downPressed = false;
				break;
			case KeyEvent.VK_LEFT:
				leftPressed = false;
				break;
			case KeyEvent.VK_RIGHT:
				rightPressed = false;
				break;
			case KeyEvent.VK_SPACE:
				spacePressed = false;
				break;
			}
		}
	}
	/* 키보드 입력 이벤트 완료 *****************************************************************************************/
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		ShootingGame s = new ShootingGame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		// timer 이벤트
		if(e.getSource().equals(timer))
		{
			if(count == 1000)
				count = 0;
			
			// 1000 / enemyATKDelay 마다 적 공격 객체를 생성
			for(int i=0; i<enemyATK1.length; i++)
			{
				if(count % enemyATK1[i].getDelay() == 0)
				makeAttack(enemyATK1[i], 0);
			}
			
			if(count % enemyATK2.getDelay() == 0)
			{
				makeAttack(enemyATK2, 0);
				makeAttack(enemyATK2, enemyATK2.getWhose().getWidth() - 100);
			}
			 
			// ship 공격 객체 생성
			if(count % shipATK.getDelay() == 0)
			{
				if(spacePressed)
				{
					ShootSound shoot = new ShootSound();
					shoot.setFileName("shoot.mp3");
					shoot.start();
					makeAttack(shipATK, 0);
					makeAttack(shipATK, ship.getWidth()-15);
				}
			}
			
			// 0.001ms 마다 적 공격 객체가 이동
			if(count % 1 == 0)
			{
				for(int i=0; i<enemyATK1.length; i++)
					moveAttack(enemyATK1[i]);
				moveAttack(shipATK);
			}
			if(count % 2 == 0)
				moveAttack(enemyATK2);
			
			// 0.001ms 마다 적 객체가 이동
			if(count % 1 == 0)
			{
				enemyMove("Normal");
				if(count % 100 == 0 && randomMove)
				{
					Random ran = new Random();
					int ranNum = ran.nextInt(2);
					if(ranNum == 0)
						enemyMoveLeft = true;
					else if(ranNum == 1)
						enemyMoveLeft = false;
				}
			}
				
			
			// 0.001ms 마다 ship이동
			if(count % 2 == 0)
			{
				if((upPressed && leftPressed) || (upPressed&&rightPressed) || (downPressed&&leftPressed) || (downPressed&&rightPressed))
				{
					shipSpeed = (int)(Math.sqrt((Math.pow(ORIGINALSPEED, 2))/2));
				}
				else
					shipSpeed = ORIGINALSPEED;
				if(upPressed)
					if(ship.getY() > 70)
					ship.setLocation(ship.getX(), ship.getY() - shipSpeed);
				if(downPressed)
					if(ship.getY() < WINDOWHEIGHT - 55)
					ship.setLocation(ship.getX(), ship.getY() + shipSpeed);
				if(leftPressed)
					if(ship.getX() > 10)
					ship.setLocation(ship.getX() - shipSpeed, ship.getY());
				if(rightPressed)
					if(ship.getX() < WINDOWWIDTH - 50)
					ship.setLocation(ship.getX() + shipSpeed, ship.getY());
			}
			
			// 무적이 풀릴 때 까지 기다렸다가 조건을 만족하면 무적 풀림
			if(shipInf && count == infCount)
			{
				shipInf = false;
				ship.setIcon(m_ship);
			}
			
			// ship이 공격에 맞았을 경우 hit을 출력 (ship이 무적이 아닐 때)
			for(int i=0; i<enemyATK1.length && !shipInf; i++)
			{
				int index = isHit(ship, enemyATK1[i].list);
				if(index != -1)
				{
					ShootSound hit = new ShootSound();
					hit.setFileName("hit.mp3");
					hit.start();
					shipHP--;
					shipHPLabel.setText("MY HP : " + shipHP);
					enemyATK1[i].list.get(index).getLabel().setVisible(false);
					enemyATK1[i].list.remove(index);
					shipInf = true;
					if(count < 1000 - infTime)
						infCount = count + infTime;
					else
						infCount = infTime - (999-count);
					ship.setIcon(m_shipInf);
					
					System.out.println("infCount = " + infCount);
				}
			}
			
			int index = isHit(ship, enemyATK2.list);
			if(index != -1 && !shipInf)
			{
				ShootSound hit = new ShootSound();
				hit.setFileName("hit.mp3");
				hit.start();
				shipHP--;
				shipHPLabel.setText("MY HP : " + shipHP);
				enemyATK2.list.get(index).getLabel().setVisible(false);
				enemyATK2.list.remove(index);
				shipInf = true;
				if(count < 1000 - infTime)
					infCount = count + infTime;
				else
					infCount = infTime - (999-count);
				ship.setIcon(m_shipInf);
				
				System.out.println("infCount = " + infCount);
			}
			
			// ship이 공격을 맞혔을 경우
			index = isHit(enemy, shipATK.list);
			if(index != -1)
			{
				enemyHP--;
				shipATK.list.get(index).getLabel().setVisible(false);
				shipATK.list.remove(index);
				enemyHPBar.setValue(enemyHP);
				if(enemyHP < 200)	// 적 체력이 300 미만이 되면
				{
					enemySpeed = 3;
					enemyHPBar.setForeground(Color.RED);
					for(int i=0; i<enemyATK1.length; i++)
					{
						enemyATK1[i].setSpeed(FAST_ENEMY_ATK_SPEED); 
						enemyATK1[i].setDelay(FAST_ENEMY_ATK_DELAY);
					}
					randomMove = true;
				}
			}
			
			if(enemyHP <= 0)
			{
				ShootSound win = new ShootSound();
				win.setFileName("win.mp3");
				win.start();
				JOptionPane.showMessageDialog(null, "VICTORY!!!");
				this.setVisible(false);
				timer.stop();
				this.dispose();
			}
			
			if(shipHP <= 0)
			{
				ShootSound lose = new ShootSound();
				lose.setFileName("lose.mp3");
				lose.start();
				JOptionPane.showMessageDialog(null, "LOSE....");
				this.setVisible(false);
				timer.stop();
				this.dispose();
			}
			count++;
		}
		/* timer 이벤트 끝 *********************************************************************************************/
	}

}
