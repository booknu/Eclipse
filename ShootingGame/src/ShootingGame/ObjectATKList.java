package ShootingGame;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;

public class ObjectATKList {
	
	// 공통
	public ArrayList<ObjectATK> list = new ArrayList<ObjectATK>();
	public JLabel whose;
	public int speed;
	public int delay;
	String type;
	public String shape;
	
	public JLabel toWhich = null;	// tracking 탄환
	public int locationX;	// CertainLocation 탄환
	public int locationY;	// CertainLocation 탄환
	/**
	 * 구성자 (기본 공격일 경우)
	 * @param whose 누구의 공격인지
	 * @param speed 공격 객체의 속도
	 * @param delay 공격 딜레이
	 * @param shape 공격 모양
	 */
	public ObjectATKList(JLabel whose, int speed, int delay, String type, String shape)
	{
		this.type = type;
		this.whose = whose;
		this.speed = speed;
		this.delay = delay;
		this.shape = shape;
	}
	
	/**
	 * 구성자 (tracking 탄환을 만들고 싶을 떄)
	 * @param whose
	 * @param towhich
	 * @param speed
	 * @param delay
	 * @param type
	 * @param shape
	 */
	public ObjectATKList(JLabel whose, JLabel toWhich, int speed, int delay, String type, String shape)
	{
		this.type = type;
		this.toWhich = toWhich;
		this.whose = whose;
		this.speed = speed;
		this.delay = delay;
		this.shape = shape;
	}
	
	/**
	 * 구성자 (CertainLocation 탄환을 만들고 싶을 떄)
	 * @param whose
	 * @param towhich
	 * @param speed
	 * @param delay
	 * @param type
	 * @param shape
	 */
	public ObjectATKList(JLabel whose, int locationX, int locationY, int speed, int delay, String type, String shape)
	{
		this.type = type;
		this.whose = whose;
		this.speed = speed;
		this.delay = delay;
		this.shape = shape;
		this.locationX = locationX;
		this.locationY = locationY;
	}

	/* get 메소드 시작 ***********************************************************************************************/
	/**
	 * 누구 소유의 공격인지
	 */
	public JLabel getWhose()
	{
		return whose;
	}
	
	/**
	 * @return 객체의 속도
	 */
	public int getSpeed()
	{
		return speed;
	}

	/**
	 * @return 공격 딜레이
	 */
	public int getDelay()
	{
		return delay;
	}
	
	/**
	 * @return 공격의 모양
	 */
	public String getShape()
	{
		return shape;
	}
	
	public ArrayList<ObjectATK> getList()
	{
		return list;
	}
	
	public int size()
	{
		return list.size();
	}
	/* get 메소드 끝 ***************************************************************************************************/
	
	/* set 메소드 시작 *************************************************************************************************/
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public void setDelay(int delay)
	{
		this.delay = delay;
	}
	
	public void setShape(String shape)
	{
		this.shape = shape;
	}
	/* set 메소드 끝 ***************************************************************************************************/
	
	/**
	 * list에 공격 객체를 하나 추가함
	 * @param x whose의 x값
	 * @param y whose의 y값
	 */
	public void add(int x, int y)
	{
		int speedX=0;
		int speedY=0;
		
		// 노말 탄환 (그냥 밑으로 쭉 내려감)
		if(type.equals("Normal"))
		{
			speedX = 0;
			speedY = speed;
		}
		
		else if(type.equals("CertainLocation"))
		{
			int x1 = x; int y1 = y; int x2 = locationX; int y2 = locationY;
			double hypotenuse = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));	// 삼각형의 빗변
			double cos = (x2-x1) / hypotenuse; double sin = (y2-y1) / hypotenuse;
			
			speedX = (int)(cos * speed); speedY = (int)(sin * speed);
		}

		
		// 추적 탄환 (공격 주체와 공격 대상의 위치를 참조해 공격 방향을 정함)
		// 이동 중에는 경로가 변하지 않음
		else if(type.equals("Tracking"))
		{
			// 현재 공격의 주체의 위치를 (x1, y1)으로 보고
			// 추적할 대상의 위치를 (x2, y2)로 봐서
			// 추적 대상이 정지해 있다고 가정할 때 
			// 대상을 정확히 맞출 수 있도록 speedX, speedY 를 설정한다.
			int x1 = x; int y1 = y; int x2 = toWhich.getX(); int y2 = toWhich.getY();
			double hypotenuse = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));	// 삼각형의 빗변
			double cos = (x2-x1) / hypotenuse; double sin = (y2-y1) / hypotenuse;
			
			speedX = (int)(cos * speed); speedY = (int)(sin * speed);
		}
		
		list.add(new ObjectATK(x, y, speedX, speedY, whose, shape));
	}
	
	public void removeAll()
	{
		Iterator<ObjectATK> it = list.iterator();
		while(it.hasNext())
		{
			ObjectATK atk = it.next();
			atk.getLabel().setVisible(false);
			it.remove();
		}	
	}

}
