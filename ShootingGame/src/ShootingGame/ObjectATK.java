package ShootingGame;

import java.util.ArrayList;

import javax.swing.JLabel;

/**
 * 공격 객체 각각의 정보
 * @author Administrator
 *
 */
public class ObjectATK {

	public JLabel whose;
	public int x;
	public int y;
	public String shape;
	public int speedX;
	public int speedY;
	public int delay;
	public JLabel ATK;
	
	/**
	 * 구성자
	 * @param x 만들어진 장소의 x값
	 * @param y 만들어진 장소의 y값
	 * @param speedX X축으로의 속도
	 * @param speedY Y축으로의 속도
	 */
	public ObjectATK(int x, int y, int speedX, int speedY, JLabel whose, String shape)
	{
		this.whose = whose;
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.shape = shape;
		ATK = new JLabel(this.shape);
	}
	
	/**
	 * @return 처음 장소의 x값
	 */
	public int getFirstX()
	{
		return x;
	}
	
	/**
	 * @return 처음 장소의 y값
	 */
	public int getFirstY()
	{
		return y;
	}
	
	/**
	 * @return x축으로의 속도
	 */
	public int getSpeedX()
	{
		return speedX;
	}
	
	/**
	 * @return y축으로의 속도
	 */
	public int getSpeedY()
	{
		return speedY;
	}
	
	/**
	 * @return 공격 객체의 JLabel
	 */
	public JLabel getLabel()
	{
		return ATK;
	}
}
