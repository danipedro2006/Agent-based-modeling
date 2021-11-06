package ABM;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import java.awt.event.*;

public class PPSim extends JFrame implements KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = -5176170979783243427L;

	protected static DotPanel dp;

	public static final int MAX_X = 100;
	public static final int MAX_Y = 100;
	public static final int DOT_SIZE = 6;
	private static final int NUM_PREY = 10;
	private static final int NUM_PREDATORS = 5;
	World ppworld;

	public PPSim() {
		this.setSize(MAX_X * DOT_SIZE, MAX_Y * DOT_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Predator Prey World");
		dp = new DotPanel(MAX_X, MAX_Y, DOT_SIZE);

		Container cPane = this.getContentPane();
		cPane.add(dp);
		this.addKeyListener(this);
		dp.addMouseListener(this);
		dp.addMouseMotionListener(this);

		this.pack();
		dp.init();
		dp.clear();
		dp.setPenColor(Color.red);
		this.setVisible(true);

		ppworld = new World(MAX_X, MAX_Y, NUM_PREY, NUM_PREDATORS);

		while (true) {
			ppworld.update();
			ppworld.draw(dp);
			dp.repaintAndSleep(80);
		}
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		System.out.print(keyEvent.getKeyChar());
		switch (keyEvent.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			ppworld = new World(MAX_X, MAX_Y, NUM_PREY, NUM_PREDATORS);
			break;
		case KeyEvent.VK_SPACE:
			ppworld.canavasColor = Color.GREEN;
			break;
		case KeyEvent.VK_P:
			ppworld.onPressedPred();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		ppworld.OnClickPrey(mouseEvent.getX() / DOT_SIZE, mouseEvent.getY() / DOT_SIZE);
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {

	}

	public static void main(String[] args) {
		/* Create a new GUI window */
		new PPSim();
	}
}
