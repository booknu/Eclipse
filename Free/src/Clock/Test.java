package Clock;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
 
public class Test extends JFrame {
    JPanel contentPane = new JPanel();
    JLabel la = new JLabel("HELLO");
    final int FLYING_UNIT = 10;
 
    Test() {
        setTitle("상,하,좌,우 키를 이용하여 텍스트 움직이기");
        this. setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.addKeyListener(new MyKeyListener());
        la.setLocation(0, 0);
        la.setSize(100, 20);
        contentPane.add(la);
        this.setSize(300, 300);
        this.setVisible(true);
        contentPane.requestFocus();
        
    }
 
    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
            case KeyEvent.VK_UP:
                JOptionPane.showMessageDialog(null, "hello!");
                JOptionPane.showMessageDialog(null, "hello!");
                break;
            case KeyEvent.VK_DOWN:
                la.setLocation(la.getX(), la.getY() + FLYING_UNIT);
                break;
            case KeyEvent.VK_LEFT:
                la.setLocation(la.getX() - FLYING_UNIT, la.getY());
                break;
            case KeyEvent.VK_RIGHT:
                la.setLocation(la.getX() + FLYING_UNIT, la.getY());
                break;
            }
        }
    }
 
    public static void main(String[] args) {
        new Test();
    }
}