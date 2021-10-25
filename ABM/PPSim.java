package ABM;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class PPSim extends JFrame implements KeyListener, MouseListener, MouseMotionListener{
	
	protected static DotPanel dp;
	
	public static final int MAX_X=100;
	public static final int MAX_Y=100;
	public static final int DOT_SIZE=6;
	public static final int NUM_PREY=10;
	public static final int NUM_PREDATORS=5;
	World ppworld;
	
	public PPSim() {
		this.setSize(MAX_X*DOT_SIZE,MAX_Y*DOT_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);this.setTitle("Predator Prey World");
		
		dp=new DotPanel(MAX_X, MAX_Y, DOT_SIZE);
		
		Container cPane=this.getContentPane();
		cPane.add(dp);
		this.addKeyListener(this);
		dp.addMouseListener(this);
		dp.addMouseMotionListener(this);
		this.pack();
		dp.init();
		dp.setPenColor(Color.red);
		this.setVisible(true);
		
		World ppWorld=new World(MAX_X, MAX_Y, NUM_PREY, NUM_PREDATORS);
		//Create simulation world
		while(true) {
			ppWorld.update();
			ppWorld.draw();
			dp.repaintAndSleep(80);
		}
	}
	
	
	
	
	

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new PPSim();
	}

}
